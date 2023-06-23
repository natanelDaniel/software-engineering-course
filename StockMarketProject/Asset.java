package StockMarketProject;
import org.knowm.xchart.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.colors.*;

import javax.swing.*;

// window listener
class WindowListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

public abstract class Asset implements IUpdatable {

    private String _symbol;
    private double _price;
    final private double _mean;
    final private double _std;
    private ArrayList<Double> _historyPrices;
    private ArrayList<Transaction> _sellers;
    private ArrayList<Transaction> _buyers;
    private int _availableAmount;

    public Asset(String symbol, double price, double mean, double std, int availableAmount) {
        this._symbol = symbol;
        this._price = price;
        this._mean = mean;
        this._std = std;
        this._availableAmount = availableAmount;
        this._historyPrices = new ArrayList<Double>();
        this._sellers = new ArrayList<Transaction>();
        this._buyers = new ArrayList<Transaction>();
    }

    public String getSymbol() {
        return _symbol;
    }

    public double getPrice() {
        return _price;
    }

    public double getMean() {
        return _mean;
    }

    public double getStd() {
        return _std;
    }

    public int getAvailableAmount() {
        return _availableAmount;
    }

    public ArrayList<Transaction> getSellers() {
        return _sellers;
    }

    public ArrayList<Transaction> getBuyers() {
        return _buyers;
    }

    public void setPrice(double price) {
        this._price = price;
    }

    public ArrayList<Double> getHistoryPrices() {
        return _historyPrices;
    }

    public void updateAvailableAmount(int availableAmount) {
        this._availableAmount = availableAmount;
    }

    public Transaction containsByIdBuyers(String id) {
        for (Transaction transaction : this._buyers) {
            if (transaction.getID().equals(id))
                return transaction;
        }
        return null;
    }

    public void addBuyer(Transaction transaction) {
        Transaction transaction2 = this.containsByIdBuyers(transaction.getID());
        if (transaction2 != null) {
            System.out.println("Cannot add, already in the queue");
            return;
        }
        this._buyers.add(transaction);
    }

    public void removeByIdBuyers(String id) {
        Transaction transaction = this.containsByIdBuyers(id);
        if (transaction == null) {
            System.out.println("Not in queue - cannot remove");
            return;
        }
        this._buyers.remove(transaction);
    }

    public Transaction containsByIdSellers(String id) {
        for (Transaction transaction : this._sellers) {
            if (transaction.getID().equals(id))
                return transaction;
        }
        return null;
    }

    public void addSeller(Transaction transaction) {
        Transaction transaction2 = this.containsByIdSellers(transaction.getID());
        if (transaction2 != null) {
            System.out.println("Cannot add, already in the queue");
            return;
        }
        this._sellers.add(transaction);
    }

    public void removeByIdSellers(String id) {
        Transaction transaction = this.containsByIdSellers(id);
        if (transaction == null) {
            System.out.println("Not in queue - cannot remove");
            return;
        }
        this._sellers.remove(transaction);
    }

    public void addPriceToHistory(double price) {
        this._historyPrices.add(price);
    }

    //sort the sellers queue by price
    public void sortSellers() {
        for (int i = 0; i < this._sellers.size(); i++) {
            for (int j = 0; j < this._sellers.size() - 1; j++) {
                if (this._sellers.get(j).getPrice() > this._sellers.get(j + 1).getPrice()) {
                    Transaction temp = this._sellers.get(j);
                    this._sellers.set(j, this._sellers.get(j + 1));
                    this._sellers.set(j + 1, temp);
                }
            }
        }
    }

    //sort the buyers queue by price
    public void sortBuyers() {
        for (int i = 0; i < this._buyers.size(); i++) {
            for (int j = 0; j < this._buyers.size() - 1; j++) {
                if (this._buyers.get(j).getPrice() < this._buyers.get(j + 1).getPrice()) {
                    Transaction temp = this._buyers.get(j);
                    this._buyers.set(j, this._buyers.get(j + 1));
                    this._buyers.set(j + 1, temp);
                }
            }
        }
    }

    public void plot() {
//        make LineChart with the history of the asset, from  xchart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Stock Price History")
                .xAxisTitle("Time")
                .yAxisTitle("Price")
                .build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                .setMarkerSize(4)
                .setChartTitleVisible(true)
                .setLegendPosition(Styler.LegendPosition.InsideNW)
                .setChartTitleVisible(true)
                .setChartTitlePadding(15)
                .setPlotBackgroundColor(XChartSeriesColors.BLACK)
                .setPlotBorderColor(XChartSeriesColors.GREEN);

        // Create Series
        XYSeries series = chart.addSeries( this.getSymbol() + "Stock Price", null, this._historyPrices);

        // Customize Series
        series.setLineColor(XChartSeriesColors.GREEN);

        JFrame frame = new SwingWrapper(chart).displayChart();
        javax.swing.SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );
    }

    public void update() {
    }

    public String toString() {
        return "Symbol: " + this._symbol + ", Price: " + this._price + ", Available Amount: " + this._availableAmount;
    }


//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Asset) {
//            Asset asset = (Asset)obj;
//            return asset.getSymbol() == this.getSymbol();
//        }
//        return false;
//    }

}


