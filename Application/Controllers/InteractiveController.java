//Adam Svetec
//CSE 241

import java.util.List;

public class InteractiveController{

	//Constructor
	public InteractiveController(){
		getActionToBePerformed();
	}

	//Gets choice of action from user
	private void getActionToBePerformed(){
		String prompt = "Please enter number for the action you would like to perform\n";
		prompt += "1 : Add New Customer\n";
		prompt += "2 : Add New Account\n";
		prompt += "3 : Add New Phone\n";
		prompt += "4 : Add New Phone Number\n";
		prompt += "5 : Exit\n";
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
			case 5: new InterfaceSelectController();
				return;
			default: System.out.println("Choice not valid: Please try again");
				getActionToBePerformed();
				break;
		}
	}

	//Create a new Customer
	private void createCustomer(){
		System.out.println("Adding new Customer:");
		String name = CommandLineView.getString("Please enter Customer's name: ");
		String address = CommandLineView.getString("Please enter Customer's address: ");
		System.out.println("Summary:\nCustomer name: "+name+"\nCustomer address: "+address);
		int choice = CommandLineView.getInt("1 : Save Customer\n2 : Cancel\n");
		if(choice == 1){
			Customer customer = Customer.create(name, address);
			boolean success = customer.insert();
			if(success){
				System.out.println("Customer creation successful");
				getActionToBePerformed();
			}else{
				System.out.println("Failed to save customer.\n\tPlease check length of name and address");
				createCustomer();
			}
		}else if(choice == 2){
			System.out.println("Cancelling.");
			getActionToBePerformed();
		}else{
			System.out.println("Choice not recognized, please try again.");
			createCustomer();
		}
	}

	//Create a new Account
	private void createAccount(){
		System.out.println("Adding new Account:");
		System.out.println("Please enter Customer's id");
		int customerId = selectCustomerId();
		Plan.PlanType pt = CommandLineView.getPlanType("Please enter the plan type for this account");
		Account.AccountType at = CommandLineView.getAccountType("Please enter account type for this account");
		System.out.println("Summary:\nCustomer ID: "+customerId+"\nPlan Type: "+pt+"\nAccount Type: "+at);
		int choice = CommandLineView.getInt("1 : Save Account\n2 : Cancel\n");
		if(choice == 1){
			Account account = Account.create(customerId, pt, at);
			boolean success = account.insert();
			if(success){
				System.out.println("Account creation successful");
				getActionToBePerformed();
			}else{
				System.out.println("Failed to save account.\n\tPlease check the customer id is valid");
				createAccount();
			}
		}else if(choice == 2){
			System.out.println("Cancelling.");
			getActionToBePerformed();
		}else{
			System.out.println("Choice not recognized, please try again.");
			createAccount();
		}
	}

	//Create a new Phone
	private void addPhone(){
		System.out.println("Adding new Phone:");
		int meid = CommandLineView.getInt("Please enter the phone's meid: ");
		String manufacturer = CommandLineView.getString("Please enter the phone's manufacturer: ");
		String phoneModel = CommandLineView.getString("Please enter the phone's model: ");
		System.out.println("Summary:\nMEID: "+meid+"\nPhone Manufacturer: "+manufacturer+"\nPhone Model: "+phoneModel);
		int choice = CommandLineView.getInt("1 : Save Phone\n2 : Cancel\n");
		if(choice == 1){
			Phone phone = Phone.create(meid, manufacturer, phoneModel);
			boolean successful = phone.insert();
			if(successful){
				System.out.println("Phone addition successful");
				getActionToBePerformed();
			}else{
				System.out.println("Failed to add phone.\n\tPlease check the meid is valid and lengths of manufacturer and model");
				addPhone();
			}
		}else if(choice == 2){
			System.out.println("Cancelling.");
			getActionToBePerformed();
		}else{
			System.out.println("Choice not recognized, please try again.");
			addPhone();
		}
	}

	//Add new phone number
	private void addPhoneNumber(){
		System.out.println("Adding new Phone:");
		String number = getPhoneNumber();
		boolean isPrimary = CommandLineView.getBoolean("Please enter if the number should be the primary account number: ");
		int accountId = selectAccountId();
		int meid = selectMeid();
		System.out.println("Summary:\nPhone Number: "+number+"\nIs Primary: "+isPrimary+"\nAccount ID: "+accountId+"\nMEID: "+meid);
		int choice = CommandLineView.getInt("1 : Save Phone Number\n2 : Cancel\n");
		if(choice == 1){
			PhoneNumber pn = PhoneNumber.create(number, isPrimary, accountId, meid);
			boolean successful = pn.insert();
			if(successful){
				System.out.println("Phone Number addition successful");
				getActionToBePerformed();
			}else{
				System.out.println("Failed to add phone number.\n\tPlease check the meid and account ID is valid");
				addPhoneNumber();
			}
		}else if(choice == 2){
			System.out.println("Cancelling.");
			getActionToBePerformed();
		}else{
			System.out.println("Choice not recognized, please try again.");
			addPhoneNumber();
		}
	}

	//Get account id from user
	private int selectAccountId(){
		int selection = CommandLineView.getInt("1 : Enter Account Id\n2 : See list of Accounts\n");
		if(selection == 1){
			return CommandLineView.getInt("Please enter Account id: ");
		}else if(selection == 2){
			List<Account> accounts = Account.queryAll();
			for(Account account : accounts){
				System.out.println(account.getDescription());
			}
			return CommandLineView.getInt("Please enter Account id: ");
		}else{
			System.out.println("Choice not recognized, please try again");
		}
		return selectCustomerId();
	}

	//Get customer id from user
	private int selectCustomerId(){
		int selection = CommandLineView.getInt("1 : Enter Customer Id\n2 : See list of Customers\n");
		if(selection == 1){
			return CommandLineView.getInt("Please enter Customer id: ");
		}else if(selection == 2){
			List<Customer> customers = Customer.queryAll();
			for(Customer customer : customers){
				System.out.println(customer.getDescription());
			}
			return CommandLineView.getInt("Please enter Customer id: ");
		}else{
			System.out.println("Choice not recognized, please try again");
		}
		return selectCustomerId();
	}

	//Get meid from unused phones
	private int selectMeid(){
		int selection = CommandLineView.getInt("1 : Enter MEID\n2 : See list of unused MEIDS\n");
		if(selection == 1){
			return CommandLineView.getInt("Please enter MEID: ");
		}else if(selection == 2){
			List<PhoneNumber> phoneNumbers = PhoneNumber.queryAll();
			List<Phone> phones = Phone.queryAll();
			for(Phone phone : phones){
				boolean found = false;
				for(PhoneNumber phoneNumber : phoneNumbers){
					if(phoneNumber.getMeid() == phone.getMeid()){
						found = true;
					}
				}
				if(found == false){
					System.out.println(phone.getDescription());
				}
			}
			return CommandLineView.getInt("Please enter MEID: ");
		}else{
			System.out.println("Choice not recognized, please try again");
		}
		return selectCustomerId();
	}

	//Get phone number from user
	private String getPhoneNumber(){
		String number;
		while(true){
			number = CommandLineView.getString("Please enter phone number in form XXXXXXXXXX: ");
			if(number.length() == 10 && number.matches("^[0-9]+$")){
				return number;
			}
			System.out.println("Format not valid, please enter again");
		}
	}
}