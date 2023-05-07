package Tirguls;

public class Point {
    private double x;
    private double y;

    public Point() {
        this(0, 0);
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point p) {
        this(p.x, p.y);
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public static void printPoint(Point p) {
        System.out.println("(" + p.x + ", " + p.y + ")");
    }
    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static Point copyPoint(Point p) {
        Point newPoint = new Point();
        newPoint.x = p.x;
        newPoint.y = p.y;
        return newPoint;
    }

    public static boolean equals(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }
    public static float distance(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) +
                Math.pow(p1.y - p2.y, 2));
    }
    public static void incPoint(Point p) {
        p.x++;
        p.y++;
    }
}
