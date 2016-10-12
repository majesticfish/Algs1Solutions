import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percentages;
    public PercolationStats(int n, int trials) {
        percentages = new double[trials];
        for (int i = 0; i < trials; i++) {
            int sitesOpen = 0;
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int x = (int) (n*StdRandom.uniform());
                int y = (int) (n*StdRandom.uniform());
                if (!p.isOpen(x+1, y+1)) {
                    sitesOpen++;
                    p.open(x+1, y+1);
                }
            }
            percentages[i] = (sitesOpen*1.0/(n*(n*1.0)));
        }
    }
    public double mean() {
        return StdStats.mean(percentages);    
    }
    public double stddev() {
        return StdStats.stddev(percentages);
    }
    public double confidenceLo() {
        return mean()-2*stddev();
    }
    public double confidenceHi() {
        return mean()+2*stddev();
    }
    public static void main(String[] args) {
        try {
            PercolationStats s = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("Mean: " + s.mean());
            System.out.println("StdDev: " + s.stddev());
            System.out.println("ConfidenceLo: " + s.confidenceLo());
            System.out.println("ConfidenceHi: " + s.confidenceHi());
        } catch (NumberFormatException e) {
            System.err.println("You didn't give me 2 numbers!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You didn't give me 2 arguments!");
        }
    }
}
