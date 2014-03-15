import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;


public class Main {
	
	public class MyRun implements Runnable
	{
	
		private int n;
		private int i;
		private Object[] info;
		
		public MyRun(int n, int i, Object[] info)
		{
			this.n = n;
			this.i = i;
			this.info = info;
		}
		public void run()
		{
			Main.specificPart1(n, i, info);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Run a simple one quick to warm up
		Main.computeCCs(30);
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				Main.doPart1(20);
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				Main.doPart1(100);
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			public void run() {
				Main.doPart1(500);
			}
		});
		
		Thread thread4 = new Thread(new Runnable() {
			public void run() {
				Main.doPart1(1000);
			}
		});
		
		thread1.start();
		thread2.start();
		
		try {
			thread2.join();
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		thread3.start();
		thread4.start();
		try {
			thread4.join();
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}

//		computeCCs(20);
//		computeCCs(100);
//		computeCCs(500);
//		computeCCs(1000);
		recursePTimes(new File("."));
			
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
	
	public static void doPart1(int n)
	{
		
//		double times[] = new double[51];
//		double p = 0;
		Main mainInst = new Main();
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		
		BufferedWriter runTimeOut;
		BufferedWriter meanCCOut;
		BufferedWriter stdCCOut;
		BufferedWriter mstMean;
		BufferedWriter mstDiameter;
		
		try
		{
			runTimeOut = new BufferedWriter(new FileWriter(new File(n + "ccRunTimes")));
			meanCCOut = new BufferedWriter(new FileWriter(new File(n + "ccStatsMean")));
			stdCCOut = new BufferedWriter(new FileWriter(new File(n + "ccStatsStd")));
			mstMean = new BufferedWriter(new FileWriter(new File(n + "mstMean")));
			mstDiameter = new BufferedWriter(new FileWriter(new File(n + "mstDiameter")));
			
			
			runTimeOut.write("#p runTime\n");
			meanCCOut.write("#p mean\n");
			stdCCOut.write("#p stdDev\n");
			mstMean.write("#p mstAvgCost\n");
			mstDiameter.write("#p diameter\n");
			
			Object[] vals = new Object[51];
			Thread[] threads = new Thread[51];
						
			for(int i = 0; i <= 50;) {
				
				for (int j = i; j < i+8 && j <= 50; j++)
				{
					threads[j] = new Thread(mainInst.new MyRun(n, j, vals));
					threads[j].start();
				}
				
				for (int j = i; j < i+8 && j <= 50; j++)
				{
					threads[j].join();
				}
				
				i = i + 8;
			}
			

			for (int i = 0; i <= 50; i++)
			{
//				threads[i].join();
				
				double[] infoStuffs = (double[]) vals[i];
				double mean = infoStuffs[0];
				double stdDev = infoStuffs[1];
				double mstAvgCost = infoStuffs[2];
				double mstAvgDiameter = infoStuffs[3];
				double runTime = infoStuffs[4];
				
				double p = i * 0.02;
				
				runTimeOut.write(fmt.format(p) + " " + runTime + "\n");
				meanCCOut.write(fmt.format(p) + " " + mean + "\n");
				stdCCOut.write(fmt.format(p) + " " + stdDev + "\n");
				mstMean.write(fmt.format(p) + " " + mstAvgCost + "\n");
				mstDiameter.write(fmt.format(p) + " " + mstAvgDiameter + "\n");
			}
			

			runTimeOut.close();
			meanCCOut.close();
			stdCCOut.close();
			mstMean.close();
			mstDiameter.close();
		} 
		catch (Exception e)
		{
			System.err.println("I got probs!");
		}
		
		
		
		return;
	}
	
	public static void specificPart1(int n, int i, Object[] info)
	{
		double p = i * 0.02;
		int trials = 200;
		
		System.out.println("On n =" + n + ", p = " + p);
		
		// Need ccMean, cStd, mstMeanCost, mstMeanDiameter, runTime
		double[] infoStuffs = new double[5];
		
		long connComps[] = new long[trials];
		double mstCosts[] = new double[trials];
		double mstDiameters[] = new double[trials];
		
		Stats timer = new Stats();

		timer.resetTimes();
		timer.start();
		
		for (int j = 0; j < trials; j++)
		{
			
			Graph g = new Graph(n, p);
			
			// Get connected components
			LinkedList<Integer[]> cc = CCFinder.findCCs(g.adjacencyMatrix);
			connComps[j] = cc.size();
			
			// Find MSTs and Costs
			LinkedList<double[][]> ccGraphs = CCFinder.graphCCs(cc, g.adjacencyMatrix);
			double totalCost = 0;
			double totalDiameter = 0;
			for (double[][] currCC : ccGraphs)
			{
				int[] prevs = Prim.primsMST(currCC);
				double mstCost = Prim.calcMSTCost(currCC, prevs);
				totalCost += mstCost;
				
				double[][] mstGraph = Prim.outputGraph(currCC, prevs);
				double diameter = Prim.diameter(mstGraph);
				totalDiameter += diameter;
			}
			double avgMstCost = totalCost/ (double)ccGraphs.size();
			double avgDiameter = totalDiameter/ (double)ccGraphs.size();
			
			mstCosts[j] = avgMstCost;
			mstDiameters[j] = avgDiameter;

		}
		
		double mean = Stats.calcMean(connComps);
		double stdDev = Stats.stdDev(connComps);
		double mstAvgCost = Stats.calcMean(mstCosts);
		double mstDiameter = Stats.calcMean(mstDiameters);
		
		timer.end();
		
		double runTime = timer.runTime();
		
		infoStuffs[0] = mean;
		infoStuffs[1] = stdDev;
		infoStuffs[2] = mstAvgCost;
		infoStuffs[3] = mstDiameter;
		infoStuffs[4] = runTime;
		
		info[i] = infoStuffs;
	
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
		
		String units = " ms";
		if (totalTime > 1000)
		{
			totalTime = totalTime / 1000;
			units = " s";
			if (totalTime > 60)
			{
				totalTime = totalTime / 60;
				units = " mins";
				if (totalTime > 60)
				{
					totalTime = totalTime / 60;
					units = " hrs";
				}
			}
		}
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		
		System.out.println("Total time taken: " + fmt.format(totalTime) + units);
 
	}
	

}
