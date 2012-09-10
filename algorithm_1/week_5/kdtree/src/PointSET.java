import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private Set<Point2D> pointSet = null;

    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        pointSet.add(new Point2D(p.x(), p.y()));
    }

    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    public void draw() {
        StdDraw.setScale(0, 1);
        StdDraw.setPenRadius(0.02);
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        final List<Point2D> pointInRect = new ArrayList<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                pointInRect.add(p);
            }
        }
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return pointInRect.iterator();
            }
        };
    }

    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double nearestDist = -1;
        for (Point2D q : pointSet) {
            double dist = p.distanceTo(q);
            if (nearestDist == -1 || dist < nearestDist) {
                nearestDist = dist;
                nearestPoint = q;
            }
        }
        return nearestPoint;
    }

}
