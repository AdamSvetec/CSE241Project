//Adam Svetec
//CSE 241

public class InteractiveController{

	//Constructor
	public InteractiveController(){
		getActionToBePerformed();
	}

	//Gets choice of action from user
	private void getActionToBePerformed(){
		String prompt = "1 : Add New Customer\n";
		prompt += "2 : Add New Account\n";
		prompt += "3 : Add New Phone\n";
		prompt += "4 : Add New Phone Number\n";
		int choice = CommandLineView.getInt(prompt);
		switch (choice){
			case 1: createCustomer();
				break;
			case 2: createAccount();
				break;
			case 3: addPhone();
				break;
			case 4: addPhoneNumber();
				break;
			default: System.out.println("Choice not valid: Please try again");
				getActionToBePerformed();
				break;
		}
	}

	//Create a new Customer
	private void createCustomer(){

	}

	//Create a new Account
	private void createAccount(){

	}

	//Create a new Phone
	private void addPhone(){

	}

	//Add new phone number
	private void addPhoneNumber(){
		
	}
}