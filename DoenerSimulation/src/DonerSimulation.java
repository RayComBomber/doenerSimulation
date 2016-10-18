import java.util.concurrent.ThreadLocalRandom;

public class DonerSimulation {

	private static final int EMPLOYEE_COUNT = 3;
	private static final int GROUP_COUNT = 20;
	private static final int CUSTOMER_COUNT_MIN = 2;
	private static final int CUSTOMER_COUNT_MAX = 5;
	private static final int TIME_BETWEEN_GROUPS = 5000;
	
	
	public static void main(String[] args) {
		System.out.println("Döner Simulation:");
		
		// Init
		DoenerStore store = new DoenerStore(EMPLOYEE_COUNT);
		
		// Spawn Groups
		for(int groupId = 0; groupId < GROUP_COUNT; groupId++){
			
			int customerCount = ThreadLocalRandom.current().nextInt(CUSTOMER_COUNT_MIN, CUSTOMER_COUNT_MAX + 1);
			System.out.println("Group " + groupId + " with " + customerCount + " customers has arrived.");
			new CustomerGroup(groupId, customerCount, store);
						
			try {
				Thread.sleep(TIME_BETWEEN_GROUPS);
			} catch (InterruptedException e) {}
		}
				
	}

}
