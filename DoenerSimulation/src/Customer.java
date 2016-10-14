import java.util.concurrent.ThreadLocalRandom;

public class Customer extends Thread {

	private static final int TIME_FOR_GETTING_FOOD_MIN = 4000;
	private static final int TIME_FOR_GETTING_FOOD_MAX = 7000;
	
	private int number;
	private int groupNumber;
	private DoenerStore store;
	private CustomerGroup customerGroup;
	
	public Customer(int groupNumber, int number, DoenerStore store, CustomerGroup customerGroup){
		super(groupNumber + "." + number);
		this.number = number;
		this.groupNumber = groupNumber;
		this.store = store;
		this.customerGroup = customerGroup;
	}
	
	
	@Override
	public void run(){
		store.queueForFood(this);
		
		int timeForGettingFood = ThreadLocalRandom.current().nextInt(TIME_FOR_GETTING_FOOD_MIN, TIME_FOR_GETTING_FOOD_MAX + 1);
		try {
			Thread.sleep(timeForGettingFood);
		} catch (InterruptedException e) {}
		
		store.getRequestedFood(this);
		customerGroup.goHome(this);		
	}
	
	
	
	@Override
	public String toString(){
		return "Customer " + getGroupNumber() + "." + getNumber();
	}
	
	public int getNumber() {
		return number;
	}


	public int getGroupNumber() {
		return groupNumber;
	}

	


	
}
