//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//PhoneNumber object that will be intermediary between user interface and database phone_number object
public class PhoneNumber{

	private String phoneNumber;
	private boolean isPrimary;
	private int accountId;
	private int meid;

	//Constructor
	private PhoneNumber(String phoneNumber, boolean isPrimary, int accountId, int meid){
		this.phoneNumber = phoneNumber;
		this.isPrimary = isPrimary;
		this.accountId = accountId;
		this.meid = meid;
	}

	//Creates a PhoneNumber but does not insert in db
	public static PhoneNumber create(String phoneNumber, boolean isPrimary, int accountId, int meid){
		return new PhoneNumber(phoneNumber, isPrimary, accountId, meid);
	}

	//Insert given phone_number into the database
	public boolean insert(){
		String query = "insert into phone_number values ( '"+phoneNumber+"', '"+(isPrimary ? 1 : 0)+"', '"+accountId+"', '"+meid+"' )";
		return DBConnection.submitQuery(query);
	}

	//Query a given phone_number from db given the phoneNumber
	public static PhoneNumber query(String phoneNumber){
		PhoneNumber number = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from phone_number where phone_num = '" + phoneNumber +"'");
			if(rs.next()){
				number = new PhoneNumber(rs.getString("phone_num"),(rs.getInt("is_primary") != 0),rs.getInt("account_id"),rs.getInt("meid"));;
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return number;
	}

	//Update a given PhoneNumber with the new values
	public boolean update(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("update phone_number set is_primary = '"+(isPrimary ? 1 : 0)+"', account_id = '"+accountId+"', meid = '"+meid+"' where phone_num = "+phoneNumber);
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return false;
		}
		return true;
	} 

	//Query all instances of phone_number from the database
	public static List<PhoneNumber> queryAll(){
		List<PhoneNumber> phoneNumList = new ArrayList<PhoneNumber>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from phone_number");
			while(rs.next()){
				phoneNumList.add(new PhoneNumber(rs.getString("phone_num"),(rs.getInt("is_primary") != 0),rs.getInt("account_id"),rs.getInt("meid")));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return phoneNumList;
	}

	//Populates database with random data
	//Needs to be edited so there is only one primary per account
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAllPhones();
		List<Account> accountList = Account.queryAllAccounts();
		PhoneNumber phoneNum;
		String currentPhoneNumber;
		boolean isPrimary;
		int accountId;
		Phone currentPhone;
		for(int id = 0; id < phoneList.size(); id++){
			currentPhoneNumber = Integer.toOctalString(new Random().nextInt(600) + 100) +""+(new Random().nextInt(641) + 100)+""+(new Random().nextInt(8999) + 1000);
			isPrimary = new Random().nextBoolean();
			accountId = accountList.get(new Random().nextInt(accountList.size())).getAccountId();
			currentPhone = phoneList.get(new Random().nextInt(phoneList.size()));
			phoneList.remove(currentPhone);
			phoneNum = new PhoneNumber(currentPhoneNumber, isPrimary, accountId, currentPhone.getMeid());
			phoneNum.insert();
		}
	}

	//Deletes all instances of phone_number in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from phone_number");
	}

	//Deletes given instance of phone_number from the database
	public void delete(){
		DBConnection.submitQuery("delete from phone_number where phone_num = '"+phoneNumber+"'");
	}

	//Get phoneNumber
	public String getPhoneNumer(){
		return phoneNumber;
	}

	//Get isPrimary
	public boolean getIsPrimary(){
		return true;
	}

	//Get accountId
	public int getAccountId(){
		return accountId;
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Set isPrimary
	public void setIsPrimary(boolean isPrimary){
		this.isPrimary = isPrimary;
	}

	//Set accountId
	public void setAccountId(int accountId){
		this.accountId = accountId;
	}

	//Set meid
	public void setMeid(int meid){
		this.meid = meid;
	}
}