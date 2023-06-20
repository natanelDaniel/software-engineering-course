package StockMarketProject;

// class Stock inherits from Asset

public class Stock extends Asset implements IUpdatable {

    private String companyName;

    public Stock(String symbol, double price, double mean, double std, int availableAmount) {
        super(symbol, price, mean, std, availableAmount);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return "Stock{" + "companyName=" + companyName
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
