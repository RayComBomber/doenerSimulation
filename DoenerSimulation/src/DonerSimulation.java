import java.util.concurrent.ThreadLocalRandom;

public class DonerSimulation {

	private static final int EMPLOYEE_COUNT = 3;
	private static final int CUSTOMER_COUNT_MIN = 3;
	private static final int CUSTOMER_COUNT_MAX = 6;
	private static final int TIME_BETWEEN_GROUPS_MIN = 3000;
	private static final int TIME_BETWEEN_GROUPS_MAX = 6000;
	
	
	public static void main(String[] args) {
		System.out.println("Döner Simulation:");
		
		// One time init
		DoenerStore store = new DoenerStore(EMPLOYEE_COUNT);
		
		
		// Infinite loop
		for(int groupId = 0; true; groupId++){
			
			int customerCount = ThreadLocalRandom.current().nextInt(CUSTOMER_COUNT_MIN, CUSTOMER_COUNT_MAX + 1);
			new CustomerGroup(groupId, customerCount, store);
			
			
			int timeUntilNextGroupArrives = ThreadLocalRandom.current().nextInt(TIME_BETWEEN_GROUPS_MIN, TIME_BETWEEN_GROUPS_MAX + 1);
			try {
				Thread.sleep(timeUntilNextGroupArrives);
			} catch (InterruptedException e) {}
		}
				
	}

}
