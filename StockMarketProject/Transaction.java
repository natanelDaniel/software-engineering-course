package StockMarketProject;

import java.util.Date;

public class Transaction {
	private static int counter = 0;  // Static counter to generate unique IDs
	private Integer _id;
	private Trader _buyingTrader;
	private Trader _sellingTrader;
	private Asset _asset;
	private Boolean _type;
	private Double _price;
	private Double _amount;
	private Date _date;

    public Transaction() {
        this._id = 0;
        this._buyingTrader = null;
        this._sellingTrader = null;
        this._asset = null;
        this._type = false;
        this._price = 0.0;
        this._amount = 0.0;
        this._date = null;
    }
    
    public Transaction(Trader sellingTrader, Asset asset, String type, Double price, Double amount, Date date) {
        this._id = generateID();
        this._sellingTrader = sellingTrader;
        this._buyingTrader = null;  // You can set the buying trader later if needed
        this._asset = asset;
        this._type = type.equalsIgnoreCase("sell");
        this._price = price;
        this._amount = amount;
        this._date = date;
    }

    public String getID() {
        return "" + this._id;
    }

    public Double getPrice() {
        return this._price;
    }
    
    public void setBuyingTrader(Trader buyingTrader) {
        this._buyingTrader = buyingTrader;
    }

    // Method to generate a unique ID
    private static int generateID() {
        return ++counter;  // Increment the counter and return the updated value
    }
    
    // @Override
    public String toString() {
        return "ID: " + this._id + " Buying Trader: " + this._buyingTrader.getUsername()
                + " Selling Trader: " + this._sellingTrader.getUsername() + "\n"
                + this._asset.toString() + " Type: " + (this._type ? "Sell" : "Buy") + " Price: "
                + this._amount + " " + this._price + " " + this._date.toString();
    }
}
