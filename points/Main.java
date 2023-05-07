package points;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Point p1 = new Point(1, 2);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(new Point(3, 4));
        points.add(new Point(5, 6));
        points.add(new Point(7, 8));

        System.out.println(points);

    }
}
