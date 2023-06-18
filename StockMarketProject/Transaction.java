package StockMarketProject;

import java.util.Date;

public class Transaction {
	
	private Trader _buyingTrader;
	private Trader _sellingTrader;
	private Asset _asset;
	private Boolean _type;
	private Double _price;
	private Double _amount;
	private Date _date;
	
	
	// @Override
	public String toString() {
		return "Buying Trader: " + this._buyingTrader.getUsername() +
			   "Selling Trader: " + this._sellingTrader.getUsername() +
			   this._asset.toString() + "type: " + this._type + "price: " +
			   + this._amount + this._price + this._date.toString();
	}
}
