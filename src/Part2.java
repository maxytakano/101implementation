import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Part2 {
	
	public static void main(String[] args) {	
		computeShells(5); // one initial test to get rid of jitter ignore its time
		computeShells(20);
		computeShells(50);
		computeShells(100);
		computeShells(200);
		computeShells(500);
		computeShells(1000);
		computeShells(2000);
		computeShells(3000);	
	}
	
	public static void computeShells(int n) {
		
		Stats timer = new Stats();
		
		BufferedWriter runTimeOut;
		
		try
		{
			//runTimeOut = new BufferedWriter(new FileWriter(new File(n + "ccRunTimes")));
			
			//runTimeOut.write("#p runTime\n");
							
			timer.resetTimes();
			timer.start();
			
			RandomPoints r = new RandomPoints(n);
			int numberShells = r.findShellNumber();
				
			timer.end();
			double runTime = timer.runTime();
			
			//runTimeOut.write(n + " " + runTime + "\n");
			System.out.println(n + " runTime: " + runTime + "ms");
			System.out.println(n + " numberShells: " + numberShells + " shells");
				
			//runTimeOut.close();
		} 
		catch (Exception e)
		{
			System.err.println("Write errors");
		}
		
		return;
	}
	
}
