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

	//Get accountId
	public int getAccountId(){
		return accountId;
	}

	//Get customerId
	public int getCustomerId(){
		return customerId;
	}

	//Get planType
	public Plan.PlanType getPlanType(){
		return planType;
	}

	//Get accountType
	public AccountType getAccountType(){
		return accountType;
	}

	//Insert given account into the database
	private boolean insert(){
		String query = "insert into account values ( '"+accountId+"', '"+customerId+"', '"+planType.toString()+"', '"+accountType.toString()+"' )";
		return DBConnection.submitQueryBoolean(query);
	}

	//Query for all accounts in database
	public static List<Account> queryAllAccounts(){
		List<Account> accountList = new ArrayList<Account>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from account");
			Plan.PlanType pt = Plan.PlanType.PayPerUse;
			AccountType at = Account.AccountType.Individual;
			while(rs.next()){
				if(rs.getString("plan_type").equals(Plan.PlanType.PayPerUse.toString())){
					pt = Plan.PlanType.PayPerUse;
				}else if(rs.getString("plan_type").equals(Plan.PlanType.Unlimited.toString())){
					pt = Plan.PlanType.Unlimited;
				}
				if(rs.getString("account_type").equals(AccountType.Individual.toString())){
					at = AccountType.Individual;
				}else if(rs.getString("account_type").equals(AccountType.Family.toString())){
					at = AccountType.Family;
				}else if(rs.getString("account_type").equals(AccountType.Business.toString())){
					at = AccountType.Business;
				}
				accountList.add(new Account(rs.getInt("account_id"),rs.getInt("customer_id"),pt,at));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return accountList;
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