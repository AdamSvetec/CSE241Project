//Adam Svetec
//CSE 241

import java.util.Scanner;

//Methods are used to ensure the data type entered is valid
//Actual domain logic restrictions should be checked by the controller
public class CommandLineView{

	//Get string from user at command line given prompt
	public static String getString(String prompt){
		System.out.print(prompt);
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	//Get int from user at command line given prompt
	public static int getInt(String prompt){
		Scanner scanner = new Scanner(System.in);
		String intString;
		do{
			System.out.print(prompt);
			intString = scanner.next();
			try{
				return Integer.parseInt(intString);
			}catch(NumberFormatException nfe){
				System.out.println("Incorrect format, please enter integer.");
			}
		}while(true);
	}

	//Get double from user at command line given prompt
	public static double getDouble(String prompt){
		Scanner scanner = new Scanner(System.in);
		String doubleString;
		do{
			System.out.print(prompt);
			doubleString = scanner.next();
			try{
				return Double.parseDouble(doubleString);
			}catch(NumberFormatException nfe){
				System.out.println("Incorrect format, please enter decimal.");
			}
		}while(true);
	}
}