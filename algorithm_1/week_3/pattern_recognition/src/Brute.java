import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Brute {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();

        List<Point> pointColl = new ArrayList<Point>();
        for (int i = 0; i < n; ++i) {
            int x = in.readInt();
            int y = in.readInt();
            pointColl.add(new Point(x, y));
        }
        in.close();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Collections.sort(pointColl);

        for (int i = 0; i < pointColl.size(); ++i) {
            for (int j = i + 1; j < pointColl.size(); ++j) {
                for (int p = j + 1; p < pointColl.size(); ++p) {
                    for (int q = p + 1; q < pointColl.size(); ++q) {
                        Point a = pointColl.get(i);
                        Point b = pointColl.get(j);
                        Point c = pointColl.get(p);
                        Point d = pointColl.get(q);

                        if (a.SLOPE_ORDER.compare(b, c) == 0 && a.SLOPE_ORDER.compare(c, d) == 0) {
                            System.out.println(String.format("%s -> %s -> %s -> %s", a, b, c, d));
                            a.draw();
                            b.draw();
                            c.draw();
                            d.draw();
                            a.drawTo(d);
                        }
                    }
                }
            }
        }
    }
}
