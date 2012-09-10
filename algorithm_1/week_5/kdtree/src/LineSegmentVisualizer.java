public class LineSegmentVisualizer {

    public static void main(String[] args) {

        StdDraw.show(0);
        while (true) {
            StdDraw.clear();

            StdDraw.setScale(0, 20);
            StdDraw.setPenRadius(0.01);

            // A
            new LineSegment(new Point2D(10, 3), new Point2D(10, 18), "A").draw();
            // B
            new LineSegment(new Point2D(4, 2), new Point2D(4, 13), "B").draw();
            // C
            new LineSegment(new Point2D(15, 10), new Point2D(15, 18), "C").draw();
            // D
            new LineSegment(new Point2D(11, 12), new Point2D(19, 12), "D").draw();
            // E
            new LineSegment(new Point2D(0, 6), new Point2D(16, 6), "E").draw();
            // F
            new LineSegment(new Point2D(12, 16), new Point2D(17, 16), "F").draw();
            // G
            new LineSegment(new Point2D(1, 1), new Point2D(8, 1), "G").draw();
            // H
            new LineSegment(new Point2D(14, 14), new Point2D(18, 14), "H").draw();

            StdDraw.show(50);
        }

    }
}