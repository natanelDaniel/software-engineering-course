package StockMarketProject;

import java.util.Date;

public class Trader {
    private String username;
    private String password;
    private Portfolio portfolio;
    private double managementPrice;
    private double balance;
    private double profitForTax;

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

    }

    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String getPassword() {
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
        return managementPrice;
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

    public boolean buyAsset(Asset asset, int amount, double pricePerUnit, String mode) {
        if (amount <= 0) {
            System.out.println("You can't buy a negative amount of assets");
            return false;
        }
        if (pricePerUnit <= 0) {
            System.out.println("You can't buy an asset with a negative price");
            return false;
        }
        if (amount * pricePerUnit  * (1  + this.getFee()) > balance) {
            System.out.println("You don't have enough money to buy this asset");
            return false;
        }
        if (asset.getPrice() == 0) {
            System.out.println("This asset is not traded in the market");
            return false;
        }
        if (mode.equals("market")) {
            if (amount * asset.getPrice() * (1  + this.getFee()) > balance) {
                System.out.println("You don't have enough money to buy this asset");
                return false;
            }
            int availableAmount = asset.getAvailableAmount();
            if (availableAmount <= 0) {
                System.out.println("There 0 assets in the market");
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "buy", pricePerUnit, amount, date);
                asset.addBuyer(transaction);
                return false;
            }
            if (availableAmount < amount) {
                balance -= availableAmount * asset.getPrice() * (1  + this.getFee());
                portfolio.addAsset(asset, availableAmount, asset.getPrice());
                asset.updateAvailableAmount(0);

                stockMarket.updateBalance(availableAmount * asset.getPrice()  * (1  + this.getFee()));

                System.out.println("There are not enough assets in the market");
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "buy", pricePerUnit, amount - availableAmount, date);
                asset.addBuyer(transaction);
                return true;
            }
            balance -= amount * asset.getPrice() * (1  + this.getFee());
            portfolio.addAsset(asset, amount, asset.getPrice());
            asset.updateAvailableAmount(availableAmount - amount);

            stockMarket.updateBalance(amount * asset.getPrice()  * (1  + this.getFee()));

            return true;
        } else if (mode.equals("limit")) {
            if (pricePerUnit < asset.getPrice()) {
                System.out.println("The price you entered is lower than the market price, Add the Transaction to the waiting list");
//                 get the current date and time
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "buy", pricePerUnit, amount, date);
                asset.addBuyer(transaction);
                return false;
            }
            int availableAmount = asset.getAvailableAmount();
            if (availableAmount <= 0) {
                System.out.println("There 0 assets in the market");
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "buy", pricePerUnit, amount, date);
                asset.addBuyer(transaction);
                return false;
            }
            if (availableAmount < amount) {
                balance -= availableAmount * asset.getPrice() * (1  + this.getFee());
                portfolio.addAsset(asset, availableAmount, asset.getPrice());
                asset.updateAvailableAmount(0);

                stockMarket.updateBalance(availableAmount * asset.getPrice() * (1  + this.getFee()));

                System.out.println("There are not enough assets in the market");
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "buy", pricePerUnit, amount - availableAmount, date);
                asset.addBuyer(transaction);
                return true;
            }
            balance -= amount * asset.getPrice() * (1  + this.getFee());
            portfolio.addAsset(asset, amount, asset.getPrice());

            asset.updateAvailableAmount(availableAmount - amount);

            stockMarket.updateBalance(amount * asset.getPrice() * (1  + this.getFee()));

            return true;
        }
        else {
            System.out.println("You entered an invalid mode");
            return false;
        }
    }

    // Method to sell a Asset
    public boolean sellAsset(Asset asset, int amount, double pricePerUnit, String mode){
        if (amount <= 0) {
            System.out.println("You can't sell a negative amount of assets");
            return false;
        }
        if (pricePerUnit <= 0) {
            System.out.println("You can't sell an asset with a negative price");
            return false;
        }
        if (amount > portfolio.getAssetAmount(asset)) {
            System.out.println("You don't have enough assets to sell");
            return false;
        }
        if (asset.getPrice() == 0) {
            System.out.println("This asset is not traded in the market");
            return false;
        }
        if (mode.equals("market")) {
            balance += amount * asset.getPrice()  * (1  - this.getFee());
            portfolio.removeAsset(asset, amount, asset.getPrice());
            asset.updateAvailableAmount(asset.getAvailableAmount() + amount);

            stockMarket.updateBalance(- amount * asset.getPrice() * (1  - this.getFee()));

//            Tax part
            double tax = (asset.getPrice() - portfolio.getAveragePrice(asset) ) * amount * this.getTax();
            setProfitForTax(tax + getProfitForTax());
            return true;
        } else if (mode.equals("limit")) {
            if (pricePerUnit > asset.getPrice()) {
                System.out.println("The price you entered is higher than the market price, Add the Transaction to the waiting list");
                Date date = new Date();
                Transaction transaction = new Transaction(this, asset, "sell", pricePerUnit, amount, date);
                asset.addSeller(transaction);
                return false;
            }
            balance += amount * asset.getPrice() * (1 - this.getFee());
            portfolio.removeAsset(asset, amount, asset.getPrice());
            asset.updateAvailableAmount(asset.getAvailableAmount() + amount);

            stockMarket.updateBalance(-amount * asset.getPrice() * (1 - this.getFee()));

//            Tax part
            double tax = (asset.getPrice() - portfolio.getAveragePrice(asset) ) * amount * this.getTax();
            setProfitForTax(tax + getProfitForTax());
            return true;
        }
        else {
            System.out.println("You entered an invalid mode");
            return false;
        }
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("You can't deposit a negative amount of money");
            return;
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("You can't withdraw a negative amount of money");
            return;
        }
        if (amount > balance) {
            System.out.println("You don't have enough money to withdraw");
            return;
        }
        balance -= amount;
        if (getProfitForTax() > 0){
            balance -= getProfitForTax();
            setProfitForTax(0);
        }
    }

    public double getBalanceInMarket() {
        return portfolio.getBalanceInMarket();
    }

    public double giveManagementFee() {
        double fee = this.managementPrice * getBalanceInMarket();
        balance -= fee;
        return fee;
    }



}