//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.ResultSet;

//PhoneCall object that will be intermediary between user interface and database phone_call object
public class PhoneCall{

	private int id;
	private int meid;
	private Date startTime; //datetime?
	private Date endTime; //datetime?
	//private String primary_number; ?Query for this every time one is retreived?
	private String secondary_number;

	//Constructor
	private PhoneCall(int id, int meid, Date startTime, Date endTime, String secondary_number){
		this.id = id;
		this.meid = meid;
		this.startTime = startTime;
		this.endTime = endTime;
		this.secondary_number = secondary_number;
	}

	//Insert given phone_call into the database
	public boolean insert(){
		String query = "insert into phone_call values ( '"+id+"', '"+meid+"', '"+new java.sql.Timestamp(startTime.getTime())+"', '"+new java.sql.Timestamp(endTime.getTime())+"', '"+secondary_number+"' )";
		System.out.println(query);
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		DBConnection.submitQueryBoolean("delete from phone_call");
		List<Phone> phoneList = Phone.queryAllPhones();
		PhoneCall phoneCall;
		int meid;
		Date startTime;
		Date endTime;
		String secondary_number;
		for(int id = 0; id < 15; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			startTime = new Date(Math.abs(System.currentTimeMillis() - new Random().nextInt(999999999)));
			endTime = new Date(startTime.getTime() + new Random().nextInt(9999999));
			secondary_number = Integer.toOctalString(new Random().nextInt(600) + 100) +""+(new Random().nextInt(641) + 100)+""+(new Random().nextInt(8999) + 1000);
			phoneCall = new PhoneCall(id, meid, startTime, endTime, secondary_number);
			phoneCall.insert();
		}
	}
}