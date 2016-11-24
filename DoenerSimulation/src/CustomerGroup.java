import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerGroup {
	
	private CountDownLatch latch;
	private AtomicBoolean isGroupComplete;
	private static int globalGroupId = 0;
	private final boolean isGoldGroup;
	
	
	
	public CustomerGroup(int groupId, int customerCount, DoenerStore store, boolean isGoldGroup){
//		this.groupId = groupId;
		this.latch = new CountDownLatch(customerCount);
		this.isGroupComplete = new AtomicBoolean(false);
		this.isGoldGroup = isGoldGroup;

		String groupPrefix = getGroupPrefix(isGoldGroup);
		System.out.println(groupPrefix + groupId + " with " + customerCount + " customers has arrived.");
	}

	
	
	public void goHome(Customer customer){
		System.out.println(customer.toString() + " waits for his group.");
		this.latch.countDown();
		try {
			this.latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Print this message just one time
		if(this.isGroupComplete.getAndSet(true) == false){
			String groupPrefix = getGroupPrefix(isGoldGroup);
			System.out.println(groupPrefix + customer.getGroupNumber() + " leaves the store.");
		}
	}
	
	
	public static synchronized int getGlobalGroupId() {
		return globalGroupId;
	}


	public static synchronized void incrementGlobalGroupId() {
		CustomerGroup.globalGroupId += 1;
	}
	
	private String getGroupPrefix(boolean isGoldGroup) {
		return isGoldGroup ? "GOLD-Group " : "Group ";
	}


}
