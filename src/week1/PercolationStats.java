package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] results;
	

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
    	if (n <= 0 || trials <= 0){
    		throw new IllegalArgumentException("illegal input");
    	}
    	results = new double[trials];
    	for (int i=0; i<trials; i++){
    		Percolation tmp_grid = new Percolation(n);
    		while (!tmp_grid.percolates()){
    			int row = StdRandom.uniform(1, n+1);
    			int col = StdRandom.uniform(1, n+1);
    			if (tmp_grid.isOpen(row, col)) continue;
    			tmp_grid.open(row, col);
    		}
    		results[i] = (double) tmp_grid.numberOfOpenSites() / (n*n);	
    	}
    }

    // sample mean of percolation threshold
    public double mean(){
    	return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
    	if (results.length == 1){
    		return Math.sqrt((results[0] - mean()) * (results[0] - mean()));
    	}
    	return StdStats.stddev(results);
    	
    	
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
    	return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
    	return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

   // test client (see below)
   public static void main(String[] args){
	   PercolationStats ps = new PercolationStats(200, 100);
	   System.out.println(ps.mean());
	   System.out.println(ps.stddev());
	   System.out.println(ps.confidenceLo());
	   System.out.println(ps.confidenceHi());
   }

}
