package StockMarketProjectCopy;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StockMarket extends Thread{
    private ArrayList<Asset> assets;
    private ArrayList<Trader> traders;

    private double managementPrice;

    private double profitForTax;


    private double balance;

    public StockMarket() {
        this.assets = new ArrayList<Asset>();
        this.traders = new ArrayList<Trader>();
        this.balance = 0;
        this.managementPrice = 0.01;
        this.profitForTax = 0.25;
    }

    public void addTrader(Trader trader) {
        if (traders.contains(trader)) {
            System.out.println("Trader already exists");
            return;
        }
        traders.add(trader);
    }

    public void removeTrader(Trader trader) {
        if (!traders.contains(trader)) {
            System.out.println("Trader doesn't exist");
            return;
        }
        traders.remove(trader);
    }

    public ArrayList<Trader> getTraders() {
        return this.traders;
    }

    public ArrayList<Asset> getAssets() {
        return this.assets;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    public void printMainMenu() {
        System.out.println("Welcome to the Stock Market!");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Sign up");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");
    }

    public void menu(Scanner scanner) {
        this.start();
        printMainMenu();
        String choice = scanner.nextLine();
//        here we should take thread to update the stock market prices
        while (!choice.equals("3")) {
            switch (choice) {
                case "1":
                    signUp(scanner);
                    break;
                case "2":
                    signIn(scanner);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            printMainMenu();
            choice = scanner.nextLine();
        }
    }

    public void signUp(Scanner scanner) {
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        if (traders.contains(username) || username.toLowerCase().equals("admin")) {
            System.out.println("Username already exists");
            return;
        }
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        while (!VerifyPassword(password)) {
            System.out.println("Invalid password, password must contain at least 8 characters, " +
                    " at least one digit, at least one lower case letter, at least one upper case " +
                    " letter and at least one special character");
            System.out.println("Please enter your password:");
            password = scanner.nextLine();
        }


        Trader trader = new Trader(username, password, managementPrice, profitForTax, this);
        addTrader(trader);
        if (username.toLowerCase().equals("admin")) {
            adminMenu(scanner);
        } else {
            traderMenu(scanner, trader);
        }
    }

    private boolean VerifyPassword(String password) {
        if (password.length() < 8)
            return false;
        boolean hasDigit = false;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i)))
                hasDigit = true;
            else if (Character.isLowerCase(password.charAt(i)))
                hasLowerCase = true;
            else if (Character.isUpperCase(password.charAt(i)))
                hasUpperCase = true;
            else
                hasSpecial = true;
        }
        return hasDigit && hasLowerCase && hasUpperCase && hasSpecial;
    }
    public void printAdminManu(){
        System.out.println("Welcome admin!");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Take management fee");
        System.out.println("2. search for a trader");
        System.out.println("3. search for an asset");
        System.out.println("4. plot Asset");
        System.out.println("5. Add asset");
        System.out.println("6. Remove asset");
        System.out.println("7. Sort assets by name");
        System.out.println("8. Sort assets by price");
        System.out.println("9. Sort assets by amount");
        System.out.println("10. Sort traders by name");
        System.out.println("11. Sort traders by balance");
        System.out.println("12. plot Assets by price");
        System.out.println("13. plot Assets by amount");
        System.out.println("14. plot Traders by balance");
        System.out.println("15. plot Traders by Balance in Market");
        System.out.println("16. Load assets from file");
        System.out.println("17. Sign out");
    }
    private void adminMenu(Scanner scanner) {
        printAdminManu();
        String choice = scanner.nextLine();
        while (!choice.equals("17")) {
            switch (choice) {
                case "1":
                    takeManagementPrice();
                    break;
                case "2":
                    searchForTrader(scanner);
                    break;
                case "3":
                    searchForAsset(scanner);
                    break;
                case "4":
                    plotAsset(scanner);
                    break;
                case "5":
                    addAssetFromScanner(scanner);
                    break;
                case "6":
                    removeAssetFromScanner(scanner);
                    break;
                case "7":
                    sortAssetsByName();
                    printAssets();
                    break;
                case "8":
                    sortAssetsByPrice();
                    printAssets();
                    break;
                case "9":
                    sortAssetsByAmount();
                    printAssets();
                    break;
                case "10":
                    sortTradersByName();
                    printTraders();
                    break;
                case "11":
                    sortTradersByBalance();
                    printTraders();
                    break;
                case "12":
                    plotAssetsByPrice();
                    break;
                case "13":
                    plotAssetsByAmount();
                    break;
                case "14":
                    plotTradersByBalance();
                    break;
                case "15":
                    plotTradersByBalanceInMarket();
                    break;
                case "16":
                    System.out.println("Please enter the file path:");
                    String filePath = scanner.nextLine();
                    LoadAssetsFromFile(filePath);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            printAdminManu();
            choice = scanner.nextLine();
        }
    }

    private void plotAssetsByAmount() {
        assets.sort(Comparator.comparing(Asset::getAvailableAmount));
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Portfolio").xAxisTitle("Asset").yAxisTitle("Value").build();
        List<String> assetNames = new ArrayList<>();
        List<Integer> assetValues = new ArrayList<>();
        for (Asset asset : assets) {
            assetNames.add(asset.getSymbol());
            assetValues.add(asset.getAvailableAmount());
        }
        chart.addSeries("Assets", assetNames, assetValues);
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    private void sortTradersByBalance() {
        traders.sort(Comparator.comparing(Trader::getBalance));
        printTraders();
    }

    private void plotTradersByBalance() {
        traders.sort(Comparator.comparing(Trader::getBalance));
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Portfolio").xAxisTitle("Asset").yAxisTitle("Value").build();
        List<String> traderNames = new ArrayList<>();
        List<Double> traderValues = new ArrayList<>();
        for (Trader trader : traders) {
            traderNames.add(trader.getUsername());
            traderValues.add(trader.getBalance());
        }
        chart.addSeries("Traders", traderNames, traderValues);
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    private void plotTradersByBalanceInMarket() {
        traders.sort(Comparator.comparing(Trader::getBalanceInMarket));
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Portfolio").xAxisTitle("Asset").yAxisTitle("Value").build();
        List<String> traderNames = new ArrayList<>();
        List<Double> traderValues = new ArrayList<>();
        for (Trader trader : traders) {
            traderNames.add(trader.getUsername());
            traderValues.add(trader.getBalanceInMarket());
        }
        chart.addSeries("Traders", traderNames, traderValues);
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    private void printTraders() {
        for (Trader trader : traders) {
            System.out.println(trader);
        }
    }

    private void sortTradersByName() {
        traders.sort(Comparator.comparing(Trader::getUsername));
    }

    private void removeAssetFromScanner(Scanner scanner) {
        System.out.println("Please enter the symbol of the asset you want to remove:");
        String symbol = scanner.nextLine();
        Asset asset = searchAsset(symbol);
        if (!assets.contains(asset)) {
            System.out.println("Asset doesn't exist");
            return;
        }
        assets.remove(asset);
    }

    private void addAssetFromScanner(Scanner scanner) {
        System.out.println("Stock or Crypto? [S/C]");
        String choice = scanner.nextLine();
        if (! (choice.toLowerCase().equals("s") || choice.toLowerCase().equals("c"))) {
            System.out.println("Invalid input");
        }
        System.out.println("Please enter the symbol of the asset you want to add:");
        String symbol = scanner.nextLine();
        if (searchAsset(symbol) != null) {
            System.out.println("Asset with this symbol already exists");
            return;
        }
        System.out.println("Please enter the price of the asset you want to add:");
        double price = scanner.nextDouble();
        System.out.println("Please enter the amount of the asset you want to add:");
        int amount = scanner.nextInt();
        System.out.println("Please enter mean of the asset you want to add:");
        double mean = scanner.nextDouble();
        System.out.println("Please enter the std of the asset you want to add:");
        double std = scanner.nextDouble();
        if (choice.toLowerCase().equals("s")) {
            System.out.println("Please enter the company name of the stock you want to add:");
            String name = scanner.nextLine();
            scanner.nextLine();
            Stock stock = new Stock(symbol, price, mean, std, amount, name);
            this.assets.add(stock);
        } else {
            System.out.println("Please enter the currency name of the asset:");
            String currencyName = scanner.nextLine();
            VirtualCurrency vc = new VirtualCurrency(symbol, price, mean, std, amount, currencyName);
            this.assets.add(vc);
        }
    }

    private void searchForTrader(Scanner scanner) {
        System.out.println("Please enter the username of the trader you are looking for:");
        String username = scanner.nextLine();
        Trader trader = searchTrader(username);
        if (trader == null) {
            System.out.println("Trader not found");
        } else {
            System.out.println(trader);
        }
    }

    private Trader searchTrader(String username) {
        for (Trader trader : traders) {
            if (trader.getUsername().equals(username)) {
                return trader;
            }
        }
        return null;
    }

    private void printTraderManu(){
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Search for an asset");
        System.out.println("2. Plot asset");
        System.out.println("3. Buy asset");
        System.out.println("4. Sell asset");
        System.out.println("5. Print portfolio");
        System.out.println("6. Plot portfolio");
        System.out.println("7. Plot portfolio Bar Chart");
        System.out.println("8. Deposit money");
        System.out.println("9. Withdraw money");
        System.out.println("10. Remove account");
        System.out.println("11. plot Assets by price");
        System.out.println("12. Sign out");
    }
    public void traderMenu(Scanner scanner, Trader trader) {
        System.out.println("Welcome " + trader.getUsername() + "!");
        printTraderManu();
        String choice = scanner.nextLine();
        while (!choice.equals("12")) {
            switch (choice) {
                case "1":
                    searchForAsset(scanner);
                    break;
                case "2":
                    plotAsset(scanner);
                    break;
                case "3":
                    System.out.println("Please enter the Symbol of the asset you want to buy:");
                    String symbol = scanner.nextLine();
                    Asset asset = searchAsset(symbol);
                    if (asset == null) {
                        System.out.println("Asset not found");
                    } else {
                        System.out.println(asset);
                        System.out.println("Please enter the mode of purchase:");
                        System.out.println("1. Market");
                        System.out.println("2. Limit");

                        String mode = scanner.nextLine();
                        while (!mode.equals("1") && !mode.equals("2")) {
                            System.out.println("Invalid input");
                            System.out.println("Please enter the mode of purchase:");
                            System.out.println("1. Market");
                            System.out.println("2. Limit");
                            mode = scanner.nextLine();
                        }
                        if (mode.equals("1")) {
                            System.out.println("Please enter the Total Money you want to spend:");
                            int money = scanner.nextInt();
                            scanner.nextLine();
                            trader.buyAssetMarketMode(asset, money);
                        } else {
                            System.out.println("Please enter the amount you want to buy:");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Please enter the limit price:");
                            double limitPrice = scanner.nextDouble();
                            scanner.nextLine();
                            trader.buyAssetLimit(asset, amount, limitPrice, false, null);
                        }
                    }

                    break;
                case "4":
                    sellAsset(scanner, trader);
                    break;
                case "5":
                    trader.printPortfolio();
                    break;
                case "6":
                    plotPortfolio(trader);
                    break;
                case "7":
                    trader.getPortfolio().plotPortfolioBarChart();
                    break;
                case "8":
                    depositMoney(scanner, trader);
                    break;
                case "9":
                    withdrawMoney(scanner, trader);
                    break;
                case "10":
                    removeAccount(scanner, trader);
                    break;
                case "11":
                    plotAssetsByPrice();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            printTraderManu();
            choice = scanner.nextLine();
        }
    }

    private void plotPortfolio(Trader trader) {
        trader.getPortfolio().plotPortfolioPieChart();
    }

    private void depositMoney(Scanner scanner, Trader trader) {
        System.out.println("Please enter the amount of money you want to deposit:");
        double amount = Double.parseDouble(scanner.nextLine());
        trader.deposit(amount);
    }

    private boolean sellAsset(Scanner scanner, Trader trader) {
        System.out.println("Please enter the Symbol of the asset you want to sell:");
        String symbol = scanner.nextLine();
        if (searchAsset(symbol) == null) {
            System.out.println("Asset not found");
            return false;
        } else {
            System.out.println("Please enter the mode of sale:");
            System.out.println("1. Market");
            System.out.println("2. Limit");

            String mode = scanner.nextLine();
            while (!mode.equals("1") && !mode.equals("2")) {
                System.out.println("Invalid input");
                System.out.println("Please enter the mode of sale:");
                System.out.println("1. Market");
                System.out.println("2. Limit");
                mode = scanner.nextLine();
            }
            if (mode.equals("1")) {
                System.out.println("Please enter the Total amount you want to sell:");
                int amount = scanner.nextInt();
                scanner.nextLine();
                Asset asset = searchAsset(symbol);
                trader.sellAsset(asset, amount, asset.getPrice(), "market", false, null);
                return true;
            } else {
                System.out.println("Please enter the amount you want to sell:");
                int amount = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Please enter the limit price:");
                double limitPrice = scanner.nextDouble();
                scanner.nextLine();
                Asset asset = searchAsset(symbol);
                trader.sellAsset(asset, amount, limitPrice, "limit", false, null);
                return true;
            }
        }
    }

    private void plotAsset(Scanner scanner) {
        System.out.println("Please enter the symbol of the asset you want to plot:");
        String name = scanner.nextLine();
        Asset asset = searchAsset(name);
        if (asset == null) {
            System.out.println("Asset not found");
        } else {
            asset.plot();
        }
    }

    private void searchForAsset(Scanner scanner) {
        System.out.println("Please enter the symbol of the asset you want to search for:");
        String name = scanner.nextLine();
        Asset asset = searchAsset(name);
        if (asset == null) {
            System.out.println("Asset not found");
        } else {
            System.out.println(asset);
        }
    }

    private Asset searchAsset(String name) {
        for (Asset asset : assets) {
            if (asset.getSymbol().equals(name)) {
                return asset;
            }
        }
        return null;
    }

    private void sortAssetsByPrice() {
        Collections.sort(assets, new Comparator<Asset>() {
            @Override
            public int compare(Asset o1, Asset o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }

    private void sortAssetsByName() {
        Collections.sort(assets, new Comparator<Asset>() {
            @Override
            public int compare(Asset o1, Asset o2) {
                return o1.getSymbol().compareTo(o2.getSymbol());
            }
        });
    }

    private void printAssets() {
        for (Asset asset : assets) {
            System.out.println(asset);
        }
    }

    private void sortAssetsByAmount() {
        Collections.sort(assets, new Comparator<Asset>() {
            @Override
            public int compare(Asset o1, Asset o2) {
                return Integer.compare(o1.getAvailableAmount(), o2.getAvailableAmount());
            }
        });
    }

    private void withdrawMoney(Scanner scanner, Trader trader) {
        System.out.println("Please enter the amount of money you want to withdraw:");
        String amount = scanner.nextLine();
        while (!isNumeric(amount)) {
            System.out.println("Invalid input");
            System.out.println("Please enter the amount of money you want to withdraw:");
            amount = scanner.nextLine();
        }
        double amountDouble = Double.parseDouble(amount);
        if (amountDouble > trader.getBalance()) {
            System.out.println("You don't have enough money");
        } else {
            trader.withdraw(amountDouble);
            System.out.println("You withdrew " + amountDouble + " dollars");
        }
    }

    private boolean isNumeric(String amount) {
        try {
            Double.parseDouble(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void removeAccount(Scanner scanner, Trader trader) {
        System.out.println("Are you sure you want to remove your account? (y/n)");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            removeTrader(trader);
        }
    }

    public void signIn(Scanner scanner) {
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        Trader trader = findTrader(username, password);

        if (trader == null) {
            System.out.println("Invalid username or password");
        } else {
            if (username.equals("admin")) {
                adminMenu(scanner);
            } else {
                traderMenu(scanner, trader);
            }
        }

    }

    private Trader findTrader(String username, String password) {
        for (Trader trader : traders) {
            if (trader.getUsername().equals(username) && trader.getPassword().equals(password)) {
                return trader;
            }
        }
        return null;
    }

    public void run() {
//        this is the update price method, it will be called every 1 seconds
        while (true) {
            updatePrice();
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePrice() {
        for (Asset asset : assets) {
            asset.update();
            for (Transaction transaction : asset.getBuyers()) {
                Boolean isDone = transaction.Try();
                if (isDone) {
                    asset.getBuyers().remove(transaction);
                }
            }
        }
    }

    public void takeManagementPrice() {
        Double managementPrice = 0.0;
        for (Trader trader : traders) {
            managementPrice += trader.getManagementPrice();
        }
        System.out.println("The total management price is " + managementPrice);
        this.balance += managementPrice;
    }

    public void LoadAssetsFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] assetInfo = line.split(",");
                String symbol = assetInfo[0];
                String name = assetInfo[1];
                double price = Double.parseDouble(assetInfo[2]);
                double mean = Double.parseDouble(assetInfo[3]);
                double std = Double.parseDouble(assetInfo[4]);
                Integer amount = Integer.parseInt(assetInfo[5]);
                String type = assetInfo[6];
                if (type.equals("s")) {
                    assets.add(new Stock(symbol, price, mean, std, amount, name));
                } else if (type.equals("c")) {
                    assets.add(new VirtualCurrency(symbol, price, mean, std, amount, name));
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadTradersFromFile(String tradersPath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tradersPath));
            String line = reader.readLine();
            while (line != null) {
                String[] traderInfo = line.split(",");
                String username = traderInfo[0];
                String password = traderInfo[1];
                traders.add(new Trader(username, password, this.getManagementPrice(), this.getProfitForTax(), this));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void plotAssetsByPrice() {
        this.sortAssetsByPrice();
        List<Double> prices = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Asset asset : assets) {
            prices.add(asset.getPrice());
            names.add(asset.getSymbol());
        }
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Portfolio").xAxisTitle("Asset").yAxisTitle("Value").build();
        chart.addSeries("Assets", names, prices);
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );

    }

    private double getProfitForTax() {
        return this.profitForTax;
    }

    private double getManagementPrice() {
        return this.managementPrice;
    }


}
