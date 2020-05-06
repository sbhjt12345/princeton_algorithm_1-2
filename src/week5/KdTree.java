package week5;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private Node root;
	private int size;

	public KdTree(){
		// construct an empty set of points 
		root = null;
		this.size = 0;
	}

	public boolean isEmpty(){
		// is the set empty? 
		return size==0;
	}

	public int size(){
		// number of points in the set
		return size;
	}


	public void insert(Point2D p){
		// add the point to the set (if it is not already in the set)
		if (p==null) throw new IllegalArgumentException();
		root = insert(root,p,true);
	}

	private Node insert(Node cur, Point2D p, boolean order){
		if (cur==null) {
			size++;
			return new Node(p,order);
		}
		if (p.equals(cur.val)) return cur;
		double cmp = cur.compareX?p.x() - cur.val.x():p.y()-cur.val.y();
		if (cmp<0){
			cur.left = insert(cur.left, p, !order);
		}
		else if (cmp>=0){
			cur.right = insert(cur.right, p, !order);
		}
		return cur;
	}

	public boolean contains(Point2D p){
		// does the set contain point p? 
		if (p==null) throw new IllegalArgumentException();
		Node cur = root;
		while (cur != null){
			double cmp = cur.compareX?p.x() - cur.val.x():p.y()-cur.val.y();
			if (cmp<0) cur = cur.left;
			else if (cmp>0) cur = cur.right;
			else{
				if (cur.val.equals(p)) return true;
				else cur = cur.right;
			}
		}
		return false;
	}

	public void draw(){
		// draw all points to standard draw 
		draw(root,0,1,0,1);

	}

	private void draw(Node cur, double xMin, double xMax, double yMin, double yMax){
		if (cur==null) return;
		StdDraw.setPenRadius();
		StdDraw.setPenColor(cur.compareX?StdDraw.RED:StdDraw.BLUE);
		if (cur.compareX){
			StdDraw.line(cur.val.x(), yMin, cur.val.x(), yMax);
		}
		else{
			StdDraw.line(xMin, cur.val.y(), xMax, cur.val.y());
		}
		StdDraw.setPenRadius(.02);
		StdDraw.setPenColor(StdDraw.BLACK);
		cur.val.draw();
		if (cur.compareX){
			draw(cur.left, xMin, cur.val.x(), yMin, yMax);
			draw(cur.right, cur.val.x(), xMax, yMin, yMax);
		}
		else{
			draw(cur.left, xMin, xMax, yMin, cur.val.y());
			draw(cur.right, xMin, xMax, cur.val.y(), yMax);
		}
	}

	public Iterable<Point2D> range(RectHV rect){
		// all points that are inside the rectangle (or on the boundary) 
		if (rect==null) throw new IllegalArgumentException();
		LinkedBag<Point2D> res = new LinkedBag<>();
		rangeHelper(rect, root, res);
		return res;
	}

	private void rangeHelper(RectHV rect, Node cur, LinkedBag<Point2D> res){
		//if compareX, then check if cur.x < minx or cur.x > maxx
		// if compareY, do Y
		if (cur==null) return;
		if (rect.contains(cur.val)) res.add(cur.val);
		if (cur.compareX){
			if (cur.val.x() >= rect.xmin()){
				// point is left to rect, thus left tree wont in rect
				rangeHelper(rect, cur.left, res);
			}
			if (cur.val.x() <= rect.xmax()){
				rangeHelper(rect, cur.right, res);
			}
		}
		else{
			if (cur.val.y() >= rect.ymin()){
				rangeHelper(rect, cur.left, res);
			}
			if (cur.val.y() <= rect.ymax()){
				rangeHelper(rect, cur.right, res);
			}
		}
	}


	private class NodeDis{
		Node subtree;
		double potentialDistance;
		public NodeDis(Node subtree, double potentialDistance){
			this.subtree = subtree;
			this.potentialDistance = potentialDistance;
		}
	}

	public Point2D nearest(Point2D p){
		// a nearest neighbor in the set to point p; null if the set is empty 
		if (p==null) throw new IllegalArgumentException();
		if (root==null) return null;
		Stack<NodeDis> stack = new Stack<>();
		stack.push(new NodeDis(root, Double.POSITIVE_INFINITY));  // dummy val
		return nearest(p,stack);
	}

	private Point2D nearest(Point2D p, Stack<NodeDis> stack){
		double minDis = Double.POSITIVE_INFINITY;
		Point2D nearest = null;
		while (!stack.isEmpty()){
			NodeDis nd = stack.pop();
			Node cur = nd.subtree;
			if (cur==null) continue;
			//			System.out.println("current node is:(" + cur.val.x() + "," + cur.val.y()+")");
			double curDis = cur.val.distanceTo(p);
			if (curDis < minDis){
				minDis = curDis;
				nearest = cur.val;
			}
			while (!stack.isEmpty() && stack.peek().potentialDistance > minDis) stack.pop();
			if (cur.compareX){
				double potentialDis = Math.abs(cur.val.x()-p.x());
				if (cur.val.x() > p.x()){
					// search left tree first
					stack.push(new NodeDis(cur.right, potentialDis));
					stack.push(new NodeDis(cur.left, potentialDis));
				}
				else{
					// search right tree first
					stack.push(new NodeDis(cur.left, potentialDis));
					stack.push(new NodeDis(cur.right, potentialDis));
				}
			}
			else{
				double potentialDis = Math.abs(cur.val.y()-p.y());
				if (cur.val.y() > p.y()){
					// search left tree first
					stack.push(new NodeDis(cur.right, potentialDis));
					stack.push(new NodeDis(cur.left, potentialDis));
				}
				else{
					// search right tree first
					stack.push(new NodeDis(cur.left, potentialDis));
					stack.push(new NodeDis(cur.right, potentialDis));
				}
			}
		}
		return nearest;
	}

	public static void main(String[] args){
		// unit testing of the methods (optional) 
		RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
		KdTree kd = new KdTree();
		kd.insert(new Point2D(0.5,0.5));
		kd.insert(new Point2D(0.4,0.6));
		kd.insert(new Point2D(0.4,0.8));
		kd.insert(new Point2D(0.45,0.7));
		kd.insert(new Point2D(0.9,0.1));
		//		StdDraw.clear();
		//		kd.draw();
		//		StdDraw.show();
		RectHV small = new RectHV(0.3,0.3,0.7,0.7);
		Iterable<Point2D> iter = kd.range(small);
		for (Point2D pp : iter){
			System.out.println(pp.x() + ", " + pp.y());
		}

		Point2D pp = kd.nearest(new Point2D(0.8,0.8));
	}

	private class Node{
		Point2D val;
		Node left, right;
		boolean compareX;

		public Node(Point2D val, boolean compareX){
			this.val = val;
			this.compareX = compareX;
		}
	}

}
