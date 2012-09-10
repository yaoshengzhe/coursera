
public class LineSegment {

    private Point2D start;
    private Point2D end;
    private String text = null;

    public LineSegment(Point2D start, Point2D end) {
        if (start == end) {
            throw new IllegalArgumentException("start and end points are same");
        }
        this.start = start;
        this.end = end;
    }

    public LineSegment(Point2D start, Point2D end, String text) {
        this(start, end);
        this.text = text;
    }

    public void draw() {
        start.drawTo(end);
        if (text != null) {
            StdDraw.text( (start.x() + end.x()) / 2 - 1, (start.y() + end.y()) / 2 - 1, text);
        }
    }
}
