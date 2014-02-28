import java.util.LinkedList;
import java.io.*;
import java.text.DecimalFormat;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		computeCCs(20);
//		computeCCs(100);
//		computeCCs(500);
		computeCCs(1000);
			
	}
	
	public static void computeCCs(int n) {
		
//		double times[] = new double[51];
		double p = 0;
		Stats timer = new Stats();
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		
		BufferedWriter runTimeOut;
		BufferedWriter numCCOut;
		
		try
		{
			runTimeOut = new BufferedWriter(new FileWriter(new File(n + "ccRunTimes")));
			numCCOut = new BufferedWriter(new FileWriter(new File(n + "ccStats")));
			
			
			runTimeOut.write("#p runTime\n");
			numCCOut.write("#p mean stdDev\n");
			
			for(int i = 0; i <= 50; i++) {
				System.out.println("n = " + n + ", p = " + p);
				timer.resetTimes();
				timer.start();
				
				long connComps[] = new long[500];
				for (int j = 0; j < 500	; j++)
				{
					Graph g = new Graph(n, p);
					
					LinkedList<Integer[]> cc = CCFinder.findCCs(g.adjacencyMatrix);
					connComps[j] = cc.size();
				}
				
				double mean = Stats.calcMean(connComps);
				double stdDev = Stats.stdDev(connComps);
				
				timer.end();
				double runTime = timer.runTime();
				
				runTimeOut.write(fmt.format(p) + " " + runTime + "\n");
				numCCOut.write(fmt.format(p) + " " + mean + " " + stdDev + "\n");
				p = p + .02;
			}
			
			runTimeOut.close();
			numCCOut.close();
		} 
		catch (Exception e)
		{
			System.err.println("I got probs!");
		}
		
		
		
		return;
	}
	

}
