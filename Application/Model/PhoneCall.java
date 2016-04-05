//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.*;

//PhoneCall object that will be intermediary between user interface and database phone_call object
public class PhoneCall{

	private int id;
	private int meid;
	private Date startTime;
	private Date endTime;
	private String secondaryNumber;

	//Constructor
	private PhoneCall(int id, int meid, Date startTime, Date endTime, String secondaryNumber){
		this.id = id;
		this.meid = meid;
		this.startTime = startTime;
		this.endTime = endTime;
		this.secondaryNumber = secondaryNumber;
	}

	//Create instance of PhoneCall without inserting it, set id to 0 until inserted
	public static PhoneCall create(int meid, Date startTime, Date endTime, String secondaryNumber){
		return new PhoneCall(0, meid, startTime, endTime, secondaryNumber);
	}

	//Insert given phone_call into the database
	public int insert(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select max(id) as MAX from phone_call");
			if(rs.next()){
				this.id = rs.getInt("MAX") + 1;
			}else{
				this.id = 1;
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return -1;
		}
		String query = "insert into phone_call values ( '"+id+"', '"+meid+"', "+DateFormatter.toString(startTime)+", "+DateFormatter.toString(endTime)+", '"+secondaryNumber+"' )";
		DBConnection.submitQuery(query);
		return this.id;
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAllPhones();
		PhoneCall phoneCall;
		int meid;
		Date startTime;
		Date endTime;
		String secondaryNumber;
		for(int id = 0; id < 300; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			startTime = new Date(Math.abs(System.currentTimeMillis() - new Random().nextInt(999999999)));
			endTime = new Date(startTime.getTime() + new Random().nextInt(9999999));
			secondaryNumber = Integer.toOctalString(new Random().nextInt(600) + 100) +""+(new Random().nextInt(641) + 100)+""+(new Random().nextInt(8999) + 1000);
			phoneCall = new PhoneCall(id, meid, startTime, endTime, secondaryNumber);
			phoneCall.insert();
		}
	}

	//Deletes all instances of phone_call in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from phone_call");
	}

	//Delete instance of phone_call from the database
	public void delete(){
		DBConnection.submitQuery("delete from phone_call where id = "+id);;
	}

	//Get id
	public int getId(){
		return id;
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get startTime
	public Date getStartTime(){
		return startTime;
	}

	//Get endTime
	public Date getEndTime(){
		return endTime;
	}

	//Get secondaryNumber
	public String getSecondaryNumber(){
		return secondaryNumber;
	}
}