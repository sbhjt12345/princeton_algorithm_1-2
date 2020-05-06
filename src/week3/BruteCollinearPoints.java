package week3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
	private List<LineSegment> lsList;
	private LineSegment[] lsArray = new LineSegment[0];
	public BruteCollinearPoints(Point[] points){
		// finds all line segments containing 4 points
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

		lsList = new ArrayList<>();
		if (copy.length>=4){
			for (int i=0;i<copy.length-3;i++){
				for (int j=i+1;j<copy.length-2;j++){
					for (int m=j+1;m<copy.length-1;m++){
						for (int n=m+1;n<copy.length;n++){
							if (isCollinear(copy[i], copy[j], copy[m], copy[n])){
								lsList.add(new LineSegment(copy[i], copy[n]));
							}
						}
					}
				}
			}
		}
		lsArray = lsList.toArray(lsArray);

	}

	private boolean isCollinear(Point A, Point B, Point C, Point D){
		double slope1 = A.slopeTo(B);
		double slope2 = A.slopeTo(C);
		double slope3 = A.slopeTo(D);
		return slope1 == slope2 && slope2 == slope3;
	}

	public int numberOfSegments(){
		// the number of line segments
		return lsArray.length;
	}
	public LineSegment[] segments(){
		return lsArray.clone();
	}

	//	public static void main(String[] args){
	//		Point[] points = new Point[4];
	//		points[0] = new Point(8,9);
	//		points[1] = new Point(0,1);
	//		points[2] = new Point(3,4);
	//		points[3] = new Point(2,3);
	//		BruteCollinearPoints bcp = new BruteCollinearPoints(points);
	//		System.out.println(bcp.count);
	//		LineSegment[] lsArray = bcp.segments();
	//		for (LineSegment ls : lsArray){
	//			System.out.println(ls);
	//		}
	//		
	//		Point[] points = new Point[4];
	//		points[0] = new Point(8,9);
	//		points[1] = new Point(0,1);
	//		points[2] = new Point(3,4);
	//		points[3] = new Point(2,3);
	//		
	//		Point[] copy = points.clone();
	//		copy[0] = new Point(7,7);
	//		for (Point p : points){
	//			System.out.println(p);
	//		}
	//		
	//	}
}
