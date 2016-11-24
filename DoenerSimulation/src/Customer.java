
public class Customer implements Runnable, Comparable<Customer> {
	
	private int number;
	private int groupNumber;
	private DoenerStore store;
	private CustomerGroup customerGroup;
	private boolean hasGoldCard;
	
	
	public Customer(int groupNumber, int number, DoenerStore store, CustomerGroup customerGroup, boolean hasGoldCard){
		this.number = number;
		this.groupNumber = groupNumber;
		this.store = store;
		this.customerGroup = customerGroup;
		this.hasGoldCard = hasGoldCard;
		
	}
	
	@Override
	public void run(){
		store.getFood(this);
		customerGroup.goHome(this);		
	}
	
	@Override
	public int compareTo(Customer other) {
		if(this.hasGoldCard && !other.hasGoldCard){
			return -1;
		}else if(!this.hasGoldCard && other.hasGoldCard){
			return 1;
		}else{ //if(this.hasGoldCard && other.hasGoldCard || !this.hasGoldCard && !other.hasGoldCard ){
			return 0;
		}
	}

	@Override
	public String toString(){
		String prefix = this.hasGoldCard ? "GOLD-Customer " : "Customer ";
		return prefix + getGroupNumber() + "." + getNumber();
	}
	
	public int getNumber() {
		return number;
	}


	public int getGroupNumber() {
		return groupNumber;
	}

	
}
