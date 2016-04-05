//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.ResultSet;

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

	//Insert given phone_number into the database
	public boolean insert(){
		String query = "insert into phone_number values ( '"+phoneNumber+"', '"+(isPrimary ? 1 : 0)+"', '"+accountId+"', '"+meid+"' )";
		return DBConnection.submitQuery(query);
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
}