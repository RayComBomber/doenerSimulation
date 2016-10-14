
public class CustomerGroup {
	
	private final int groupCount;
	private int cusomersCountReadyToLeave;
	private int groupId;
	private DoenerStore store;
	
	public CustomerGroup(int groupId, int customerCount, DoenerStore store){
		this.groupId = groupId;
		this.groupCount = customerCount;
		this.store = store;
		this.cusomersCountReadyToLeave = 0;
		init();
	}
	
	private void init(){
		for(int customerNumber = 0; customerNumber < groupCount; customerNumber++){
			Customer newCostomer = new Customer(groupId, customerNumber, store, this);
			newCostomer.start();
		}
	}
	
	
	public synchronized void goHome(Customer customer){
		cusomersCountReadyToLeave++;
		while(cusomersCountReadyToLeave < groupCount){
			try {
				System.out.println(customer.toString() + " waits for his group.");
				wait();
			} catch (InterruptedException e) {}
		}
		notifyAll();
		System.out.println(customer.toString() + " leaves the store with his group.");
	}

}
