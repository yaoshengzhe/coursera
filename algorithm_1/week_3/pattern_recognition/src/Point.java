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

    private final int x; // x coordinate
    private final int y; // y coordinate

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
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return 0;
        }
        else {
            return (double) (this.y - that.y) / (this.x - that.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
        if (this.y == that.y) {
            return this.x - that.x;
        } else {
            return this.y - that.y;
        }
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
            if (slopeToA == Double.NEGATIVE_INFINITY && slopeToB == Double.NEGATIVE_INFINITY) {
                return 0;
            }

            if (slopeToA == Double.POSITIVE_INFINITY && slopeToB == Double.POSITIVE_INFINITY) {
                return 0;
            }

            if (Math.abs(slopeToA - slopeToB) < 1e-6) {
                return 0;
            } else if (slopeToA < slopeToB) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}