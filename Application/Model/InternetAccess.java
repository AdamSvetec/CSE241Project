//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//IntenetAccess object that will be intermediary between user interface and database internet_access object
public class InternetAccess{

	private int id;
	private int meid;
	private Date usageTime;
	private int accessBytes;

	//Constructor
	private InternetAccess(int id, int meid, Date usageTime, int accessBytes){
		this.id = id;
		this.meid = meid;
		this.usageTime = usageTime;
		this.accessBytes = accessBytes;
	}

	//Create new Internet access and set id to -1 until inserted
	public static InternetAccess create(int meid, Date usageTime, int accessBytes){
		return new InternetAccess(-1, meid, usageTime, accessBytes);
	}

	//Insert given InternetAccess into the db
	public int insert(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select max(id) as MAX from internet_access");
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
		String query = "insert into internet_access values ( '"+id+"', '"+meid+"', "+DateFormatter.toString(usageTime)+", '"+accessBytes+"')";
		if(!DBConnection.submitQuery(query)){
			this.id = -1;
		}
		return this.id;
	}

	//Query a given InternetAccess from the db given an id
	public static InternetAccess query(int id){
		InternetAccess ia = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from internet_access where id = " + id);
			if(rs.next()){
				ia = new InternetAccess(rs.getInt("id"),rs.getInt("meid"),rs.getDate("usage_time"),rs.getInt("access_bytes"));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return ia;
	}

	//Query for all internet_access' in database
	public static List<InternetAccess> queryAll(){
		List<InternetAccess> iaList = new ArrayList<InternetAccess>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from internet_access");
			while(rs.next()){
				iaList.add(new InternetAccess(rs.getInt("id"),rs.getInt("meid"),rs.getDate("usage_time"),rs.getInt("access_bytes")));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return iaList;
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAll();
		InternetAccess ia;
		int meid;
		Date usageTime;
		int accessBytes;
		for(int id = 0; id < 1000; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			usageTime = DateFormatter.getRandomDate();
			accessBytes = new Random().nextInt(500);
			ia = new InternetAccess(id, meid, usageTime, accessBytes);
			ia.insert();
		}
	}

	//Deletes all instances of internet_access in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from internet_access");
	}

	//Delete specific instance of internet_access from the database
	public void delete(){
		DBConnection.submitQuery("delete from internet_access where id = "+id);
	}

	//Get id
	public int getId(){
		return id;
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get usageTime
	public Date getUsageTime(){
		return usageTime;
	}

	//Get accessBytes
	public int getAccessBytes(){
		return accessBytes;
	}
}