import java.util.*;

public class Prim
{
	public static double[][] primsMST(Graph g)
	{
		double[][] graph = g.getAdjacency();
		int length = graph.length;
		
		double[][] prev = new double[length][length];		
		double[][] cost = new double[length][length];
		
		//Creating priority queue of size V
		PriorityQueue<Integer[]> pq = new PriorityQueue<Integer[]>(length);
		
		for(int i = 0; i < length; i++) 
		{	
			for(int j = 0; j < i; j++)
			{
				cost[i][j] = Integer.MAX_VALUE;	
				prev[i][j] = -1;
			}
		}
		
		cost[0][0] = 0;
		
		//fill queue?
		
		while(pq.size()!=0)
		{
			
			for(int i=0; i<length; i++)
			{
				for(int j = 0; j < i; j++)
				{
					if(cost[i][j] > graph[i][j])
					{
						cost[i][j] = graph[i][j];
						prev[i][j] = i;
					}
				}
			}
		}
		
		return prev;
	}
}