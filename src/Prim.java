import java.util.*;

public class Prim
{
	public static int[] primsMST(double[][] g)
	{
		double[][] graph = g;
		int length = graph.length;
		
		int[] prev = new int[length];	
		Vertex[] cost = new Vertex[length];
		
		Comparer<Vertex> c = new Comparer<Vertex>();
		
		//Creating priority queue of size V
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(length, c);
		
		//Pick any initial node, set its cost to 0, and add it to the priority queue
		Vertex v0 = new Vertex(0, 0);
		pq.add(v0);
		cost[0] = v0;
		prev[0] = -1;
		
		//Initialize every other vertex to have a cost of infinity and add it to the priority queue
		for(int i = 1; i < length; i++) 
		{	
			Vertex v1 = new Vertex(i, Double.MAX_VALUE);
			pq.add(v1);
			
			cost[i] = v1;
			prev[i] = -1;
		}
		
		boolean[] inPQ = new boolean[length];
		for (int i = 0; i < inPQ.length; i ++)
		{
			inPQ[i] = true;
		}
		
		while(pq.size()!=0)
		{
			//removes the vertex with the smallest cost in the pq
			Vertex v = pq.poll();
			int i = v.vert;
			inPQ[i] = false;
			
			for(int j = 0; j < i; j++)
			{
				if(graph[i][j] >= 0)
				{
					//System.out.println(graph[i][j]);
					if(cost[j].getCost() > graph[i][j] && inPQ[j])
					{
						Vertex vertexJ = cost[j];
						pq.remove(vertexJ);
						
						vertexJ.setCost(graph[i][j]);
						pq.add(vertexJ);
						
						cost[j] = vertexJ;
						prev[j] = i;
					}
				}
			}
			
			for(int j = i+1; j < length; j++)
			{
				if(graph[j][i] >= 0)
				{
					if(cost[j].getCost() > graph[j][i] && inPQ[j])
					{
						Vertex vertexJ = cost[j];
						pq.remove(vertexJ);
						
						vertexJ.setCost(graph[j][i]);
						pq.add(vertexJ);
						
						cost[j] = vertexJ;
						prev[j] = i;
					}
				}
			}
		}
		
		return prev;
	}
	
	public static double[][] outputGraph(double[][] graph, int prev[])
	{
		double[][] newGraph = new double[prev.length][prev.length];
		
		// Initialize all possible edges to -1 to indicate no edge
		for (int i = 1; i < newGraph[0].length; i++)
		{
			for (int j = 0; j < i; j++)
			{
				newGraph[i][j] = -1;
			}
		}
		
		int i=1;
		
		for(; i<prev.length; i++)
		{
			int j = prev[i];
//			System.out.println(j);
			if(i>j)
			{
				newGraph[i][j] = graph[i][j];
			}
			else if(i<j)
				newGraph[j][i] = graph[j][i];
		}
	
		return newGraph;
	}
	
	public static double calcMSTCost(double[][] graph, int[] prevs)
	{
		double totalCost = 0;
//		System.out.println("Prevs array: ");
		for (int i = 0; i < prevs.length; i++)
		{
//			System.out.print(prevs[i] + ", ");
			int prev = prevs[i];
			if (prev == -1)
				continue;
			
			if (i < prev)
				totalCost += graph[prev][i];
			else
				totalCost += graph[i][prev];
		}
//		System.out.println(" ");
		return totalCost;
		
	}
	
	public static double[] bfs(double[][] mst, int startIndex)
	{
		double[] dist = new double[mst.length];
		
		if(startIndex < mst.length);
		{
		
			for(int i=0; i<dist.length; i++)
			{
				dist[i] = Integer.MAX_VALUE;
			}
		
			dist[startIndex] = 0;
		
			LinkedList<Integer> q = new LinkedList<Integer>();
			q.add(startIndex);
		
			while(q.size()!=0)
			{
//				System.out.println("Entered while");
			
				int u = q.removeLast();
			
				//For all edges (u,v) in E
				for(int v = 0; v < u; v++)
				{
//					System.out.println("Entered for");
//					System.out.println("mst[u][v]: "+mst[u][v]);
					if(mst[u][v] > 0)
					{
//						System.out.println("Entered first if");
						if(dist[v] == Integer.MAX_VALUE)
						{
							q.addFirst(v);
							dist[v] = dist[u] + 1;
//							System.out.println(v+": "+dist[v]);
						}
					}
				}
			
				for(int v = u+1; v < mst.length; v++)
				{
					if(mst[v][u] > 0)
					{
						if(dist[v] == Integer.MAX_VALUE)
						{
							q.addFirst(v);
							dist[v] = dist[u] + 1;
						}
					}
				}
			}
		}
		return dist;
	}
	
