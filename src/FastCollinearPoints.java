import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
	private LineSegment[] lsArray = new LineSegment[0];
	public FastCollinearPoints(Point[] points){
		// finds all line segments containing 4 or more points
		List<LineSegment> slList = new ArrayList<>();
		if (points == null) throw new IllegalArgumentException();
		for (Point p : points){
			if (p==null) throw new IllegalArgumentException();
		}
		Point[] copy = points.clone();
		Arrays.sort(copy);
		if (copy.length>=2){
			for (int i=0;i<copy.length-1;i++){
				if (copy[i].compareTo(copy[i+1])==0){
					throw new IllegalArgumentException();
				}
			}
		}
		if (copy.length >= 4){
			Point[] pointsCopy = points.clone();
			for (int i = 0; i<points.length;i++){
				Point pivot = points[i];
				Arrays.sort(pointsCopy, pivot.slopeOrder());
				int index = 1;
				while (index < pointsCopy.length){
					double current = pivot.slopeTo(pointsCopy[index]);
					LinkedList<Point> sameSlopeSubList = new LinkedList<>();
					while (index < pointsCopy.length && current == pivot.slopeTo(pointsCopy[index])){
						sameSlopeSubList.add(pointsCopy[index++]);
					}

					if (sameSlopeSubList.size()>=3){
						Collections.sort(sameSlopeSubList);
						if (pivot.compareTo(sameSlopeSubList.peek()) < 0){
							slList.add(new LineSegment(pivot, sameSlopeSubList.getLast()));
						}
					}
				}
			}
		}
		lsArray = slList.toArray(lsArray);
	}
	public int numberOfSegments(){
		// the number of line segments
		return lsArray.length;
	}
	public LineSegment[] segments(){
		// the line segments
		return lsArray.clone();
	}

	//	public static void main(String[] args){
	//		Point[] points = new Point[5];
	//		points[0] = new Point(-1,0);
	//		points[1] = new Point(0,1);
	//		points[2] = new Point(3,4);
	//		points[3] = new Point(2,3);
	//		points[4] = new Point(5,6);
	//		FastCollinearPoints bcp = new FastCollinearPoints(points);
	//		System.out.println(bcp.numberOfSegments());
	//		LineSegment[] lsArray = bcp.segments();
	//		for (LineSegment ls : lsArray){
	//			System.out.println(ls);
	//		}
	//	}
}
