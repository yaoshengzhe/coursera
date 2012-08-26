
import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

/* @(#)PercolationStats.java
 */
/**
 * 
 *
 * @author Shengzhe Yao
 */

public class PercolationStats {

    private double sampleMean = 0;
    private double sampleStd = 0;

    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        double totalFractions = 0;
        Collection<Double> fractionColl = new ArrayList<Double>();

        for (int i = 0; i < T; ++i) {
            Percolation test = new Percolation(N);
            Random rand = new Random();
            int openedSites = 0;
            while (!test.percolates()) {
                int val = rand.nextInt(N*N);
                int[] index = fromUnionFindIndex(val, N);
                if (!test.isOpen(index[0], index[1])) {
                    ++openedSites;
                    test.open(index[0], index[1]);
                }
            }
            double fraction = (double) openedSites / (N*N);
            totalFractions += fraction;
            fractionColl.add(fraction);
        }
        sampleMean = totalFractions / T;

        for (double fraction : fractionColl) {
            double x = fraction - mean();
            sampleStd += x*x;
        }
        sampleStd = Math.sqrt(sampleStd / (T-1));
    }

    private int toUnionFindIndex(int i, int j, int N) {
        return (i-1) * N + j-1;
    }

    private int[] fromUnionFindIndex(int val, int N) {
        int i = (val / N) + 1;
        int j = val - (i-1)*N + 1;
        return new int[] {i, j};
    }

    public double mean() {
        return sampleMean;
    }

    public double stddev() {
        return sampleStd;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats percolationStats = new PercolationStats(N, T);

        double part = 1.96*percolationStats.stddev()/Math.sqrt(T);

        System.out.println("mean\t\t\t\t= " + percolationStats.mean());
        System.out.println("stddev\t\t\t\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval\t\t= " + (percolationStats.mean() - part) + ", " + (percolationStats.mean() + part));
    }
}
