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
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				Main.computeCCs(20);
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				Main.computeCCs(100);
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			public void run() {
				Main.computeCCs(500);
			}
		});
		
		Thread thread4 = new Thread(new Runnable() {
			public void run() {
				Main.computeCCs(1000);
			}
		});
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();

//		computeCCs(20);
//		computeCCs(100);
//		computeCCs(500);
//		computeCCs(1000);
//		recursePTimes(new File("C:\\Users\\danny_000\\Desktop\\impProj"));
			
	}
	
	public static void computeCCs(int n) {
		
//		double times[] = new double[51];
		double p = 0;
		Stats timer = new Stats();
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		
		BufferedWriter runTimeOut;
		BufferedWriter meanCCOut;
		BufferedWriter stdCCOut;
		
		try
		{
			runTimeOut = new BufferedWriter(new FileWriter(new File(n + "ccRunTimes")));
			meanCCOut = new BufferedWriter(new FileWriter(new File(n + "ccStatsMean")));
			stdCCOut = new BufferedWriter(new FileWriter(new File(n + "ccStatsStd")));
			
			
			runTimeOut.write("#p runTime\n");
			meanCCOut.write("#p mean\n");
			stdCCOut.write("#p stdDev\n");
			
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
				meanCCOut.write(fmt.format(p) + " " + mean + "\n");
				stdCCOut.write(fmt.format(p) + " " + stdDev + "\n");
				p = p + .02;
			}
			
			runTimeOut.close();
			meanCCOut.close();
			stdCCOut.close();
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
			fileReader.close();
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
