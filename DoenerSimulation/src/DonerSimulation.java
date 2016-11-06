import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DonerSimulation {

	private static final int EMPLOYEE_COUNT = 3;
//	private static final int GROUP_COUNT = 20;
	private static final int CUSTOMER_COUNT_MIN = 2;
	private static final int CUSTOMER_COUNT_MAX = 5;
	private static final int TIME_BETWEEN_GROUPS = 5000;
	
	private static ScheduledExecutorService schedulePool =  Executors.newScheduledThreadPool(1);
	private static Executor pool = Executors.newCachedThreadPool();
	
	
	public static void main(String[] args) {
		System.out.println("D�ner Simulation:");
		
		// Init
		DoenerStore store = new DoenerStore(EMPLOYEE_COUNT);		
		
		schedulePool.scheduleAtFixedRate(() -> {
			int groupId = CustomerGroup.getGlobalGroupId();
			
			int customerCount = ThreadLocalRandom.current().nextInt(CUSTOMER_COUNT_MIN, CUSTOMER_COUNT_MAX + 1);
			CustomerGroup group = new CustomerGroup(groupId, customerCount, store);
			System.out.println("Group " + groupId + " with " + customerCount + " customers has arrived.");
			
			// Customer creation
			for(int customerNumber = 0; customerNumber < customerCount; customerNumber++){
				Customer newCostomer = new Customer(groupId, customerNumber, store, group);
				pool.execute(newCostomer);
			}
			
			CustomerGroup.incrementGlobalGroupId();
			
		}, 0, TIME_BETWEEN_GROUPS, TimeUnit.MILLISECONDS);
	
	}

}
