package StockMarketProjectCopy;

import java.util.Date;

public class Transaction {
	private static int counter = 0;  // Static counter to generate unique IDs
	private Integer _id;
	private Trader _Trader;
	private Asset _asset;
	private String _type;
	private Double _price;
	private int _amount;
	private Date _date;

    
    public Transaction(Trader sellingTrader, Asset asset, String type, Double price, int amount, Date date) {
        this._id = generateID();
        this._Trader = sellingTrader;
        this._asset = asset;
        this._type = type;
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

    // Method to generate a unique ID
    private static int generateID() {
        return ++counter;  // Increment the counter and return the updated value
    }
    
    // @Override
    public String toString() {
        return "Transaction{" + "ID=" + _id
                + ", Trader=" + _Trader
                + ", Asset=" + _asset
                + ", Type=" + _type
                + ", Price=" + _price
                + ", Amount=" + _amount
                + ", Date=" + _date
                + '}';
    }

    public Trader getTrader() {
        return _Trader;
    }

    public boolean Try(){
        if (_type == "Buy"){
            return _Trader.buyAssetLimit(_asset, _amount, _price, true, this);
    }
        else if (_type == "Sell"){
            return _Trader.sellAsset(_asset, _amount, _price, "limit", true, this);
        }
        else{
            return false;
        }
    }

    public void setAmount(int i) {
        this._amount = i;
    }
}
