import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DonerSimulation {

	private static final int EMPLOYEE_COUNT = 3;
	private static final int MAX_GROUP_COUNT = 20;
	private static final int CUSTOMER_COUNT_MIN = 2;
	private static final int CUSTOMER_COUNT_MAX = 5;
	private static final int TIME_BETWEEN_GROUPS = 5000;
	private static final int TIME_BETWEEN_GOLD_GROUPS = 10000;
	
	private static ScheduledExecutorService schedulePool =  Executors.newScheduledThreadPool(2);
	private static ExecutorService pool = Executors.newFixedThreadPool(6);
	
	private static ScheduledFuture<?> futureNormalCustomers;
	private static ScheduledFuture<?> futureGoldCustomers;
	private static List<Future<?>> cutomerFutures = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.println("Döner Simulation:");
		
		// Init
		DoenerStore store = new DoenerStore(EMPLOYEE_COUNT);		

		// normal customers
		futureNormalCustomers = schedulePool.scheduleAtFixedRate(() -> {
			createCustomers(store, false);
			}, 0, TIME_BETWEEN_GROUPS, TimeUnit.MILLISECONDS);
		
		// gold card customers
		futureGoldCustomers = schedulePool.scheduleAtFixedRate(() -> {
			createCustomers(store, true);
			}, 2000, TIME_BETWEEN_GOLD_GROUPS, TimeUnit.MILLISECONDS);
			
	}
	
	private static synchronized void createCustomers(DoenerStore store, boolean customerHasGoldCard) {
			int groupId = CustomerGroup.getGlobalGroupId();
			
			int customerCount = ThreadLocalRandom.current().nextInt(CUSTOMER_COUNT_MIN, CUSTOMER_COUNT_MAX + 1);
			CustomerGroup group = new CustomerGroup(groupId, customerCount, store, customerHasGoldCard);
			
			// Customer creation
			for(int customerNumber = 0; customerNumber < customerCount; customerNumber++){
				Customer newCostomer = new Customer(groupId, customerNumber, store, group, customerHasGoldCard);
				cutomerFutures.add(pool.submit(newCostomer));
			}
			
			CustomerGroup.incrementGlobalGroupId();
			if(groupId == MAX_GROUP_COUNT){
				futureNormalCustomers.cancel(false);
				futureGoldCustomers.cancel(false);
				waitForTermination();
			}
	}
	
	private static void waitForTermination() {
		// Wait until all open tasks are done
		System.out.println("Waiting for last customer ...");
		for (Future<?> f : cutomerFutures) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		// end ExecutorServices 
		pool.shutdown();
		schedulePool.shutdown();
		
		try {
			pool.awaitTermination(15, TimeUnit.SECONDS);
			schedulePool.awaitTermination(15, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Doener-Store closed.");
	}

}
