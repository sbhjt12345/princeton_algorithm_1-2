package week4;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.In;

public class Board {
	private int[][] board;
	private final int dimension, manhattan, hamming;
	private int zeroRow, zeroCol;

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)
	public Board(int[][] tiles){
		dimension = tiles.length;
		board = new int[dimension][dimension];
		for (int i=0;i<dimension;i++){
			for (int j=0;j<dimension;j++){
				board[i][j] = tiles[i][j];
				if (board[i][j]==0){
					zeroRow = i;
					zeroCol = j;
				}
			}
		}
		hamming = countHamming();
		manhattan = countManhattan();
	}

	// string representation of this board
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(dimension+"\n");
		for (int i=0;i<dimension;i++){
			StringBuilder tmp = new StringBuilder();
			for (int j=0;j<dimension;j++){
				tmp.append(board[i][j] + " ");
			}
			if (i!= dimension-1) tmp.append("\n");
			sb.append(tmp);
		}
		return sb.toString();
	}

	// board dimension n
	public int dimension(){
		return dimension;
	}

	// number of tiles out of place
	public int hamming(){
		return hamming;
	}
	
	private int countHamming(){
		int sum = 0;
		for (int i=0;i<dimension;i++){
			for (int j=0;j<dimension;j++){
				if (board[i][j] == 0) continue;
				int gap = i * dimension + j + 1 == board[i][j]?0:1;
				sum += gap;
			}
		}
		return sum;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan(){
		return manhattan;
	}
	
	private int countManhattan(){
		int sum = 0;
		for (int i=0;i<dimension;i++){
			for (int j=0;j<dimension;j++){
				if (board[i][j] == 0) continue;
				int goalRow = (board[i][j]-1)/dimension;
				int goalCol = board[i][j]%dimension==0?dimension-1:board[i][j]%dimension-1;
				int gap = Math.abs(goalRow-i) + Math.abs(goalCol-j);
				sum += gap;
			}
		}
		return sum;
	}


	// is this board the goal board?
	public boolean isGoal(){
		return manhattan() == 0;
	}

	// does this board equal y?
	public boolean equals(Object y){
		if (y == null || y.getClass() != getClass()) return false;
		Board another = (Board) y;
		if (dimension != another.board.length)
			return false;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++) {
				if (this.board[i][j] != another.board[i][j])
					return false;
			}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors(){
		int a = zeroRow, b = zeroCol;
		List<Board> list = new ArrayList<>();
		if (a > 0){
			//row is not at top, means it has neighbor above
			list.add(swap(a,b,a-1,b));
		}
		if (a<dimension-1){
			list.add(swap(a,b,a+1,b));
		}
		if (b>0){
			list.add(swap(a,b,a,b-1));
		}
		if (b<dimension-1){
			list.add(swap(a,b,a,b+1));
		}
		return list;
	}
	
	private Board swap(int row, int col, int row1, int col1){
		int[][] copy = new int[dimension][dimension];
		for (int i=0;i<dimension;i++){
			for (int j=0;j<dimension;j++){
				copy[i][j] = board[i][j];
			}
		}
		int tmp = copy[row][col];
		copy[row][col] = copy[row1][col1];
		copy[row1][col1] = tmp;
		
		return new Board(copy);
		
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin(){
//		int a = StdRandom.uniform(0,dimension), b = StdRandom.uniform(0,dimension);
//		while (a==zeroRow && b==zeroCol){
//			a = StdRandom.uniform(0,dimension);
//			b = StdRandom.uniform(0,dimension);
//		}
//		int c = StdRandom.uniform(0,dimension), d = StdRandom.uniform(0,dimension);
//		while ((c==zeroRow && d==zeroCol) || (c==a && d==b)){
//			c = StdRandom.uniform(0,dimension);
//			d = StdRandom.uniform(0,dimension);
//		}
//		return swap(a,b,c,d);
		if (board[0][0] != 0 && board[0][1] != 0) {
            return swap(0, 0, 0, 1);
        } else {
            return swap(1, 0, 1, 1);
        }
	}


	// unit testing (not graded)
	public static void main(String[] args){
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				tiles[i][j] = in.readInt();
		Board initial = new Board(tiles);
		System.out.println(initial.hamming());
		System.out.println(initial.manhattan());
		List<Board> neighbors = (List<Board>) initial.neighbors();
		for (Board ne : neighbors){
			List<Board> nnnnei = (List<Board>) ne.neighbors();
			for (Board nn : nnnnei){
				System.out.println(nn.toString());
			}
		}
		Board twins1 = initial.twin();
		Board twins2 = initial.twin();
		System.out.println(twins1.equals(twins2));
	}
}
