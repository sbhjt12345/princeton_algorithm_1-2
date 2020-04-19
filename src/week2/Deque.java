package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;
	private int n;   // size
	
	private class Node<Item>{
		private Item item;
		private Node<Item> prev;
		private Node<Item> next;
	}

    // construct an empty deque
    public Deque(){
    	first = null;
    	last = null;
    	n = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
    	return n==0;	
    }

    // return the number of items on the deque
    public int size(){
    	return n;
    }

    // add the item to the front
    public void addFirst(Item item){
    	if (item==null) throw new IllegalArgumentException();
    	Node<Item> oldFirst = first;
    	first = new Node<Item>();
    	first.next = oldFirst;
    	first.item = item;
    	if (size() == 0){    // corner case 1: if there was no element
    		last = first;
    		
    	}
    	else{
    		oldFirst.prev = first;
    	}
    	n++;
    }

    // add the item to the back
    public void addLast(Item item){
    	if (item==null) throw new IllegalArgumentException();
    	Node<Item> oldLast = last;
    	last = new Node<Item>();
    	last.prev = oldLast;
    	last.item = item;
    	if (size() == 0){
    		first = last;
    	}
    	else{
    		oldLast.next = last;
    	}
    	n++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
    	if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    	Item item = first.item;
    	first = first.next;
    	if (size() == 1){
    		last = null;
    	}
    	else{
    		 first.prev = null;
    	}
    	n--;
    	return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
    	if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    	Item item = last.item;
    	last = last.prev;
    	if (size() == 1){
    		first = null;
    	}
    	else{
    		last.next = null;
    	}
    	n--;
    	return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	return new DoublyLL(first);
    }
    
    private class DoublyLL implements Iterator<Item>{
    	private Node<Item> current;
    	
    	public DoublyLL(Node<Item> first){
    		current = first;
    	}
    	
    	public boolean hasNext(){
    		return current!= null;
    	}
    	
    	public void remove(){
    		throw new UnsupportedOperationException();
    	}
    	
    	public Item next(){
    		if (!hasNext()) throw new NoSuchElementException();
    		Item item = current.item;
    		current = current.next;
    		return item;
    	}
    }

    // unit testing (required)
    public static void main(String[] args){
    	Deque<Integer> deque = new Deque<>();
    	deque.addFirst(0);
//    	deque.addLast(100000);
    	System.out.println(deque.first.item);
//    	System.out.println(deque.first.next.item);
//    	System.out.println(deque.last.item);
//    	System.out.println(deque.last.prev.item);
    	// deque.removeFirst();
    	System.out.println(deque.removeLast());
//    	System.out.println(deque.removeFirst());
    	// deque.removeFirst();
    }

}