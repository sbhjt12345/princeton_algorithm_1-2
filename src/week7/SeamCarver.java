package week7;
import java.awt.Color;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture pic;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture){
		this.validate(picture);
		pic = new Picture(picture);
	}

	// current picture
	public Picture picture(){
		return new Picture(pic);

	}

	// width of current picture
	public int width(){
		return pic.width();
	}

	// height of current picture
	public int height(){
		return pic.height();
	}
	
	// energy of pixel at column x and row y - width x and height y
	public double energy(int x, int y){
		if (x<0 || y<0 || x>=width() || y>=height()) throw new IllegalArgumentException("out of range");
		if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) return 1000;
		return Math.sqrt(energy(x-1, y, x+1, y) + energy(x, y-1, x, y+1));
	}
	
	private int energy(int a, int b, int c, int d){
		int rgb1 = pic.get(a, b).getRGB();
        int rgb2 = pic.get(c, d).getRGB();

        int red1 = (rgb1 >> 16) & 0xFF;   // the first 8 bits
        int red2 = (rgb2 >> 16) & 0xFF;
        int red = red2 - red1;

        int green1 = (rgb1 >> 8) & 0xFF;   // 8 bits in the middle
        int green2 = (rgb2 >> 8) & 0xFF;
        int green = green2 - green1;

        int blue1 = (rgb1) & 0xFF;         // last 8 bits
        int blue2 = (rgb2) & 0xFF;
        int blue = blue2 - blue1;
		
		return red*red + blue*blue + green*green;
	}
	
	private double[][] energyList(){
		double[][] eList = new double[width()][height()];
		for (int x = 0 ; x < pic.width(); x++){
			for (int y = 0; y < pic.height(); y++){
				eList[x][y] = energy(x, y);
			}
		}
		return eList;
	}
	
	private Bag<Integer> adj(int x, int y){
		Bag<Integer> bag = new Bag<Integer>();
		if (y != pic.height() - 1){
			bag.add(x);
			if (x < pic.width()-1) bag.add(x+1);
			if (x > 0) bag.add(x-1);
		}
		return bag;
		}
	
	private double[][] distTo(){
		double[][] distTo = new double[width()][height()];
		for (int i=0;i<width();i++){
			for (int j=0;j<height();j++){
				if (j==0) distTo[i][j] = 0;
				else distTo[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		return distTo;
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam(){
		transpose();
		int[] res = this.findVerticalSeam();
//		for (int i=0;i<res.length;i++){
//			res[i] = this.width() - 1 - res[i];
//		}
		transposeBack();
		return res;
		
	}
	
	private void transpose(){
		Picture rotate = new Picture(height(), width());    
		for (int i=0;i<width();i++){
			for (int j=0;j<height();j++){
				rotate.set(j,i,pic.get(i, j));
			}
		}
		this.pic = rotate;
	}
	
	private void transposeBack(){
		Picture rotate = new Picture(height(), width());
		for (int i=0;i<height();i++){
			for (int j=0;j<width();j++){
				rotate.set(i, j, pic.get(j, i));
			}
		}
		this.pic = rotate;
	}
	
	

	// sequence of indices for vertical seam
	public int[] findVerticalSeam(){
		double[][] distTo = this.distTo();
		double[][] elist = this.energyList();
		int[][] edgeTo = new int[width()][height()];   // just store width here
		for (int i=0; i<pic.height();i++){
			for (int j=0;j<pic.width();j++){
				relax(j,i,distTo, elist,edgeTo);
//				System.out.println(j+" th col, "+i+" th row");
			}
		}
		double least = Double.POSITIVE_INFINITY;
		int leastCol = -1;
		for (int i=0;i<pic.width();i++){
			if (distTo[i][height()-1] < least){
				least = distTo[i][height()-1];
				leastCol = i;
			}
		}
		int[] res = new int[height()];
		int h = height()-1;
		while (h>=0) {
			res[h] = leastCol;
			leastCol = edgeTo[leastCol][h--];
		}
		return res;
	}
	
	
	private void relax(int curW, int curH, double[][] distTo, double[][] elist, int[][] edgeTo){
		int hei = curH + 1;
		for (Integer wid : this.adj(curW, curH)){
			if (elist[wid][hei] + distTo[curW][curH] < distTo[wid][hei]){
				distTo[wid][hei] = elist[wid][hei] + distTo[curW][curH];
				edgeTo[wid][hei] = curW;
			}
		}
	}
	

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam){
		this.validate(seam);
		this.checkArray(seam, true);
		Picture newPic = new Picture(width(), height()-1);
		for (int i=0;i<seam.length;i++){
			int tokill = seam[i];
			for (int j=0;j<height();j++){
				if (j < tokill){
					newPic.set(i, j, pic.get(i, j));
				}
				else if (j > tokill){
					newPic.set(i, j-1, pic.get(i, j));
				}
			}
		}
		this.pic = newPic;
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam){
		this.validate(seam);
		this.checkArray(seam, false);
		Picture newPic = new Picture(width()-1, height());
		for (int i=0;i<seam.length;i++){   // every loop is for every col, ie width
			int tokill = seam[i];
			for (int j=0;j<width();j++){
				if (j < tokill){
					newPic.set(j,i, pic.get(j, i));
				}
				else if (j > tokill){
					newPic.set(j-1, i, pic.get(j, i));
				}
			}
		}
		this.pic = newPic;
	}
	
	private void validate(Object obj){
		if (obj==null) throw new IllegalArgumentException("null Argument");
	}
	
	private void checkArray(int[] seam, boolean isWidth){
		if (isWidth){
			if (seam.length != width()) throw new IllegalArgumentException("horizon not right");
			if (height() <= 1) throw new IllegalArgumentException("too low");
		}
		else{
			if (seam.length != height()) throw new IllegalArgumentException("vertical not right");
			if (width() <= 1) throw new IllegalArgumentException("too narrow");
		}
		for (int i=0;i<seam.length-1;i++){
			if (Math.abs(seam[i+1] - seam[i]) > 1) throw new IllegalArgumentException("not neighbor");
		}
		
	}

	//  unit testing (optional)
	public static void main(String[] args){
		Picture pic = new Picture(args[0]);
		SeamCarver sc = new SeamCarver(pic);
		int[] res = sc.findHorizontalSeam();
		for (int i : res) System.out.println(i);
		
		
	}

}
