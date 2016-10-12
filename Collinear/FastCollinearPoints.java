import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class FastCollinearPoints {
    private ArrayList<MyLine> lines;
    private MyLine[] myLines;
    private ArrayList<LineSegment> lineSegments;

    private class MyLine extends LineSegment implements Comparable<MyLine>{
        double slope;
        private Point p;
        private Point q;
        public MyLine(Point p, Point q) {
            super(p, q);
            slope = p.slopeTo(q);
            this.p = p;
        }

        public int compareTo(MyLine that) {
            double slopeT = p.slopeTo(that.p);
            int compareT = p.compareTo(that.p);
            if (this.slope > that.slope) return 1;
            else if (this.slope < that.slope) return -1;
            else if ((slopeT < this.slope && slopeT > 0 && compareT > 0) || 
                     ((slopeT > this.slope || slopeT < 0) && compareT < 0) ||
                     (slopeT == 0 && compareT < 0)) return 1;
            else if (slopeT == this.slope) return 0;
            else return -1;
        }
    }

    public FastCollinearPoints(Point[] points) {
        int n = points.length;
        lines = new ArrayList<MyLine>();
        lineSegments = new ArrayList<LineSegment>();
        Arrays.sort(points); //to get natural order

        for (int i = 0; i < n; i ++) {
            Point thisPoint = points[i];
            //System.out.print("From " + thisPoint + "  : ");
            Point[] pts = new Point[n-i];
            for (int j = 0; j < n-i; j ++) { pts[j] = points[j+i]; }
            Arrays.sort(pts,  thisPoint.slopeOrder());
            boolean equal = false;
            int counter = 0;
            for(int j = 0; j < n-i; j ++){
                //System.out.print(pts[j]+ " ");
                if (!equal) {
                    counter = 1;
                    equal = true;
                } else {
                    counter++;
                }
                double slope1 = thisPoint.slopeTo(pts[j]);
                if ((j != n-1-i && slope1 != thisPoint.slopeTo(pts[j+1]))
                    || j == n-1-i) { 
                    equal = false;
                    if (counter >= 3) {
                        Point highestPoint = (thisPoint.compareTo(pts[j]) > 0) ? thisPoint : pts[j];
                        Point lowestPoint = (thisPoint.compareTo(pts[j+1-counter]) < 0) ? 
                                                                    thisPoint : pts[j+1-counter];
                        lines.add(new MyLine(lowestPoint, highestPoint));
                    }
                }
            }
            //System.out.println();
        }
        myLines = new MyLine[0];
        myLines = lines.toArray(myLines); 
        Arrays.sort(myLines);
        MyLine previous;
        if (myLines.length > 0) {
            previous = myLines[0];
            lineSegments.add(new LineSegment(previous.p, previous.q));
            for (int i = 1; i < myLines.length; i++) {
                if (!(myLines[i].compareTo(previous) == 0)) {
                    previous = myLines[i];
                    lineSegments.add(new LineSegment(previous.p, previous.q));
                }
            }
        }

    }

    public int numberOfSegments() { 
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] a = new LineSegment[0];
        return lineSegments.toArray(a);
    }

    public static void main(String[] args){
        try{

            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int n = Integer.parseInt(br.readLine());
            Point[] p = new Point[n];
            for (int i = 0; i < n; i++) {
                String line = br.readLine();
                String[] params = line.split("\\s+");
                boolean spaceFirst = line.charAt(0) == ' ';
                //System.out.println(params[0] + "|" + params[1]);
                p[i] = new Point(Integer.parseInt(params[spaceFirst? 1 : 0]), 
                                Integer.parseInt(params[spaceFirst? 2: 1]));
                //System.out.println(p[i]);
            }
            FastCollinearPoints s = new FastCollinearPoints(p);
            LineSegment[] x = s.segments();
            for (int i = 0; i < s.numberOfSegments(); i++) {
                System.out.println(x[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
