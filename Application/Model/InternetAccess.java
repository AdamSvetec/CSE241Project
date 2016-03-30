//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

//IntenetAccess object that will be intermediary between user interface and database internet_access object
public class InternetAccess{

	private int id;
	private int meid;
	private Date usageTime;
	private int accessBytes;
	//private String primary_number; ?Query for this every time one is retreived?

	//Constructor
	private InternetAccess(int id, int meid, Date usageTime, int accessBytes){
		this.id = id;
		this.meid = meid;
		this.usageTime = usageTime;
		this.accessBytes = accessBytes;
	}

	//Insert given InternetAccess into the database
	public boolean insert(){
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");
		String query = "insert into internet_access values ( '"+id+"', '"+meid+"', TO_TIMESTAMP ('"+dtf.format(usageTime)+"', 'YYYY-MM-DD HH24:MI:SS.FF'), '"+accessBytes+"')";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAllPhones();
		InternetAccess ia;
		int meid;
		Date usageTime;
		int accessBytes;
		for(int id = 0; id < 15; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			usageTime = new Date(Math.abs(System.currentTimeMillis() - new Random().nextInt(999999999)));
			accessBytes = new Random().nextInt(500);
			ia = new InternetAccess(id, meid, usageTime, accessBytes);
			ia.insert();
		}
	}

	//Deletes all instances of text_message in the database
	public static void deleteAll(){
		DBConnection.submitQueryBoolean("delete from internet_access");
	}
}