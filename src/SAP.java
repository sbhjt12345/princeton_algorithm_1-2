import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
	private final Digraph di;
//	private Map<Tuple, Tuple> minMap;        


	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G){
		if (G==null) throw new IllegalArgumentException();
		di = new Digraph(G);
	}
	
	private void validate(int m){
		if (m<0 || m>=di.V()) throw new IllegalArgumentException();
	}
	
	private int distance(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW, int m){
		return bfsV.distTo(m) + bfsW.distTo(m);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w){
		validate(v);validate(w);
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(di, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(di, w);
		int ancestor = this.ancestor(bfsV, bfsW);
		if (ancestor != -1){
			return this.distance(bfsV, bfsW, ancestor);
 		}
		return -1;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w){
		validate(v);validate(w);
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(di, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(di, w);
		return this.ancestor(bfsV, bfsW);
	
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w){
		if (v==null || w==null) throw new IllegalArgumentException();
		for (Integer vv : v) if (vv == null) throw new IllegalArgumentException();
		for (Integer ww : w) if (ww == null) throw new IllegalArgumentException();
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(di, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(di, w);
		int ancestor = this.ancestor(bfsV, bfsW);
		if (ancestor != -1){
			return this.distance(bfsV, bfsW, ancestor);
 		}
		return -1;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
		if (v==null || w==null) throw new IllegalArgumentException();
		for (Integer vv : v) if (vv == null) throw new IllegalArgumentException();
		for (Integer ww : w) if (ww == null) throw new IllegalArgumentException();
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(di, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(di, w);
		return this.ancestor(bfsV, bfsW);
	}
	
	private int ancestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW){
		int length = Integer.MAX_VALUE;
		int key = -1;
		for (int i=0;i<di.V();i++){
			if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
				int tmp_dis = this.distance(bfsV, bfsW, i);
				if (tmp_dis < length){
					length = tmp_dis;
					key = i;
				}
			}
		}
		return key;
	}

	// do unit testing of this class
	public static void main(String[] args){
		
	}
}

