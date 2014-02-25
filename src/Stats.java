



public class Stats {
	
	long startTime;
	long endTime;

	public Stats()
	{
		resetTimes();
	}
	
	public void start()
	{
		startTime = System.nanoTime();
	}
	
	public void end()
	{
		endTime = System.nanoTime();
	}
	
	public void resetTimes()
	{
		startTime = 0;
		endTime = 0;
	}
	
	// checks to see if startTime and endTime are valid before
	// computing the difference
	// returns -1 if startTime or endTime are invalid
	// returns time in milliseconds if valid
	public double runTime()
	{
		if (startTime == 0 || endTime == 0)
		{
			System.err.println("startTime or endTime not set!");
			return -1;
		}
		if (endTime < startTime)
		{
			System.err.println("endTime less than startTime!");
			return -1;
		}
		
		return (endTime - startTime) / ((double) 1000000);
	}
	
	static double calcMean(long times[])
	{
		long total = 0;
		for (int i = 0; i < times.length; i++)
		{
			total += times[i];
		}
		double mean = ((double)total) / ((double)times.length);
		return mean;
	}
	
	static double stdDev(long times[])
	{
		double mean = calcMean(times);
		
		double devSum = 0;
		for (int i = 0; i < times.length; i++)
		{
			double diff = ((double)times[i]) - mean;
			devSum += diff*diff;
		}
		double variance = devSum/ ((double)times.length);
		
		return Math.sqrt(variance);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// test this class
		Stats myStats = new Stats();
		
		myStats.start();
//		myStats.end();
		long[] times = {600, 470, 170, 430, 300};
		double mean = calcMean(times);
		double dev = stdDev(times);
		myStats.end();
//		myStats.start();
		double runTime = myStats.runTime();
		
		System.out.println("mean: " + mean);
		System.out.println("stdDev: " + dev);
		System.out.println("runTime in miliseconds: " + runTime);
		
	}
	
}
