package princeton;

public class QuickFindUF {
	private int[] id;
	public QuickFindUF(int N){
		id = new int[N];
		for (int i=0;i<N;i++){
			id[i] = i;
		}
	}
	
	public boolean connected(int p, int q){
		return id[p] == id[q];
	}
	
	public void union(int p, int q){   //too slow
		int idp = id[p];
		int idq = id[q];
		for (int i=0;i<id.length;i++){
			if (id[i]==idp){
				id[i] = idq;
			}
		}
	}
	
	// quick_union: just change the root of A to B

}
