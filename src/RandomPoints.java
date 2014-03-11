
import java.util.Random;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/*
 * Holds an array of points which each have the x and y of the randomly
 * generated point they represent.
 */
public class RandomPoints extends JFrame{
	int n;
	Point2D.Double[] points;
	Random randomD = new Random();
	double theta;
	double radius;
	
	public RandomPoints(int number) {
		n = number;
		points = new Point2D.Double[n];
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		for (int i = 0; i < points.length; i++) {
			g.fillOval( ((int)(points[i].x * 100)) + 400, 
					((int)(points[i].y * 100)) + 400, 2, 2);
		}
		
	}
	
	public static void main(String[] args) {
		RandomPoints random = new RandomPoints(200);
		random.setSize(800,800);
		random.generateRandomPoints();
		
		random.setVisible(true);
		
	}
	
	private void generateRandomPoints() {
		for (int i = 0; i < n; i++) {
			theta = 2*Math.PI*randomD.nextDouble();
			radius = randomD.nextDouble();
			points[i] = new Point2D.Double((double) radius*Math.cos(theta), 
					(double) radius*Math.sin(theta));
		}
	}
	
	
	
	
}
