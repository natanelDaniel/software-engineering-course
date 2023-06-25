package StockMarketProject;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.XChartSeriesColors;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Trader {
    private String username;
    private String password;
    private Portfolio portfolio;
    private double managementPrice;
    private double balance;
    private double profitForTax;

    private ArrayList<Double> balanceHistory;

    private StockMarket stockMarket;

    private static final double TAX = 0.25;

    private static final double ORDER_FEE = 0.01;

    public Trader(String username, String password, double managementPrice, double profitForTax, StockMarket stockMarket) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio();
        this.managementPrice = managementPrice;
        this.balance = 0;
        this.profitForTax = profitForTax;
        this.stockMarket = stockMarket;
        this.balanceHistory = new ArrayList<Double>();

    }

    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public double getManagementPrice() {
        return managementPrice*portfolio.getBalanceInMarket();
    }

    public void setManagementPrice(double managementPrice) {
        this.managementPrice = managementPrice;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public double getProfitForTax() {
        return profitForTax;
    }

    public void setProfitForTax(double profitForTax) {
        this.profitForTax = profitForTax;
    }

    public double getFee() {
        return ORDER_FEE;
    }

    public double getTax() {
        return TAX;
    }
    // Methods

    // Method to buy a Asset
    public boolean buyAssetLimit(Asset asset, int amount, double pricePerUnit, boolean fromTransaction, Transaction transaction) {
        if (amount <= 0) {
            if (! fromTransaction) {
                System.out.println("You can't buy a negative amount of assets");
            }
            return false;
        }
        if (pricePerUnit <= 0) {
            if (! fromTransaction) {
                System.out.println("You can't buy an asset with a negative price");
            }
            return false;
        }
        if (amount * pricePerUnit  * (1  + this.getFee()) > balance) {
            if (! fromTransaction) {
                System.out.println("You don't have enough money to buy this asset");
            }
            return false;
        }
        if (asset.getPrice() == 0) {
            if (! fromTransaction) {
                System.out.println("The asset is not available");
            }
            return false;
        }
        if (pricePerUnit < asset.getPrice()) {
//                 get the current date and time
            if (! fromTransaction) {
                Date date = new Date();
                Transaction transaction_ = new Transaction(this, asset, "buy", pricePerUnit, amount, date);
                asset.addBuyer(transaction_);
                System.out.println("The Transaction was added to the waiting list");
            }
            return false;
        }
        int availableAmount = asset.getAvailableAmount();
        if (availableAmount <= 0) {
            if (! fromTransaction) {
                System.out.println("The Transaction was added to the waiting list");
                Date date = new Date();
                Transaction transaction_ = new Transaction(this, asset, "buy", pricePerUnit, amount, date);
                asset.addBuyer(transaction_);
            }
            return false;
        }
        if (availableAmount < amount) {
            this.balance -= availableAmount * asset.getPrice() * (1  + this.getFee());
            portfolio.addAsset(asset, availableAmount, asset.getPrice());
            asset.updateAvailableAmount(0);
            asset.addTrader(this);
            stockMarket.updateBalance(availableAmount * asset.getPrice() * (1  + this.getFee()));

            if (! fromTransaction) {
                System.out.println("The Transaction was added to the waiting list");
                Date date = new Date();
                Transaction transaction_ = new Transaction(this, asset, "buy", pricePerUnit, amount - availableAmount, date);
                asset.addBuyer(transaction_);
            }
            else {
                transaction.setAmount(amount - availableAmount);
            }
            return false;
        }
        this.balance -= amount * asset.getPrice() * (1  + this.getFee());
        portfolio.addAsset(asset, amount, asset.getPrice());

        asset.updateAvailableAmount(availableAmount - amount);
        asset.addTrader(this);
        stockMarket.updateBalance(amount * asset.getPrice() * (1  + this.getFee()));
        if (! fromTransaction) {
            System.out.println("The Transaction was successful");
        }
        return true;
    }

    // Method to sell an Asset
    public boolean sellAsset(Asset asset, int amount, double pricePerUnit, String mode, boolean fromTransaction, Transaction transaction) {
        if (amount <= 0) {
            if (! fromTransaction) {
                System.out.println("You can't sell a negative amount of assets");
            }
            return false;
        }
        if (pricePerUnit <= 0) {
            if (! fromTransaction) {
                System.out.println("You can't sell an asset with a negative price");
            }
            return false;
        }
        if (amount > portfolio.getAssetAmount(asset)) {
            if (! fromTransaction) {
                System.out.println("You don't have enough assets to sell");
            }
            return false;
        }
        if (asset.getPrice() == 0) {
            if (! fromTransaction) {
                System.out.println("The asset is not available");
            }
            return false;
        }
        if (mode.equals("market")) {
            System.out.println("You are selling " + amount + " " + asset.getSymbol() + " for " + asset.getPrice() + " each");
            System.out.println("The Transaction was successful");

            balance += amount * asset.getPrice()  * (1  - this.getFee());
            portfolio.removeAsset(asset, amount, asset.getPrice());
            if (portfolio.getAssetAmount(asset) == 0) {
                asset.removeTrader(this);
            }
            asset.updateAvailableAmount(asset.getAvailableAmount() + amount);

            stockMarket.updateBalance(- amount * asset.getPrice() * (1  - this.getFee()));

//            Tax part
            double tax = (asset.getPrice() - portfolio.getAveragePrice(asset) ) * amount * this.getTax();
            setProfitForTax(tax + getProfitForTax());
            System.out.println("You are paying " + tax + " tax");
            return true;
        } else if (mode.equals("limit")) {
            if (pricePerUnit > asset.getPrice()) {
                if (! fromTransaction) {
                    System.out.println("The price you entered is higher than the market price, Add the Transaction to the waiting list");
                    Date date = new Date();
                    Transaction transaction_ = new Transaction(this, asset, "sell", pricePerUnit, amount, date);
                    asset.addSeller(transaction_);
                }
                return false;
            }
            System.out.println("You are selling " + amount + " " + asset.getSymbol() + " for " + pricePerUnit + " each");
            balance += amount * asset.getPrice() * (1 - this.getFee());
            portfolio.removeAsset(asset, amount, asset.getPrice());
            asset.updateAvailableAmount(asset.getAvailableAmount() + amount);
            if (portfolio.getAssetAmount(asset) == 0) {
                asset.removeTrader(this);
            }
            stockMarket.updateBalance(-amount * asset.getPrice() * (1 - this.getFee()));

//            Tax part
            double tax = (asset.getPrice() - portfolio.getAveragePrice(asset) ) * amount * this.getTax();
            System.out.println("You are paying " + tax + " tax");
            setProfitForTax(tax + getProfitForTax());
            return true;
        }
        else {
            if (! fromTransaction) {
                System.out.println("The mode you entered is not valid");
            }
            return false;
        }
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("You can't deposit a negative amount of money");
            return;
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("You can't withdraw a negative amount of money");
            return;
        }
        if (amount > this.balance) {
            System.out.println("You don't have enough money to withdraw");
            return;
        }
        this.balance -= amount;
        if (getProfitForTax() > 0){
            this.balance -= getProfitForTax();
            setProfitForTax(0);
        }
    }

    public double getBalanceInMarket() {
        return portfolio.getBalanceInMarket();
    }

    public double giveManagementFee() {
        double fee = this.managementPrice * getBalanceInMarket();
        this.balance -= fee;
        return fee;
    }


    public void buyAssetMarketMode(Asset asset, int money) {
        double balance = this.getBalance();
        int amount = (int) (money / asset.getPrice());
        if (asset.getPrice() == 0) {
            System.out.println("This asset is not traded in the market");
            return;
        }
        if (amount * asset.getPrice() * (1 + this.getFee()) >= balance) {
            System.out.println("You don't have enough money to buy this asset");
            return;
        }
        Integer availableAmount = asset.getAvailableAmount();
        System.out.println("You try to buy " + amount + " of " + asset.getSymbol());
        if (amount > asset.getAvailableAmount()) {
            System.out.println("There are not enough assets in the market, Buying the available amount");
            System.out.println("You bought " + availableAmount + " of " + asset.getSymbol());
            System.out.println("You paid " + availableAmount * asset.getPrice() * (1  + this.getFee()) + " for this transaction");
            this.balance -= availableAmount * asset.getPrice() * (1  + this.getFee());
            portfolio.addAsset(asset, availableAmount, asset.getPrice());
            asset.updateAvailableAmount(0);
            asset.addTrader(this);
            stockMarket.updateBalance(availableAmount * asset.getPrice() * (1  + this.getFee()));

            System.out.println("There are not enough assets in the market");
            Date date = new Date();
            Transaction transaction = new Transaction(this, asset, "buy", asset.getPrice(), amount - availableAmount, date);
            asset.addBuyer(transaction);
        }
        else {
            this.balance -= amount * asset.getPrice() * (1  + this.getFee());
            portfolio.addAsset(asset, amount, asset.getPrice());

            asset.updateAvailableAmount(availableAmount - amount);
            asset.addTrader(this);
            stockMarket.updateBalance(amount * asset.getPrice() * (1  + this.getFee()));
            System.out.println("You bought " + amount + " of " + asset.getSymbol());
            System.out.println("You paid " + amount * asset.getPrice() * (1  + this.getFee()) + " for this transaction");
        }

    }

    public String toString() {
        return "Name: " + this.getUsername() + " Balance: " + this.getBalance() + " Balance in Market: " + this.getBalanceInMarket();
    }

    public void printPortfolio() {
        System.out.println(this.getPortfolio().toString());
    }

    public void update() {
        balanceHistory.add(this.getBalance() + this.getBalanceInMarket());
    }

    public void plotBalanceHistory(){
        //        make LineChart with the history of the asset, from  xchart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Stock Price History")
                .xAxisTitle("Time")
                .yAxisTitle("Price")
                .build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                .setMarkerSize(4)
                .setChartTitleVisible(true)
                .setLegendPosition(Styler.LegendPosition.InsideNW)
                .setChartTitleVisible(true)
                .setChartTitlePadding(15)
                .setPlotBackgroundColor(XChartSeriesColors.BLACK)
                .setPlotBorderColor(XChartSeriesColors.GREEN);

        // Create Series

        XYSeries series = chart.addSeries("Balance", null, balanceHistory);

        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }
}
