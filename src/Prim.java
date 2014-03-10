import java.util.*;

public class Prim
{
	public static double[] primsMST(Graph g)
	{
		double[][] graph = g.getAdjacency();
		int length = graph.length;
		
		double[] prev = new double[length];	
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
		
		
		while(pq.size()!=0)
		{
			//removes the vertex with the smallest cost in the pq
			Vertex v = pq.poll();
			int i = v.vert;
			
			for(int j = 0; j < i; j++)
			{
				if(cost[j].getCost() > graph[i][j])
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
		
		return prev;
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
}


