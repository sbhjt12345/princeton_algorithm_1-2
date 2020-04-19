package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
	private int[][] grid;
	private final int begin;
	private final int end;
	private final int grid_width;
	private final WeightedQuickUnionUF wuf;
	private final WeightedQuickUnionUF wuf2;   // to avoid backwash problem
	private int count;   // num of open sites
	

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
    	if (n <= 0){
    		throw new IllegalArgumentException("grid width too small");
    	}
    	begin = 0;
    	end = n*n + 1;
    	grid_width = n;
    	wuf = new WeightedQuickUnionUF(n*n + 2);
    	wuf2 = new WeightedQuickUnionUF(n*n + 1);
    	for (int i = 1; i <= n; i++){
    		wuf.union(begin, i);
    		wuf2.union(begin, i);
    	}
    	for (int i = n*n - n + 1; i <= n*n; i++){
    		wuf.union(i, end);
    	}
    	grid = new int[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
    	checkException(row, col);
    	if (!isOpen(row, col)){
    		grid[row-1][col-1] = 1;
    		int wuf_index = wufIndex(row, col);
    		if (col != 1 && isOpen(row, col-1)){
    			wuf.union(wuf_index, wufIndex(row, col-1));
    			wuf2.union(wuf_index, wufIndex(row, col-1));
    		}
    		if (col != grid_width && isOpen(row, col+1)){
    			wuf.union(wuf_index, wufIndex(row, col+1)); 
    			wuf2.union(wuf_index, wufIndex(row, col+1));
    		}
    		if (row != 1 && isOpen(row-1, col)){
    			wuf.union(wuf_index, wufIndex(row-1, col));
    			wuf2.union(wuf_index, wufIndex(row-1, col));
    		}
    		if (row != grid_width && isOpen(row+1, col)){
    			wuf.union(wuf_index, wufIndex(row+1, col));
    			wuf2.union(wuf_index, wufIndex(row+1, col));
    			
    		}	
    		count++; 
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
    	checkException(row, col);
    	return grid[row-1][col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
    	checkException(row, col);
    	if (!isOpen(row, col)) return false;
    	return wuf.connected(begin, wufIndex(row, col)) && wuf2.connected(begin, wufIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
    	return count;
    }

    // does the system percolate?
    public boolean percolates(){
    	if (grid_width == 1){
    		return isOpen(1,1);
    	}
//    	else if (grid_width == 2){
//    		return (isOpen(1,1) && isOpen(2,1)) || (isOpen(1,2) && isOpen(2,2));
//    	}
    	return wuf.connected(begin, end);
    }
    
    private void checkException(int row, int col){
    	if (row < 1 || col < 1 || row > grid_width || col > grid_width){
    		throw new IllegalArgumentException("Index out of bounds");
    	}
    }
    
    private int wufIndex(int row, int col){
    	return grid_width * (row-1) + col;
    }

    // test client (optional)
    public static void main(String[] args){
    	Percolation pp = new Percolation(5);
    	pp.open(1,3);
    	pp.open(2, 3);
    	pp.open(2, 4);
    	pp.open(3, 4);
    	pp.open(3, 5);
    	pp.open(4, 5);
    	pp.open(4, 4);
    	pp.open(4, 3);
    	pp.open(4, 2);
    	pp.open(4, 1);
    	pp.open(5, 1);
    	System.out.println(pp.numberOfOpenSites());
    	System.out.println(pp.percolates());
    }
}
