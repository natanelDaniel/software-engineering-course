package points;

public class Point implements Comparable<Point>{
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
    public void printPoint() {
        System.out.println(this);
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

    public boolean equals(Object o) {
        if ( o instanceof Point) {
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }

    public int compareTo(Point p) {
//        if (this.radius() > p.radius()) {
//            return 1;
//        } else if (this.radius() < p.radius()) {
//            return -1;
//        } else {
//            return 0;
//        }
        return (int) (this.radius() - p.radius());
    }


    public double radius() {
        return this.distance(new Point());
    }
    public double angle() {
        return Math.atan2(this.y,this.x);
    }
    public float distance(Point p1) {
        return (float) Math.sqrt(Math.pow(this.x - p1.x, 2) +
                Math.pow(this.y - p1.y, 2));
    }

    public static void incPoint(Point p) {
        p.x++;
        p.y++;
    }
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
