
public class DoenerStore {

	private int freeEmployees;
	
	public DoenerStore(int employees){
		this.freeEmployees = employees;
	}

	/**
	 * Customer queues for food. If an employee is free, he will get food fast. Else, he will be queued.
	 * @param customer
	 */
	public synchronized void queueForFood(Customer customer) {

		while (freeEmployees == 0) {
			try {
				System.out.println(customer.toString() + " is queued.");
				wait();
			} catch (InterruptedException e) {}
		}

		freeEmployees--;
		System.out.println(customer.toString() + " gets food made.");
	}
	
	
	/**
	 * Customer is now on his turn and gets his food made.
	 * @param customer 
	 */
	public synchronized void getRequestedFood(Customer customer){
		freeEmployees++;
		System.out.println(customer.toString() + " got his food.");
		notify();
	}

}
