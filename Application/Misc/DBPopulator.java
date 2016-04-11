//Adam Svetec
//CSE 241

import java.util.Scanner;

//DBPopulator will call populate DB methods of all classes in order to create random data
public class DBPopulator{

	//Clear all entities and repopulate them
	public static void main(String [] args){
		validateUser();
		clearDB();
		populateDB();
	}

	//Validate user by continually asking for password until correct one is entered
	private static void validateUser(){
		Scanner scan = new Scanner(System.in);
		while(true){
			System.out.println("Please enter password for ajs217 to access database:");
			String password = scan.next();
			if(DBConnection.validate("ajs217",password)){
				System.out.println("Success: Thank You");
				return;
			}
		}
	}

	//Deletes all instances from database
	private static void clearDB(){
		PhoneNumber.deleteAll();
		System.out.println("deleted phone_number");
		InternetAccess.deleteAll();
		System.out.println("deleted internet_access");
		PhoneCall.deleteAll();
		System.out.println("deleted phone_call");
		TextMessage.deleteAll();
		System.out.println("deleted text_message");
		InventoryItem.deleteAll();
		System.out.println("deleted inventory_item");
		Account.deleteAll();
		System.out.println("deleted account");
		Phone.deleteAll();
		System.out.println("deleted phone");
		Store.deleteAll();
		System.out.println("deleted store");
		Customer.deleteAll();
		System.out.println("deleted customer");
		Plan.deleteAll();
		System.out.println("deleted plan");
	}

	//Populates database with random but realistic entities from each class
	private static void populateDB(){
		Phone.populateDB();
		System.out.println("phone populated");
		PhoneCall.populateDB();
		System.out.println("phone_call populated");
		TextMessage.populateDB();
		System.out.println("text_message populated");
		InternetAccess.populateDB();
		System.out.println("internet_access populated");
		Store.populateDB();
		System.out.println("store populated");
		InventoryItem.populateDB();
		System.out.println("inventory_item populated");
		Customer.populateDB();
		System.out.println("customer populated");
		Plan.populateDB();
		System.out.println("plan populated");
		Account.populateDB();
		System.out.println("account populated");
		PhoneNumber.populateDB();
		System.out.println("phone_number populated");
	}
}