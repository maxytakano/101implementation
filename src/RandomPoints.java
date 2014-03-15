
import java.util.*;
import java.util.List;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/*
 * Holds an array of points which each have the x and y of the randomly
 * generated point they represent.
 */
public class RandomPoints extends JFrame{
	private int n;
	private double r;
	private List<Point2D.Double> points = new ArrayList<Point2D.Double>();
	
	Random randomD = new Random();
	private double theta;
	private double radius;
	private List<Point2D.Double> hullPoints = new ArrayList<Point2D.Double>();
	
	public RandomPoints(int number) {
		n = number;
		generateRandomPoints();
	}
	
	public RandomPoints(int number, double r) {
		this.r = r;
		n = number;
		generateAnnulusPoints();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		
		for (int i = 0; i < points.size(); i++) {
			g.fillOval( ((int)(points.get(i).x * 100)) + 400, 
				((int)(points.get(i).y * 100)) + 400, 2, 2);
		}
		
		g.setColor(Color.red);
		for (int j = 0; j < hullPoints.size(); j++) {
			g.fillOval( ((int)(hullPoints.get(j).x * 100)) + 400, 
					((int)(hullPoints.get(j).y * 100)) + 400, 2, 2);
		}
		
	}
	
	/*
	 * Used for testing, uses swing graphics to draw convex hull and stuff
	 */
	public static void main(String[] args) {
		//RandomPoints random = new RandomPoints(200);
		RandomPoints random = new RandomPoints(200, .6);
		random.setSize(800,800);
		
		random.setVisible(true);
		
		while(random.points.isEmpty() == false) {
			GrahamScan g = new GrahamScan();
			random.hullPoints = g.getConvexHull(random.points);
			
			//random.hullPoints = g.getConvexHull(random.points);
			
			random.deleteHullPoints(random.points, random.hullPoints);
			
			random.repaint();
			
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
		}
	}
	
	/*
	 * Iteratively deletes each convex hull from the point set
	 */
	public int findShellNumber() {
		int count = 0;
		GrahamScan g = new GrahamScan();
		
		while (points.isEmpty() == false) {
			hullPoints = g.getConvexHull(points);
			deleteHullPoints(points, hullPoints);
			count++;
		}
		
		return count; 
	}
	
	/*
	 * Deletes the current convex hull
	 */
	private void deleteHullPoints(List<Point2D.Double> points, List<Point2D.Double> hull) {
		for (int i = 0; i < hull.size(); i++) {
			points.remove(hull.get(i));
		}
	}
	
	/*
	 * Generates random points from 0,1
	 */
	private void generateRandomPoints() {
		for (int i = 0; i < n; i++) {
			theta = 2*Math.PI*randomD.nextDouble();
			radius = randomD.nextDouble();
			points.add(new Point2D.Double((double) radius*Math.cos(theta), 
					(double) radius*Math.sin(theta)));
		}
	}
	
	/*
	 * Generates random points in the annulus bound
	 */
	private void generateAnnulusPoints() {
		for (int i = 0; i < n; i++) {
			theta = 2*Math.PI*randomD.nextDouble();
			
			double Annulus = 2 / ( (1*1) - (r*r) );

			radius = Math.sqrt( ((2 * randomD.nextDouble()) / Annulus) + (r*r) );

			double x = radius * Math.cos(theta);
			double y = radius * Math.sin(theta);
			System.out.println(radius);
			
			points.add(new Point2D.Double(x, y));
		}
	}
		
}
