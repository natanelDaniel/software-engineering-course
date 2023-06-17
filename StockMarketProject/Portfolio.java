package StockMarketProject;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


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
        DefaultPieDataset dataset = new DefaultPieDataset();

        double totalBalanceInMarket = getBalanceInMarket();

        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double assetBalancePercentage = (amount * assetPrice) / totalBalanceInMarket * 100.0;
            dataset.setValue(assetName, assetBalancePercentage);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Portfolio Overview", // Chart title
                dataset, // Dataset
                true, // Include legend
                true, // Include tooltips
                false // Include URLs
        );

        ChartFrame frame = new ChartFrame("Portfolio Chart", chart);
        frame.pack();
        frame.setVisible(true);
    }

    public String toString(String mode){
        String str = "Portfolio:\n";
        str += "Total balance in market: " + getBalanceInMarket() + "\n";
        str += "Total Earnings: " + getTotalEarnings() + "\n";
        str += "Total Earnings Percentage: " + getTotalEarningsPercentage() + "\n";
        str += "Asset\t\tValue\t\t";
        if(mode.equals("DifferenceFromBuying")){
            str += "DifferenceFromBuying\n";
        }
        else if(mode.equals("Amount")){
            str += "Amount\n";
        }
        else if(mode.equals("TotalEarnings")){
            str += "TotalEarnings\n";
        }
        else{
            throw new IllegalArgumentException("Invalid mode");
        }
        for (String assetName : assetsMap.keySet()) {
            int amount = amountMap.get(assetName);
            double assetPrice = assetsMap.get(assetName).getPrice();
            double assetBalancePercentage = (amount * assetPrice);
            double assetBuyingPrice = totalBuyingPricePerAssetMap.get(assetName);
            double difference = assetBalancePercentage / assetBuyingPrice * 100.0 - 100.0;
            double totalEarnings = assetBalancePercentage - assetBuyingPrice;

            if (mode.equals("DifferenceFromBuying")){
                str += assetName + "\t\t" + assetBalancePercentage + "\t\t" + difference + "\n";
            }
            else if(mode.equals("Amount")){
                str += assetName + "\t\t" + assetBalancePercentage + "\t\t" + amount + "\n";
            }
            else if(mode.equals("TotalEarnings")){
                str += assetName + "\t\t" + assetBalancePercentage + "\t\t" + totalEarnings + "\n";
            }
        }

        return str;
    }
}