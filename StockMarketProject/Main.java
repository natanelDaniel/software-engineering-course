package StockMarketProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            // TODO Auto-generated method stub
            StockMarket stockMarket = new StockMarket();
            String AssetsPath = "Ex2\\Tests\\assets.txt";
            stockMarket.LoadAssetsFromFile(AssetsPath);

            String test1 = "StockMarketProject\\Tests\\StockMarketTest.txt";
            Boolean fromFile = true;
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