	public static double diameter(double[][] graph)
	{
		double diam = 0;
		double radius = 0;
		int vertex = 0;
		
		double[] firstBFS = bfs(graph, vertex);
		
		for(int i=0; i<firstBFS.length; i++)
		{
			if(firstBFS[i] > radius)
			{
				radius = firstBFS[i];
				vertex = i;
			}
		}
		
		double[] secondBFS = bfs(graph, vertex);
		
		for(int i=0; i<secondBFS.length; i++)
		{
			if(secondBFS[i] > diam)
			{
				diam = secondBFS[i];
			}
		}
		
		return diam;
	}
	
	public static class Vertex
	{
	  int vert;
	  double cost;
	  
	  public Vertex(int v, double c)
	  {
	     this.vert = v;
	     this.cost = c;
	  }
	  
	  public boolean equals(Vertex v)
	  {
		 if(this.vert == v.vert && this.cost == v.getCost())
			 return true;
		 else
			 return false;
	  }
	  
	  public double getCost()
	  {
		return this.cost;
	  }
	  
	  public void setCost(double c)
	  {
		this.cost = c;
	  }
	}
	
	public static class Comparer<V> implements Comparator<V>
	{
		public int compare(V v1, V v2)
		{
			Vertex vert1 = (Vertex) v1;
			Vertex vert2 = (Vertex) v2;
			
			if(vert1.getCost() < vert2.getCost())
				return -1;
			else if(vert1.getCost() > vert2.getCost())
				return 1;
			else
				return 0;
		}
		
		public boolean equals(Object v1)
		{
			return true;
		}
	}
	
	public static void main(String[] args)
	{
		Graph graph = new Graph(4, 1);
		LinkedList<Integer[]> ccs = CCFinder.findCCs(graph.getAdjacency());
		System.out.println("graph for n = 4 and p = 1:");
		for(int i = 0; i < graph.n; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < graph.n; j++) {
				System.out.print(graph.getAdjacency()[j][i] + " | ");
			}
			System.out.println("");
		}
		System.out.println("CCs for this graph:");
		for (int i = 0; i < ccs.size(); i++)
		{
			Integer[] cc = ccs.get(i);
			System.out.print("CC " + i + " : ");
			for (int j = 0; j < cc.length; j++)
			{
				System.out.print(cc[j] + ", ");
			}
			System.out.println();
		}
		
		int[] mstEdges = primsMST(graph.getAdjacency());
		double totalCost = calcMSTCost(graph.getAdjacency(), mstEdges);

		System.out.println("Total Mst Cost:\n" + totalCost);
		
//		System.out.println("\n\n");
//		
//		Graph graph2 = new Graph(6, .1);
//		LinkedList<Integer[]> ccs2 = CCFinder.findCCs(graph2.getAdjacency());
//		System.out.println("graph for n = 6 and p = .1:");
//		for(int i = 0; i < graph2.n; i++) {
//			System.out.print("v" + i +": ");
//			for(int j = 0; j < graph2.n; j++) {
//				System.out.print(graph2.getAdjacency()[j][i] + " | ");
//			}
//			System.out.println("");
//		}
//		System.out.println("CCs for this graph:");
//		for (int i = 0; i < ccs2.size(); i++)
//		{
//			Integer[] cc2 = ccs2.get(i);
//			System.out.print("CC " + i + " : ");
//			for (int j = 0; j < cc2.length; j++)
//			{
//				System.out.print(cc2[j] + ", ");
//			}
//			System.out.println();
//		}
//		
//		System.out.println("\n\n");
//		
////		Graph graph3 = new Graph(6, .1);
//		double[][] graph3= {{0, 0, 0, 0, 0, 0}, {-1, 0, 0, 0, 0, 0},
//				{-1, -1, 0, 0, 0, 0}, {0.57, -1, -1, 0, 0, 0},
//				{-1, 0.09, -1, -1, 0, 0}, {-1, -1, -1, -1, -1, 0}};
//		LinkedList<Integer[]> ccs3 = CCFinder.findCCs(graph3);
//		System.out.println("graph for n = 6 and 4 CCs");
//		for(int i = 0; i < graph3[0].length; i++) {
//			System.out.print("v" + i +": ");
//			for(int j = 0; j < graph3[0].length; j++) {
//				System.out.print(graph3[j][i] + " | ");
//			}
//			System.out.println("");
//		}
//		System.out.println("CCs for this graph:");
//		for (int i = 0; i < ccs3.size(); i++)
//		{
//			Integer[] cc3 = ccs3.get(i);
//			System.out.print("CC " + i + " : ");
//			for (int j = 0; j < cc3.length; j++)
//			{
//				System.out.print(cc3[j] + ", ");
//			}
//			System.out.println();
//		}
		
