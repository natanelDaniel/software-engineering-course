package Examples;

public class Point {
    private int x;
    private int y;
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

    public static void main(String[] args) {
        Point p1 = new Point();
        p1.x = 5;
        p1.y = 10;
        Point p2 = new Point();
        p2.x = 3;
        p2.y = 7;
        printPoint(p1);
        printPoint(p2);
        p1 = p2;
        printPoint(p1);
        incPoint(p1);
        incPoint(p2);
        printPoint(p1);
        printPoint(p2);
        System.out.println(equals(p1, p2));
        System.out.println(distance(p1, p2));
        Point p3 = new Point();
        p3.x = 0;
        p3.y = 0;

    }
}
