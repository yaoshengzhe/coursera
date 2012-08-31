import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Fast {

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
        if (pointColl.size() < 3) {
            return;
        }

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Point[] pointArr = pointColl.toArray(new Point[0]);
        Arrays.sort(pointArr);
        Set<String> colinearSegmentSet = new TreeSet<String>();

        for (Point p : pointArr) {
            Collections.sort(pointColl, p.SLOPE_ORDER);
            Point prevPoint = pointColl.get(1);
            int start = 0;
            int end = 1;
            int max = -1;

            for (int i = 1; i < pointColl.size(); ++i) {
                Point curPoint = pointColl.get(i);
                if (p.SLOPE_ORDER.compare(prevPoint, curPoint) == 0) {
                    end++;
                } else {
                    if (end - start > 2) {
                        outputSegment(pointColl, start, end, colinearSegmentSet);
                    }
                    start = i;
                    end = i + 1;
                }

                prevPoint = curPoint;
            }

            if (end - start > 2) {
                outputSegment(pointColl, start, end, colinearSegmentSet);
            }
        }
    }

    private static void outputSegment(List<Point> pointColl, int start, int end, Set<String> colinearSegmentSet) {
        List<Point> subList = new ArrayList<Point>();
        for (int i = start; i < end; ++i) {
            subList.add(pointColl.get(i));
        }
        if (start > 0) {
            subList.add(pointColl.get(0));
        }
        if (subList.size() > 3) {

            Collections.sort(subList);
            String key = colinearSegmentToString(subList);
            if (!colinearSegmentSet.contains(key)) {
                drawPointColl(subList);
                colinearSegmentSet.add(key);
            }
        }
    }

    private static String colinearSegmentToString(List<Point> segment) {
        StringBuilder builder = new StringBuilder();
        for (Point p : segment) {
            builder.append(p);
        }
        return builder.toString();
    }

    private static void drawPointColl(Collection<Point> pointColl) {
        Iterator<Point> itr = pointColl.iterator();
        Point prevPoint = itr.next();

        System.out.print(prevPoint);
        prevPoint.draw();
        Point first = prevPoint;
        Point last = null;
        while (itr.hasNext()) {
            Point curPoint = itr.next();
            System.out.print(" -> " + curPoint);
            curPoint.draw();
            prevPoint = curPoint;
            last = curPoint;
        }

        if (last != null) {
            first.drawTo(last);
        }
        System.out.println();
    }
}
