package Tirguls;

public class Book implements Item {
    private String id;
    private int pages;
    public static final double WEIGHT_PER_PAGE = 0.0025;

    public Book(String id, int pages) {
        this.id = id;
        this.pages = pages;
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        return (Book) super.clone();
    }

    public String getID() {
        return id;
    }

    public double getWeight() {
        return pages * WEIGHT_PER_PAGE;
    }

}
