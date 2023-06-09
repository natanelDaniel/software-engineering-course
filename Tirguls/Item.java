package Tirguls;

public interface Item extends Cloneable {
    String getID();
    double getWeight();
    Object clone() throws CloneNotSupportedException;
}
