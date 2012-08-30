/*************************************************************************
 * Name: Shengzhe Yao
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.equals(that)) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (this.y - that.y) / (this.x - that.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
        return (this.y == that.y) ? (this.x - that.x) : (this.y - that.y);
    }

    // return string representation of this point
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

    private class SlopeOrderComparator implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            double slopeToA = Point.this.slopeTo(a);
            double slopeToB = Point.this.slopeTo(b);
            if (slopeToA < slopeToB) {
                return -1;
            } else if (slopeToA > slopeToB) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}