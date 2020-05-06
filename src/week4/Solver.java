package week4;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private final MinPQ<BoardNode> originalHeap;
	private BoardNode heapFinalNode;
	private int finalMoves;
	private boolean solvable = false;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial){
		if (initial==null) throw new IllegalArgumentException();
		originalHeap = initialize(initial);
		MinPQ<BoardNode> twinHeap = initialize(initial.twin());
		while (!originalHeap.isEmpty() && !twinHeap.isEmpty()){
			BoardNode bnHeap = originalHeap.delMin();
			BoardNode bnTwin = twinHeap.delMin();
			if (bnHeap.cur.isGoal()){
				heapFinalNode = bnHeap;
				finalMoves = bnHeap.moves;
				solvable = true;
				return;
			}
			if (bnTwin.cur.isGoal()){
				return;
			}
			Iterable<Board> neighborsHeap = bnHeap.cur.neighbors();
			Iterable<Board> neighborsTwin = bnTwin.cur.neighbors();
			for (Board ne : neighborsHeap){
				if (bnHeap.prevNode == null || !ne.equals(bnHeap.prevNode.cur)){
					originalHeap.insert(new BoardNode(ne,bnHeap.moves+1,bnHeap));
				}
			}
			for (Board ne : neighborsTwin){
				if (bnTwin.prevNode == null || !ne.equals(bnTwin.prevNode.cur)){
					twinHeap.insert(new BoardNode(ne,bnTwin.moves+1,bnTwin));
				}
			}
		}
	}

	private MinPQ<BoardNode> initialize(Board initial){
		MinPQ<BoardNode> res = new MinPQ<BoardNode>(new BNComparator());
		res.insert(new BoardNode(initial,0,null));
		return res;
	}

	// is the initial board solvable? (see below)
	public boolean isSolvable(){
		return this.solvable;
	}

	// min number of moves to solve initial board
	public int moves(){
		if (isSolvable()) return this.finalMoves;
		return -1;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution(){
		if (isSolvable()){
			Stack<Board> res = new Stack<>();
			BoardNode copy = heapFinalNode;
			while (copy != null){
				res.push(copy.cur);
				copy = copy.prevNode;
			}
			return res;
		}
		return null;
	}


	private class BNComparator implements Comparator<BoardNode>{

		@Override
		public int compare(BoardNode o1, BoardNode o2) {
			// TODO Auto-generated method stub
			if (o1.manhattan+o1.moves > o2.manhattan+o2.moves){
				return 1;
			}
			else if (o1.manhattan+o1.moves < o2.manhattan+o2.moves){
				return -1;
			}
			return 0;
		}

	}

	private class BoardNode{
		Board cur;
		BoardNode prevNode;
		int moves;
		int manhattan;
		public BoardNode(Board cur, int moves, BoardNode prevNode){
			this.cur = cur;
			this.moves = moves;
			this.manhattan = cur.manhattan();
			this.prevNode = prevNode;
		}
	}


	// test client (see below) 
	public static void main(String[] args) {

//		// create initial board from file
//		In in = new In(args[0]);
//		int n = in.readInt();
//		int[][] tiles = new int[n][n];
//		for (int i = 0; i < n; i++)
//			for (int j = 0; j < n; j++)
//				tiles[i][j] = in.readInt();
//		Board initial = new Board(tiles);
//
//		// solve the puzzle
//		Solver solver = new Solver(initial);
//
//		// print solution to standard output
//		if (!solver.isSolvable())
//			StdOut.println("No solution possible");
//		else {
//			StdOut.println("Minimum number of moves = " + solver.moves());
//			for (Board board : solver.solution())
//				StdOut.println(board);
//		}
	}

}
