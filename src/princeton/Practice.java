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


	//	public static void main(String[] args){
	//		Practice p = new Practice();
	//		int[] a = new int[]{3,5,1,2,7,33,74,34,46,11,2,0,23};
	//		a = p.insertionSortReverseOrder(a);
	//		for (int x : a){
	//			System.out.println(x);
	//		}
	//	}

	public int[] sortColors(int[] A){
		int lo = 0, hi = A.length-1, i=0;
		while (i<=hi){
			if (A[i]==2){
				swap(A,i,hi--);
			}
			else if (A[i]==0){
				swap(A,lo++,i++);
			}
			else i++;
		}
		return A;
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int m = nums1.length, n = nums2.length;
		// if m+n is odd then just find (m+n)/2 th element
		// else find (m+n)/2 th and (m+n)/2-1 th element
		if ((m+n)%2 != 0){
			return findKthElement(nums1, nums2, 0, m, 0, n, (m+n)/2+1);
		}
		else{
			double tmp = findKthElement(nums1, nums2, 0, m, 0, n, (m+n)/2+1);
			double tmp2 = findKthElement(nums1, nums2, 0, m, 0, n, (m+n)/2);
			System.out.println(tmp);
			System.out.println(tmp2);
			//k is 1 index based
			// lo_a and lo_b is 0 index based
			// hi_a and hi_b is 1 index based
			return (double) (tmp+tmp2)/2;
		}

	}

	public double findKthElement(int[] a, int[] b, int lo_a, int hi_a, int lo_b, int hi_b, int k){
		if (lo_a==hi_a) return b[lo_b + k-1];
		if (lo_b == hi_b) return a[lo_a + k-1];
		if (k==1){
			return a[lo_a] < b[lo_b] ? a[lo_a]:b[lo_b];
		}
		int cur = k/2;
		if (lo_a + cur - 1 >= hi_a){
			if (a[hi_a-1]<b[lo_b+cur-1]){
				// which means kth element is in b
				return b[lo_b + k-1 - hi_a+lo_a];
			}
			else{
				return findKthElement(a,b,lo_a,hi_a,lo_b+cur,hi_b,k-cur);
			}
		}
		else if (lo_b + cur - 1 >= hi_b){
			if (b[hi_b-1] < a[lo_a+cur-1]){
				return a[lo_a+k-1 - (hi_b-lo_b)];
			}
			else{
				return findKthElement(a,b,lo_a+cur,hi_a,lo_b,hi_b,k-cur);
			}
		}
		else{
			if (a[lo_a + cur-1] > b[lo_b + cur-1]){
				return findKthElement(a,b,lo_a,hi_a,lo_b+cur,hi_b,k-cur);
			}
			else{
				return findKthElement(a,b,lo_a+cur,hi_a,lo_b,hi_b,k-cur);
			}
		}	
	}

	public static void main(String[] args){
		int[] a = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
		int[] b = new int[]{0,6};
		Practice p = new Practice();
		System.out.println(p.findMedianSortedArrays(a, b));
	}

	public void merge(int[] A, int[] aux, int lo, int mid, int hi){
		for (int i=lo;i<=hi;i++){
			aux[i] = A[i];
		}
		int i=lo,j=mid+1;
		for (int k=lo;k<=hi;k++){
			if (i>mid) A[k] = aux[j++];
			if (j>hi) A[k] = aux[i++];
			if (aux[i]>aux[j]) A[k] = aux[j++];
			if (aux[i]<aux[j]) A[k] = aux[i++];
 		}
	}

	public void mergeSort(int[] A){
		int lo = 0, hi = A.length-1;
		int[] aux= new int[A.length];
		helper(A,aux,lo,hi);
	}

	public void helper(int[] A, int[] aux, int lo, int hi){
		if (lo<hi){
			int mid = lo + (hi-lo)/2;
			helper(A,aux,lo,mid);
			helper(A,aux,mid+1,hi);
			merge(A,aux,lo,mid,hi);
		}
		return;
	}



}
