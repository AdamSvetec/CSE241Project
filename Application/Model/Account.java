//Adam Svetec
//CSE 241

import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//Account object that will be intermediary between user interface and database account object
public class Account{

	//Enum to hold types of accounts
	public enum AccountType{
		Individual,
		Family,
		Business
	}

	private int accountId;
	private int customerId;
	private Plan.PlanType planType;
	private AccountType accountType;

	//Constructor
	private Account(int accountId, int customerId, Plan.PlanType planType, AccountType accountType){
		this.accountId = accountId;
		this.customerId = customerId;
		this.planType = planType;
		this.accountType = accountType;
	}

	//Insert given account into the database
	private boolean insert(){
		String query = "insert into account values ( '"+accountId+"', '"+customerId+"', '"+planType.toString()+"', '"+accountType.toString()+"' )";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		List<Customer> customerList = Customer.queryAllCustomers();
		List<Plan.PlanType> planTypeList = Arrays.asList(Plan.PlanType.PayPerUse, Plan.PlanType.Unlimited);
		List<AccountType> accountTypeList = Arrays.asList(AccountType.Individual, AccountType.Family, AccountType.Business);
		Account account;
		int customerId;
		Plan.PlanType planType;
		AccountType accountType;
		for(int accountId = 0; accountId < customerList.size(); accountId++){
			customerId = customerList.get(new Random().nextInt(customerList.size())).getCustomerId();
			planType = planTypeList.get(new Random().nextInt(planTypeList.size()));
			accountType = accountTypeList.get(new Random().nextInt(accountTypeList.size()));
			account = new Account(accountId, customerId, planType, accountType);
			account.insert();
		}
	}

	//Deletes all instances of account in the database
	public static void deleteAll(){
		DBConnection.submitQueryBoolean("delete from account");
	}
}