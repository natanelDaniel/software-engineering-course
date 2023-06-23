package StockMarketProjectCopy;

import java.util.Random;

public class VirtualCurrency extends Asset implements IUpdatable{

    private String currencyName;
        public VirtualCurrency(String symbol, double price, double mean, double std, int availableAmount, String currencyName) {
            super(symbol, price, mean, std, availableAmount);
            this.currencyName = currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        @Override
        public String toString() {
            return "VirtualCurrency{" + "currencyName=" + currencyName
                    + super.toString() + '}';
        }

        @Override
        public void update() {
//            random normal distribution

            double change = new Random().nextGaussian() * getStd() + getMean();
            double noise = new Random().nextGaussian() * 0.1;
            double newPrice = getPrice() + (getPrice() * (change / 100)) + noise;
            getHistoryPrices().add(getPrice());
            setPrice(newPrice);
        }
}
