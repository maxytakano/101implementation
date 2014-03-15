import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Part3 {
	
	public static void main(String[] args) {	
		computeAnnulusShells(5); // one initial test to get rid of jitter ignore its time
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				Part3.computeAnnulusShells(20);
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				Part3.computeAnnulusShells(100);
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			public void run() {
				Part3.computeAnnulusShells(500);
			}
		});
		
		Thread thread4 = new Thread(new Runnable() {
			public void run() {
				Part3.computeAnnulusShells(2000);
			}
		});
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
	}
	
	public static void computeAnnulusShells(int n) {
		
		double times[] = new double[51];
		Stats timer = new Stats();
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		
		BufferedWriter runTimeOut;
		BufferedWriter meanShellsOut;
		BufferedWriter stdShellsOut;
		
		try
		{
			runTimeOut = new BufferedWriter(new FileWriter(new File(n + "shellRunTimes")));
			meanShellsOut = new BufferedWriter(new FileWriter(new File(n + "shellStatsMean")));
			stdShellsOut = new BufferedWriter(new FileWriter(new File(n + "shellStatsStd")));
			
			
			runTimeOut.write("#r runTime\n");
			meanShellsOut.write("#r mean\n");
			stdShellsOut.write("#r stdDev\n");
			
			double r;
			
			for(int i = 1; i < 10; i++) {
				r = i*.1;
				System.out.println("n = " + n + ", r = " + r);
				timer.resetTimes();
				timer.start();
				
				long shellCounts[] = new long[200];
				for (int j = 0; j < 200	; j++)
				{
					RandomPoints a = new RandomPoints(n, r);
					int numberShells = a.findShellNumber();
					
					shellCounts[j] = numberShells;
				}
				
				double mean = Stats.calcMean(shellCounts);
				double stdDev = Stats.stdDev(shellCounts);
				
				timer.end();
				double runTime = timer.runTime();
				
				runTimeOut.write(fmt.format(r) + " " + runTime + "\n");
				runTimeOut.flush();
				meanShellsOut.write(fmt.format(r) + " " + mean + "\n");
				runTimeOut.flush();
				stdShellsOut.write(fmt.format(r) + " " + stdDev + "\n");
				runTimeOut.flush();

			}
			
			runTimeOut.close();
			meanShellsOut.close();
			stdShellsOut.close();
		} 
		catch (Exception e)
		{
			System.err.println("I got probs!");
		}
		
		
		
		return;
	}
	
}

