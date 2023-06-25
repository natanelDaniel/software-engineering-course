package StockMarketProject;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.XChartSeriesColors;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Portfolio {
    private HashMap<String, Asset> assetsMap;
    private HashMap<String, Integer> amountMap;
    private HashMap<String, Double> totalBuyingPricePerAssetMap;

    public Portfolio() {
        assetsMap = new HashMap<>();
        amountMap = new HashMap<>();
        totalBuyingPricePerAssetMap = new HashMap<>();
    }
    public double getBalanceInMarket() {
        double balance = 0;

        for (String assetName : assetsMap.keySet()) {
            Asset asset = assetsMap.get(assetName);
            int amount = amountMap.get(assetName);
            double currentPrice = asset.getPrice();
            balance += amount * currentPrice;
        }
        return balance;
    }
    public void addAsset(Asset asset, int amount, double buyingPrice) {
        String assetName = asset.getSymbol();

        if (assetsMap.containsKey(assetName)) {
            // Asset already exists in the portfolio, update the amount and total buying price
            int currentAmount = amountMap.get(assetName);
            double currentTotalBuyingPrice = totalBuyingPricePerAssetMap.get(assetName);

            int updatedAmount = currentAmount + amount;
            double updatedTotalBuyingPrice = currentTotalBuyingPrice + (buyingPrice * amount);

            amountMap.put(assetName, updatedAmount);
            totalBuyingPricePerAssetMap.put(assetName, updatedTotalBuyingPrice);
        } else {
            // Asset doesn't exist in the portfolio, add it with the provided amount and buying price
            assetsMap.put(assetName, asset);
            amountMap.put(assetName, amount);
            totalBuyingPricePerAssetMap.put(assetName, buyingPrice * amount);
        }
    }

    public void removeAsset(Asset asset, int amount, double sellingPrice) {
        String assetName = asset.getSymbol();

        if (assetsMap.containsKey(assetName)) {
            int currentAmount = amountMap.get(assetName);

            if (currentAmount == amount) {
                // Remove the asset completely from the portfolio
                assetsMap.remove(assetName);
                amountMap.remove(assetName);
                totalBuyingPricePerAssetMap.remove(assetName);
            } else {
                // Update the amount and total buying price of the asset
                int updatedAmount = currentAmount - amount;
                double currentTotalBuyingPrice = totalBuyingPricePerAssetMap.get(assetName);

                // Calculate the updated total buying price based on the remaining amount
                double updatedTotalBuyingPrice = currentTotalBuyingPrice - (sellingPrice * amount);

                amountMap.put(assetName, updatedAmount);
                totalBuyingPricePerAssetMap.put(assetName, updatedTotalBuyingPrice);
            }
        }
    }
    public int getAssetAmount(Asset asset) {
        String name = asset.getSymbol();
        if (amountMap.containsKey(name)) {
            return amountMap.get(name);
        }

        return 0; // If the asset doesn't exist in the portfolio, return 0 as the amount
    }

    public double getAveragePrice(Asset asset) {
        String name = asset.getSymbol();
        if (amountMap.containsKey(name)) {
            int amount = amountMap.get(name);
            double totalBuyingPrice = totalBuyingPricePerAssetMap.get(name);
            return totalBuyingPrice / amount;
        }
        return 0.0; // If the asset doesn't exist in the portfolio, return 0 as the average price
    }

    public double getTotalValue(String name) {
        if (assetsMap.containsKey(name)) {
            int amount = amountMap.get(name);
            double assetPrice = assetsMap.get(name).getPrice();
            return amount * assetPrice;
        }
        return 0.0; // If the asset doesn't exist in the portfolio, return 0 as the total value
    }

    public double getTotalEarnings(){
        double totalEarnings = 0;
        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double totalBuyingPrice = totalBuyingPricePerAssetMap.get(assetName);
            totalEarnings += (amount * assetPrice) - totalBuyingPrice;
        }
        return totalEarnings;
    }

    public double getTotalEarningsPercentage(){
        double totalEarnings = getTotalEarnings();
        double totalBuyingPrice = getTotalBuyingPrice();
        return totalEarnings / totalBuyingPrice * 100.0;
    }

    private double getTotalBuyingPrice() {
        double totalBuyingPrice = 0;
        for (String assetName : assetsMap.keySet()) {
            double totalBuyingPricePerAsset = totalBuyingPricePerAssetMap.get(assetName);
            totalBuyingPrice += totalBuyingPricePerAsset;
        }
        return totalBuyingPrice;
    }

    public void plotPortfolioPieChart() {
//        make pie chart with XChart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Portfolio").build();
        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double assetBalancePercentage = (amount * assetPrice) / getBalanceInMarket() * 100.0;
            chart.addSeries(assetName, assetBalancePercentage);
        }
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    public String toString(){
        String str = "Portfolio:\n";
        str += "Total balance in market [$]: " + Math.round(getBalanceInMarket() * 100.0) / 100.0 + "\n";
        str += "Total Earnings [$]: " + Math.round(getTotalEarnings() * 100.0) / 100.0 + "\n";
        str += "Total Earnings Percentage [$]: " + Math.round(getTotalEarningsPercentage() * 100.0) / 100.0 + "%\n";
        str += "Asset\t\tValue\t\t";
        str += "DifferenceFromBuying [%]\t\t";
        str += "Amount\t\t";
        str += "TotalEarnings [$]\n";
        str += "--------------------------------------------------\n";
        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double assetBalancePercentage = (amount * assetPrice);
            double assetBuyingPrice = totalBuyingPricePerAssetMap.get(assetName);
            double difference = assetBalancePercentage / assetBuyingPrice * 100.0 - 100.0;
            double totalEarnings = assetBalancePercentage - assetBuyingPrice;
//            round
            assetBalancePercentage = Math.round(assetBalancePercentage * 100.0) / 100.0;
            difference = Math.round(difference * 100.0) / 100.0;
            totalEarnings = Math.round(totalEarnings * 100.0) / 100.0;
            str += assetName + "\t\t" + assetBalancePercentage + "\t\t" + difference + "\t\t" + amount + "\t\t" + totalEarnings + "\n";
        }
        return str;
    }

    public void plotPortfolioBarChart(){
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Portfolio").xAxisTitle("Asset").yAxisTitle("Value").build();
        List<String> assetNames = new ArrayList<>();
        List<Double> assetValues = new ArrayList<>();
        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double assetBalancePercentage = (amount * assetPrice);
            assetNames.add(assetName);
            assetValues.add(assetBalancePercentage);
        }
        chart.addSeries("Assets", assetNames, assetValues);
        JFrame frame = new SwingWrapper(chart).displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    public void printPortfolio() {
        System.out.println(this);
    }

}
