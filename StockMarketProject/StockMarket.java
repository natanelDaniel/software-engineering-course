package StockMarketProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class StockMarket {
    private ArrayList<Asset> assets;
    private ArrayList<Trader> traders;

    private double managementPrice;

    private double profitForTax;


    private double balance;

    public StockMarket() {
        this.assets = new ArrayList<Asset>();
        this.traders = new ArrayList<Trader>();
        this.balance = 0;
    }

    public void addTrader(Trader trader) {
        traders.add(trader);
    }
    public void removeTrader(Trader trader) {
        traders.remove(trader);
    }
    public ArrayList<Trader> getTraders() {
        return this.traders;
    }
    public void addAsset(Asset asset) {
        assets.add(asset);
    }
    public void removeAsset(Asset asset) {
        assets.remove(asset);
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
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        while (! VerifyPassword(password))
        {
            System.out.println("Invalid password, password must contain at least 8 characters, " +
                    " at least one digit, at least one lower case letter, at least one upper case " +
                    " letter and at least one special character");
            System.out.println("Please enter your password:");
            password = scanner.nextLine();
        }


        Trader trader = new Trader(username, password, managementPrice, profitForTax, this);
        addTrader(trader);
        if (username.equals("admin")) {
            adminMenu(scanner, trader);
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

    private void adminMenu(Scanner scanner, Trader trader) {
        System.out.println("Welcome admin!");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Update prices");
        System.out.println("2. Take management fee");
        System.out.println("3. search for a trader");
        System.out.println("4. search for an asset");
        System.out.println("5. plot Asset");
        System.out.println("6. Add asset");
        System.out.println("7. Remove asset");
        System.out.println("8. Sort assets by name");
        System.out.println("9. Sort assets by price");
        System.out.println("10. Sort assets by amount");
        System.out.println("11. Load assets from file");
        System.out.println("12. Sign out");
        String choice = scanner.nextLine();
        while (!choice.equals("12")){
            switch (choice) {
                case "1":
                    updatePrices();
                    break;
                case "2":
                    takeManagementPrice();
                    break;
                case "3":
                    searchForTrader(scanner);
                    break;
                case "4":
                    searchForAsset(scanner);
                    break;
                case "5":
                    plotAsset(scanner);
                    break;
                case "6":
                    addAsset(scanner);
                    break;
                case "7":
                    removeAsset(scanner);
                    break;
                case "8":
                    sortAssetsByName();
                    break;
                case "9":
                    sortAssetsByPrice();
                    break;
                case "10":
                    sortAssetsByAmount();
                    break;
                case "11":
                    System.out.println("Please enter the file path:");
                    String filePath = scanner.nextLine();
                    LoadAssetsFromFile(filePath);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            adminMenu(scanner, trader);
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


    public void traderMenu(Scanner scanner, Trader trader) {
        System.out.println("Welcome " + trader.getUsername() + "!");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Search for an asset");
        System.out.println("2. Plot asset");
        System.out.println("3. Buy asset");
        System.out.println("4. Sell asset");
        System.out.println("5. Print portfolio");
        System.out.println("6. Plot portfolio");
        System.out.println("7. Deposit money");
        System.out.println("8. Withdraw money");
        System.out.println("9. Remove account");
        System.out.println("10. Sign out");
        String choice = scanner.nextLine();
        while (!choice.equals("10")){
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
                    if (searchAsset(symbol) == null) {
                        System.out.println("Asset not found");
                    } else {
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
                            System.out.println("Please enter the Total amount you want to buy:");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            Asset asset = searchAsset(symbol);
                            trader.buyAsset(asset, amount, asset.getPrice(), "market");
                        } else {
                            System.out.println("Please enter the amount you want to buy:");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Please enter the limit price:");
                            double limitPrice = scanner.nextDouble();
                            scanner.nextLine();
                            Asset asset = searchAsset(symbol);
                            trader.buyAsset(asset, amount, limitPrice, "limit");
                        }
                    }

                    break;
                case "4":
                    sellAsset(scanner, trader);
                    break;
                case "5":
                    printPortfolio(trader);
                    break;
                case "6":
                    plotPortfolio(trader);
                    break;
                case "7":
                    depositMoney(scanner, trader);
                    break;
                case "8":
                    withdrawMoney(scanner, trader);
                    break;
                case "9":
                    removeAccount(scanner, trader);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            traderMenu(scanner, trader);
        }
    }

    private void printPortfolio(Trader trader) {
        System.out.println(trader.getPortfolio());
    }

    private void plotPortfolio(Trader trader) {
        trader.getPortfolio().plotPortfolioPieChart();
    }

    private void depositMoney(Scanner scanner, Trader trader) {
        System.out.println("Please enter the amount of money you want to deposit:");
        double amount = Double.parseDouble(scanner.nextLine());
        trader.deposit(amount);
    }

    private void plotAsset(Scanner scanner) {
        System.out.println("Please enter the name of the asset you want to plot:");
        String name = scanner.nextLine();
        Asset asset = searchAsset(name);
        if (asset == null) {
            System.out.println("Asset not found");
        } else {
            asset.plot();
        }
    }

    private void searchForAsset(Scanner scanner) {
        System.out.println("Please enter the name of the asset you want to search for:");
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

    public void signIn(Scanner scanner){
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        Trader trader = findTrader(username, password);

        if (trader == null) {
            System.out.println("Invalid username or password");
        } else {
            if (username.equals("admin")) {
                adminMenu(scanner, trader);
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

    public void updatePrices() {
        // TODO implement here
    }
    public void takeManagementPrice() {
        for (Trader trader : traders) {
            this.balance += trader.getManagementPrice();
        }
    }

    public void LoadAssetsFromFile(String filePath) {
//        TODO: read from file
    }
}
