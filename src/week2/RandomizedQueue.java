package week2;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] a;
	private int n;

	// construct an empty randomized queue
	public RandomizedQueue(){
		// a = (Item[]) new Object[0];
		n = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty(){
		return size() == 0;
	}

	// return the number of items on the randomized queue
	public int size(){
		return n;
	}

	private void resize(int capacity){
		assert capacity >= n;
		Item[] copy = (Item[]) new Object[capacity];
		for (int i=0;i<size();i++){
			copy[i] = a[i];
		}
		a = copy;
	}

	// add the item
	public void enqueue(Item item){
		if (item==null) throw new IllegalArgumentException();
		if (isEmpty()){
			a = (Item[]) new Object[]{item};
			n++;
		}
		else{
			if (a.length==n){
				resize(2*a.length);
			}
			a[n++] = item;
		}


	}

	// remove and return a random item
	public Item dequeue(){
		if (isEmpty()) throw new NoSuchElementException();
		int index = StdRandom.uniform(1,n+1);
		Item item = a[index-1];
		a[index-1] = a[--n];
		a[n] = null;
		if (!isEmpty() && a.length >= 4*size()) resize(a.length/2);
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample(){
		if (isEmpty()) throw new NoSuchElementException();
		int index = StdRandom.uniform(1,n+1);
		return a[index-1];

	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator(){
		return new ArrayListIterator();
	}

	private class ArrayListIterator implements Iterator<Item>{
		//idea: build a new randomArray, which stores all elements of a but in random order
		private int cur;
		private int[] randomQueueIndex;

		public ArrayListIterator(){
			randomQueueIndex = new int[n];
			for (int i=0;i<n;i++){
				randomQueueIndex[i] = i;
			}
			StdRandom.shuffle(randomQueueIndex);
			cur = 0;


		}

		@Override
		public boolean hasNext() {
			return (!isEmpty() && (cur < n));
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return a[randomQueueIndex[cur++]];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	// unit testing (required)
	public static void main(String[] args){
		RandomizedQueue<Integer> sut = new RandomizedQueue<Integer>();

//		System.out.println(sut.isEmpty());
//		System.out.println(sut.size());
		
		for (int i=0;i<10;i++){
			sut.enqueue(i);
		}

		Iterator<Integer> randomizedQueueIterator = sut.iterator();
		while (randomizedQueueIterator.hasNext()) {
			System.out.println(randomizedQueueIterator.next());
		}

	}

}
