import java.util.LinkedList;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		computeCCs(5);
			
	}
	
	public static void computeCCs(int n) {
		
		double times[] = new double[51];
		double p = 0;
		
		
		for(int i = 0; i <= 50; i++) {
			Graph g = new Graph(n, p);
			
			p = p + .02;
		}
		
		return;
	}
	

}
