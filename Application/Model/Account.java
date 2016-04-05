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

	//Takes given string and converts it to an accountType
	public static AccountType stringToAccountType(String type){
		if(type.equals(AccountType.Individual.toString())){
			return AccountType.Individual;
		}else if(type.equals(AccountType.Family.toString())){
			return AccountType.Family;
		}else if(type.equals(AccountType.Business.toString())){
			return AccountType.Business;
		}
		return null;
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

	//Create new Account, id is set to 0 until it is inserted
	public static Account create(int customerId, Plan.PlanType planType, AccountType accountType){
		return new Account(0, customerId, planType, accountType);
	}

	//Insert given account into the database
	private int insert(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select max(account_id) as MAX from account");
			if(rs.next()){
				this.accountId = rs.getInt("MAX") + 1;
			}else{
				this.accountId = 1;
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return -1;
		}
		String query = "insert into account values ( '"+accountId+"', '"+customerId+"', '"+planType.toString()+"', '"+accountType.toString()+"' )";
		DBConnection.submitQuery(query);
		return this.accountId;
	}

	//Query a given Account from the db given an id
	public static Account query(int id){
		Account account = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from account where account_id = " + id);
			if(rs.next()){
				account = new Account(rs.getInt("account_id"),rs.getInt("customer_id"),Plan.stringToPlanType(rs.getString("plan_type")),stringToAccountType(rs.getString("account_type")));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return account;
	}

	//Update a given Account with the new values
	public boolean update(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("update account set customer_id = '"+customerId+"', plan_type = '"+planType.toString()+"', account_type = '"+accountType.toString()+"' where account_id = "+accountId);
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return false;
		}
		return true;
	}

	//Query for all accounts in database
	public static List<Account> queryAll(){
		List<Account> accountList = new ArrayList<Account>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from account");
			Plan.PlanType pt; 
			AccountType at;
			while(rs.next()){
				pt = Plan.stringToPlanType(rs.getString("plan_type"));
				at = stringToAccountType(rs.getString("account_type"));
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
		List<Customer> customerList = Customer.queryAll();
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

	//Delete account from db with given account_id
	public void delete(){
		DBConnection.submitQuery("delete from account where account_id = "+accountId);
	}

	//Deletes all instances of account in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from account");
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

	//Set customerId
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	//Set planType
	public void setPlanType(Plan.PlanType planType){
		this.planType = planType;
	}

	//Set accountType
	public void setAccountType(AccountType accountType){
		this.accountType = accountType;
	}
}