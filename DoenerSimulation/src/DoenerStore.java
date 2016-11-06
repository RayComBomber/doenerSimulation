import java.util.concurrent.Semaphore;

public class DoenerStore {

	private final Semaphore employeeSemaphore;
	
	public DoenerStore(int employees){
		this.employeeSemaphore = new Semaphore(employees);
	}

	/**
	 * Customer queues for food. If an employee is free, he will get food fast. Else, he will be queued.
	 * @param customer
	 * @throws InterruptedException 
	 */
	public void queueForFood(Customer customer) throws InterruptedException {
		System.out.println(customer.toString() + " is queued.");
		employeeSemaphore.acquire();
		System.out.println(customer.toString() + " gets food made.");
	}
	
	
	/**
	 * Customer is now on his turn and gets his food made.
	 * @param customer 
	 */
	public void getRequestedFood(Customer customer){
		employeeSemaphore.release();
		System.out.println(customer.toString() + " got his food.");
	}

}
