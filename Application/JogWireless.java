//Adam Svetec
//CSE 241

import java.util.Scanner;

//Main method of application
//Validates user and opens interfaces for use
public class JogWireless{
	public static void main(String [] args){
		createClosingHook();
		validateUser();
	}

	//Validate user by continually asking for password until correct one is entered
	public static void validateUser(){
		Scanner scan = new Scanner(System.in);
		while(true){
			System.out.println("Please enter password for ajs217 to access database:");
			String password = scan.next();
			if(DBConnection.validate(password)){
				DBConnection.setPassword(password);
				System.out.println("Success: Thank You");
				return;
			}
		}
	}

	//Create a hook so that when database closes it will close connection to database
	private static void createClosingHook(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    		public void run() {
    			DBConnection.closeConnection();
    		}
		}));
	}
}