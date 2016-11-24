import java.util.concurrent.ThreadLocalRandom;

public class Employee {
	
	private static final int TIME_FOR_GETTING_FOOD_MIN = 4000;
	private static final int TIME_FOR_GETTING_FOOD_MAX = 7000;
	
	private final int id;
	
	public Employee(int id){
		this.id = id;
	}
	
	public void makeFood(Customer customer){
		
		System.out.println(customer.toString() + " gets food made from Employee " + this.id + ".");
		int timeForGettingFood = ThreadLocalRandom.current().nextInt(TIME_FOR_GETTING_FOOD_MIN, TIME_FOR_GETTING_FOOD_MAX + 1);
		try {
			Thread.sleep(timeForGettingFood);
		} catch (InterruptedException e) {}
		
		System.out.println(customer.toString() + " got his food.");
	}
}
