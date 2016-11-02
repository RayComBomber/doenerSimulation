import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerGroup {
	
	private final ReentrantLock  lock = new ReentrantLock ();
	private final Condition condition = lock.newCondition();
	
	private final int groupCount;
	private int cusomersCountReadyToLeave;
	private int groupId;
	private DoenerStore store;
	
	public CustomerGroup(int groupId, int customerCount, DoenerStore store){
		this.groupId = groupId;
		this.groupCount = customerCount;
		this.store = store;
		this.cusomersCountReadyToLeave = 0;
	}
	
	
	public void goHome(Customer customer){
		lock.lock();
		try{
			cusomersCountReadyToLeave++;
			
			
			while(cusomersCountReadyToLeave < groupCount){
				try {
					System.out.println(customer.toString() + " waits for his group.");
					condition.await();
				} catch (InterruptedException e) {}
			}
			condition.signalAll();
			System.out.println(customer.toString() + " leaves the store with his group.");
		}
		finally{
			lock.unlock();
		}
	}

}
