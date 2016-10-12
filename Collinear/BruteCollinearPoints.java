import java.util.Arrays;
import java.util.ArrayList;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) throws NullPointerException,
                                                    IllegalArgumentException{
        if (points == null) {
            throw new NullPointerException();
        }
        int n = points.length;
        lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    for (int l = k+1; l < n; l++) {
                        if(points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
                        && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])){
                            Point[] collin = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(collin);
                            lineSegments.add(new LineSegment(collin[0], collin[3]));
                        }

                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] a = new LineSegment[numberOfSegments()];
        return lineSegments.toArray(a);
    }

    public static void main(String[] args) {
        Point[] points = {new Point(1, 1), new Point(1, 0), new Point(1, -1), new Point(1, 2)
                            , new Point(2, 2), new Point(3, 3), new Point(4, 4)};
        BruteCollinearPoints s = new BruteCollinearPoints(points);
        System.out.println(s.numberOfSegments());
        LineSegment[] l = s.segments();
        for (int i = 0; i < l.length; i++) {
            System.out.println(l[i]);
        }
    }
}
