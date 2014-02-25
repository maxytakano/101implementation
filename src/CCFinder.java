import java.util.LinkedList;
import java.util.HashSet;


public class CCFinder {

	public static LinkedList<Integer[]> findCCs(double graph[][])
	{
		HashSet<Integer> vertices = new HashSet<Integer>();
		for (int i = 0; i < graph[0].length; i++)
		{
			vertices.add(i);
		}
		
		LinkedList<Integer[]> conComps = new LinkedList<Integer[]>();
		
		while (vertices.size() != 0)
		{
			Integer[] conComp;
			for (int i = 0; i < graph[0].length; i++)
			{
				if (!vertices.contains(i))
					continue;
				
				conComp = dfs(graph, i, new HashSet<Integer>());
				conComps.add(conComp);
				for (int j = 0; j < conComp.length; j++)
				{
					vertices.remove(conComp[j]);
				}
			}
		}

		return conComps;
	}
	
	public static Integer[] dfs(double graph[][], int vertex, HashSet<Integer> set)
	{
		set.add(vertex);
		for (int i = 0; i < graph[vertex].length; i++)
		{
			double edge;
			if (i == vertex || set.contains(i))
				continue; // skip when i == vertex
			else if (i > vertex)
			{
				edge = graph[i][vertex];
			}
			else // i < vertex
			{
				edge = graph[vertex][i];
			}
			
			if (edge < 0)
				continue;
			else
				dfs(graph, i, set);
		}
		return set.toArray(new Integer[0]);
	}
	
	// tests
	public static void main(String[] args)
	{
		Graph graph = new Graph(4, 1);
		LinkedList<Integer[]> ccs = findCCs(graph.getAdjacency());
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
		
		System.out.println("\n\n");
		
		Graph graph2 = new Graph(6, .1);
		LinkedList<Integer[]> ccs2 = findCCs(graph2.getAdjacency());
		System.out.println("graph for n = 6 and p = .1:");
		for(int i = 0; i < graph2.n; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < graph2.n; j++) {
				System.out.print(graph2.getAdjacency()[j][i] + " | ");
			}
			System.out.println("");
		}
		System.out.println("CCs for this graph:");
		for (int i = 0; i < ccs2.size(); i++)
		{
			Integer[] cc2 = ccs2.get(i);
			System.out.print("CC " + i + " : ");
			for (int j = 0; j < cc2.length; j++)
			{
				System.out.print(cc2[j] + ", ");
			}
			System.out.println();
		}
		
		System.out.println("\n\n");
		
//		Graph graph3 = new Graph(6, .1);
		double[][] graph3= {{0, 0, 0, 0, 0, 0}, {-1, 0, 0, 0, 0, 0},
				{-1, -1, 0, 0, 0, 0}, {0.57, -1, -1, 0, 0, 0},
				{-1, 0.09, -1, -1, 0, 0}, {-1, -1, -1, -1, -1, 0}};
		LinkedList<Integer[]> ccs3 = findCCs(graph3);
		System.out.println("graph for n = 6 and 4 CCs");
		for(int i = 0; i < graph3[0].length; i++) {
			System.out.print("v" + i +": ");
			for(int j = 0; j < graph3[0].length; j++) {
				System.out.print(graph3[j][i] + " | ");
			}
			System.out.println("");
		}
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
	}
	
}
