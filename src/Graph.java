import java.util.*;

public class Graph {
	int n;
	double p;
	double adjacencyMatrix[][];
	
	/*
	 * Default Constructor
	 */
	public Graph() {
		n = 0;
		p = 0.0;
		adjacencyMatrix = new double[n][n];
	}
	
	/*
	 * Constructor
	 */
	public Graph(int numberOfN, double probability) {
		n = numberOfN;
		p = probability;
		adjacencyMatrix = new double[n][n];
		initAdjacency();
	}
	
	/*
	 * Main method for testing the Graph class
	 */
	public static void main(String[] args) {
		Graph testGraph = new Graph(10, .9);
		double testMatrix[][];
		testMatrix = testGraph.getAdjacency();
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.print(testMatrix[j][i] + " | ");
			}
			System.out.println("");
		}
	}

	/*
	 * Initialize the top right half of the matrix with edge connections
	 * and edge weights for each pair.
	 */
	private void initAdjacency() {
		Random randomP = new Random();
		double nextP, weight;
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < i; j++) {
				nextP = randomP.nextDouble();
				if(nextP < p) {
					weight = randomP.nextDouble();
					adjacencyMatrix[i][j] = weight;
				}
				else {
					adjacencyMatrix[i][j] = -1;
				}
			}
		}
		
	}
	
	/*
	 * Returns the adjacency matrix
	 */
	public double[][] getAdjacency() {
		return adjacencyMatrix;
	}
	
}
