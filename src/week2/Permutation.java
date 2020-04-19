package week2;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args){
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		while (!StdIn.isEmpty()){
			rq.enqueue(StdIn.readString());
		}
		assert(k>=0 && k<=rq.size());
		for (int i=0;i<k;i++){
			System.out.println(rq.dequeue());
		}

	}
}