		System.out.println("\n\n");
		
//		Graph graph3 = new Graph(6, .1);
		double[][] graph4= {{0, 0, 0, 0}, {.809, 0, 0, 0},
				{.61, .497, 0, 0}, {.75, .704, .146, 0}};
		LinkedList<Integer[]> ccs4 = CCFinder.findCCs(graph4);
		System.out.println("graph for n = 4 and 1 CC");
		for(int i = 0; i < graph4[0].length; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < graph4[0].length; j++) {
				System.out.print(graph4[j][i] + " | ");
			}
			System.out.println("");
		}
		System.out.println("CCs for this graph:");
		for (int i = 0; i < ccs4.size(); i++)
		{
			Integer[] cc4 = ccs4.get(i);
			System.out.print("CC " + i + " : ");
			for (int j = 0; j < cc4.length; j++)
			{
				System.out.print(cc4[j] + ", ");
			}
			System.out.println();
		}
		
		int[] mstEdges4 = primsMST(graph4);
		double totalCost4 = calcMSTCost(graph4, mstEdges4);
		
		System.out.println("Total Mst Cost Calculated:\n" + totalCost4);
		System.out.println("Total Actual Mst Cost:\n" + 1.253);
		
		//Graph Neg Edges Test
		double[][] graphNeg= {{0, 0, 0, 0}, {.809, 0, 0, 0},
				{-1, .497, 0, 0}, {.75, .704, -1, 0}};
		LinkedList<Integer[]> ccsNeg = CCFinder.findCCs(graphNeg);
		System.out.println("graph for n = 4 and 1 CC");
		for(int i = 0; i < graphNeg[0].length; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < graphNeg[0].length; j++) {
				System.out.print(graphNeg[j][i] + " | ");
			}
			System.out.println("");
		}
		System.out.println("CCs for this graph:");
		for (int i = 0; i < ccsNeg.size(); i++)
		{
			Integer[] ccNeg = ccsNeg.get(i);
			System.out.print("CC " + i + " : ");
			for (int j = 0; j < ccNeg.length; j++)
			{
				System.out.print(ccNeg[j] + ", ");
			}
			System.out.println();
		}
		
		int[] mstEdgesNeg = primsMST(graphNeg);
		double totalCostNeg = calcMSTCost(graphNeg, mstEdgesNeg);
		
		System.out.println("Total Mst Cost Calculated:\n" + totalCostNeg);
		
		double[][] mstGraph = outputGraph(graphNeg, primsMST(graphNeg));
		for(int i = 0; i < mstGraph[0].length; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < mstGraph[0].length; j++) {
				System.out.print(mstGraph[j][i] + " | ");
			}
			System.out.println("");
		}
		double[] d = bfs(mstGraph, 0);
		System.out.println("BFS: ");
		for(int i=0; i<d.length; i++)
		{
			System.out.print(d[i] + " | ");
		}
		System.out.println("");
		
		double diam = diameter(mstGraph);
		System.out.println("Diameter: "+diam);
		
		////////////////////////////////////////
		System.out.println("\n\n");
		
//		Graph graph3 = new Graph(6, .1);
		double[][] graph3= {{0, 0, 0, 0, 0, 0}, {-1, 0, 0, 0, 0, 0},
				{0.3, -1, 0, 0, 0, 0}, {0.57, -1, -1, 0, 0, 0},
				{-1, 0.09, -1, -1, 0, 0}, {-1, -1, -1, -1, -1, 0}};
		LinkedList<Integer[]> ccs3 = CCFinder.findCCs(graph3);
		System.out.println("graph for n = 6 and 4 CCs");
		CCFinder.printGraph(graph3);
//		for(int i = 0; i < graph3[0].length; i++) {
//			System.out.print("v" + i +": ");
//			for(int j = 0; j < graph3[0].length; j++) {
//				System.out.print(graph3[j][i] + " | ");
//			}
//			System.out.println("");
//		}
		System.out.println("CCs for this graph:");
		for (int i = 0; i < ccs3.size(); i++)
		{
			Integer[] cc3 = ccs3.get(i);
			System.out.print("CC " + i + " : ");
			for (int j = 0; j < cc3.length; j++)
			{
				System.out.print(cc3[j] + ", ");
			}
			System.out.println();
		}
		System.out.println("Individual CC graphs:");
		LinkedList<double[][]> cc3Graphs = CCFinder.graphCCs(ccs3, graph3);
		
		Iterator<double[][]> it3 = cc3Graphs.iterator();
		for (int m = 0; m < cc3Graphs.size(); m++)
		{
			double[][] currGraph3 = it3.next();
			System.out.println("Graph for CC" + m + ":");
			CCFinder.printGraph(currGraph3);
			
			int[] mstEdges3 = primsMST(currGraph3);
			double totalCost3 = calcMSTCost(currGraph3, mstEdges3);
			
			System.out.println("Total Mst Cost Calculated:\n" + totalCost3);
			
			double[][] mstGraph3 = outputGraph(currGraph3, primsMST(currGraph3));
			CCFinder.printGraph(mstGraph3);
//			for(int i = 0; i < mstGraph3[0].length; i++) {
//				System.out.print("v" + i +": ");
//				for(int j = 0; j < mstGraph[0].length; j++) {
//					System.out.print(mstGraph[j][i] + " | ");
//				}
//				System.out.println("");
//			}
		}
		
		
		
	}
}


