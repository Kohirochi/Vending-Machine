package application;

public class Drink {
	static int drinkTotal= 0;
	String id;
	String name;
	String price;
	Integer numberOfStock;
	String imageFileName;
	
	Drink (String id, String name, String price, String numberOfStock, String imageFileName){
		this.id = id;
		this.name = name;
		this.price = price;
		this.numberOfStock = Integer.parseInt(numberOfStock);
		this.imageFileName = imageFileName;
		drinkTotal += 1;
	}
		
	String getId() {
		System.out.println(id);
		return id;
	}
	
	String getName() {
		System.out.println(name);
		return name;
	}
	
	String getPrice() {
		System.out.println("RM" + price);
		return price;
	}
	
	Integer getNumberOfStock() {
		System.out.println(numberOfStock);
		return numberOfStock;
	}
}
