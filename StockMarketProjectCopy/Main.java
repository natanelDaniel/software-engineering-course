package StockMarketProjectCopy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            StockMarket stockMarket = new StockMarket();
            String AssetsPath = "StockMarketProjectCopy\\Tests\\assets.txt";
            stockMarket.LoadAssetsFromFile(AssetsPath);
            String TradersPath = "StockMarketProjectCopy\\Tests\\traders.txt";
            stockMarket.LoadTradersFromFile(TradersPath);

            String test1 = "StockMarketProject\\Tests\\StockMarketTest.txt";
            Boolean fromFile = false;
            Scanner scanner;
            if (fromFile) {
                try {
                    scanner = new Scanner(new FileInputStream(test1));
                    stockMarket.menu(scanner);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                scanner = new Scanner(System.in);
                stockMarket.menu(scanner);
            }
            System.out.println("Bye Bye");
        }
}
