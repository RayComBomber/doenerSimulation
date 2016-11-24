import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


public class DoenerStore {

	private final BlockingQueue<Employee> employeeQueue;
	private final BlockingQueue<Customer> customerQueue;
	
	public DoenerStore(int employees){
		this.employeeQueue = new ArrayBlockingQueue<Employee>(employees);
		this.customerQueue = new PriorityBlockingQueue<Customer>();
		
		
		
		for (int i = 0; i < employees; i++) {
			employeeQueue.add(new Employee(i));
		}
		
	}

	public void getFood(Customer newCustomer) {
		
		queueCustomer(newCustomer);
		
		try {
			Employee employee = employeeQueue.take();
			Customer nextCustomer = customerQueue.take();
			
			employee.makeFood(nextCustomer);
			employeeQueue.add(employee);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void queueCustomer(Customer customer) {
		System.out.println(customer.toString() + " is queued.");
		customerQueue.add(customer);
	}

}
