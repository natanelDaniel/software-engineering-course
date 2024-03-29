package StockMarketProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            StockMarket stockMarket = new StockMarket();
            String AssetsPath = "StockMarketProject\\Tests\\assets.txt";
            stockMarket.LoadAssetsFromFile(AssetsPath);
            String TradersPath = "StockMarketProject\\Tests\\traders.txt";
            stockMarket.LoadTradersFromFile(TradersPath);

            String test1 = "StockMarketProject\\Tests\\StockMarketTest.txt";
            Boolean fromFile = false;
            Scanner scanner;
            if (fromFile) {
                try {
                    scanner = new Scanner(new FileInputStream(test1));
                    try {
                        stockMarket.menu(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    finally {
//                        stop the program
                        System.exit(0);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                scanner = new Scanner(System.in);
                try {
                    stockMarket.menu(scanner);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                finally {
//                        stop the program
                    System.exit(0);
                }
            }
        }
}
