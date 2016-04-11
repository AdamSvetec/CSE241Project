//Adam Svetec
//CSE 241

import java.util.Scanner;

//Main method of application
//Validates user and opens interfaces for use
public class JogWireless{

	//Main application
	public static void main(String [] args){
		createClosingHook();
		new LoginController();
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