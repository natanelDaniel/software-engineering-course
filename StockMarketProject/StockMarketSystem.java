package StockMarketProject;
import java.util.ArrayList;
import java.util.HashSet;

public class StockMarketSystem {
    private ArrayList<Asset> assets;
    private ArrayList<Trader> traders;

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
    public void menu() {
        // TODO implement here
    }
    public void signUp() {
        // TODO implement here
    }
    public void signin() {
        // TODO implement here
    }
    public void updatePrices() {
        // TODO implement here
    }
    public void takeManagmentPrice() {
        // TODO implement here
    }
}
