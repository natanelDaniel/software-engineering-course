package StockMarketProject;

public class VirtualCurrency extends Asset implements IUpdatable{

    private String currencyName;
        public VirtualCurrency(String symbol, double price, double mean, double std, int availableAmount) {
            super(symbol, price, mean, std, availableAmount);
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
                    + ", symbol=" + getSymbol()
                    + ", price=" + getPrice()
                    + ", availableAmount=" + getAvailableAmount()
                    + '}';
        }

        @Override
        public void update() {
            double newPrice = getPrice() + (getPrice() * (getMean() / 100));
            setPrice(newPrice);
        }
}
