
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
	private List<Point2D.Double> points = new ArrayList<Point2D.Double>();
	
	Random randomD = new Random();
	private double theta;
	private double radius;
	private List<Point2D.Double> hullPoints = new ArrayList<Point2D.Double>();
	
	public RandomPoints(int number) {
		n = number;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		
		for (int i = 0; i < points.size(); i++) {
			g.fillOval( ((int)(points.get(i).x * 100)) + 400, 
				((int)(points.get(i).y * 100)) + 400, 2, 2);
		}
		
	}
	
	public static void main(String[] args) {
		RandomPoints random = new RandomPoints(200);
		random.setSize(800,800);
		random.generateRandomPoints();
		
		GrahamScan g = new GrahamScan();
		random.hullPoints = g.getConvexHull(random.points);
		
		random.deleteHullPoints(random.points, random.hullPoints);
		
		random.repaint();
		
		random.setVisible(true);
		
	}
	
	private void deleteHullPoints(List<Point2D.Double> points, List<Point2D.Double> hull) {
		for (int i = 0; i < hull.size(); i++) {
			points.remove(hull.get(i));
		}
	}
	
	private void generateRandomPoints() {
		for (int i = 0; i < n; i++) {
			theta = 2*Math.PI*randomD.nextDouble();
			radius = randomD.nextDouble();
			points.add(new Point2D.Double((double) radius*Math.cos(theta), 
					(double) radius*Math.sin(theta)));
		}
	}
	
	
}
