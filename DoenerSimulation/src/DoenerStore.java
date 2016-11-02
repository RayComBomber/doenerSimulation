import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DoenerStore {

	private final ReentrantLock  lock = new ReentrantLock ();
	private final Condition condition = lock.newCondition();
	private int freeEmployees;
	
	public DoenerStore(int employees){
		this.freeEmployees = employees;
	}

	/**
	 * Customer queues for food. If an employee is free, he will get food fast. Else, he will be queued.
	 * @param customer
	 */
	public void queueForFood(Customer customer) {
		lock.lock();
		try{
			while (freeEmployees == 0) {
				try {
					System.out.println(customer.toString() + " is queued.");
					condition.await();
				} catch (InterruptedException e) {}
			}
	
			freeEmployees--;
			System.out.println(customer.toString() + " gets food made.");
		}
		finally{
			lock.unlock();
		}
	}
	
	
	/**
	 * Customer is now on his turn and gets his food made.
	 * @param customer 
	 */
	public void getRequestedFood(Customer customer){
		lock.lock();
		try{
			freeEmployees++;
			System.out.println(customer.toString() + " got his food.");
			condition.signal();
		}
		finally{
			lock.unlock();
		}
	}

}
