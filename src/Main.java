import java.util.LinkedList;
import java.util.Scanner;
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
//		computeCCs(1000);
		recursePTimes(new File("C:\\Users\\danny_000\\Desktop\\impProj"));
			
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
	
	public static void recursePTimes(File node)
	{
		if (!node.isDirectory())
		{
			System.err.println("File " + node.getAbsoluteFile() +
					" is not a directory.");
			return;
		}
		
		System.out.println("Recursing in directory " + node.getAbsoluteFile());
		String[] fileNames = node.list();
		
		for (int i = 0; i < fileNames.length; i++)
		{
			String currFile = fileNames[i];
			if (currFile.contains("ccRunTimes"))
			{
				printTimes(new File(node.getAbsoluteFile()+
						"\\" + currFile));
			}
		}
	}
	
	public static void printTimes(File node){
		
		System.out.println("Current file: " + node.getAbsoluteFile());
 
		double totalTime = 0;
		
		try
		{
			Scanner fileReader = new Scanner(node.getAbsoluteFile());
			while (fileReader.hasNextLine())
			{
				String line = fileReader.nextLine();
				String[] nums;
				
				if (line.contains("#") || line.isEmpty())
					continue; //skip
				else
				{
					nums = line.split(" ");
				}
				totalTime += Double.parseDouble(nums[1]);
			}
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		String units = "ms";
		if (totalTime > 1000)
		{
			totalTime = totalTime / 1000;
			units = "s";
			if (totalTime > 60)
			{
				totalTime = totalTime / 60;
				units = "mins";
				if (totalTime > 60)
				{
					totalTime = totalTime / 60;
					units = "hrs";
				}
			}
		}
		
		System.out.println("Total time taken: " + totalTime + units);
 
	}
	

}
