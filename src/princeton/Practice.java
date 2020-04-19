package princeton;

public class Practice {
	public int[] insertionSortReverseOrder(int[] a){
		for (int i = 0; i<a.length;i++){
			for (int j=a.length-1;j>0;j--){
				if (a[j]<a[j-1]) swap(a,j,j-1);   
				//loop from end to start cannot use break
				//loop from start to end can because the left side is already sorted
			}
		}
		return a;
	}
	
	private void swap(int[] a, int i, int j){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	
	public static void main(String[] args){
		Practice p = new Practice();
		int[] a = new int[]{3,5,1,2,7,33,74,34,46,11,2,0,23};
		a = p.insertionSortReverseOrder(a);
		for (int x : a){
			System.out.println(x);
		}
	}

}
