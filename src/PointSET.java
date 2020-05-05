import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;


public class PointSET {
	SET<Point2D> pointSet;
	public PointSET(){
		// construct an empty set of points 
		pointSet = new SET<>();

	}
	public boolean isEmpty(){
		// is the set empty? 
		return pointSet.isEmpty();
	}
	public int size(){
		// number of points in the set 
		return pointSet.size();
	}
	public void insert(Point2D p){
		// add the point to the set (if it is not already in the set)
		if (p==null) throw new IllegalArgumentException();
		if (!contains(p)) pointSet.add(p);
	}
	public boolean contains(Point2D p){
		// does the set contain point p? 
		if (p==null) throw new IllegalArgumentException();
		return pointSet.contains(p);
	}
	public void draw(){
		// draw all points to standard draw 
		Iterator<Point2D> iterator = pointSet.iterator();
		while (iterator.hasNext()){
			Point2D pt = iterator.next();
			pt.draw();
		}

	}
	public Iterable<Point2D> range(RectHV rect){
		// all points that are inside the rectangle (or on the boundary) 
		if (rect==null) throw new IllegalArgumentException();
		Iterator<Point2D> iterator = pointSet.iterator();
		List<Point2D> res = new ArrayList<>();
		while (iterator.hasNext()){
			Point2D pt = iterator.next();
			if (rect.contains(pt)){
				res.add(pt);
			}
		}
		return res;
	}
	public Point2D nearest(Point2D p){
		// a nearest neighbor in the set to point p; null if the set is empty 
		if (p==null) throw new IllegalArgumentException();
		Iterator<Point2D> iterator = pointSet.iterator();
		double minDis = Double.MAX_VALUE;
		Point2D res = null;
		while (iterator.hasNext()){
			Point2D pt = iterator.next();
			double dis = p.distanceSquaredTo(pt);
			if (dis < minDis) res = pt;
		}
		return res;
	}

	public static void main(String[] args){
		// unit testing of the methods (optional) 
	}
}
