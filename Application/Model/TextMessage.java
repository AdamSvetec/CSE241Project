//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

//TextMessage object that will be intermediary between user interface and database text_message object
public class TextMessage{

	private int id;
	private int meid;
	private Date sendTime;
	private int messageSize;
	//private String primary_number; ?Query for this every time one is retreived?
	private String secondary_number;

	//Constructor
	private TextMessage(int id, int meid, Date sendTime, int messageSize, String secondary_number){
		this.id = id;
		this.meid = meid;
		this.sendTime = sendTime;
		this.messageSize = messageSize;
		this.secondary_number = secondary_number;
	}

	//Insert given text_message into the database
	public boolean insert(){
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");
		String query = "insert into text_message values ( '"+id+"', '"+meid+"', TO_TIMESTAMP ('"+dtf.format(sendTime)+"', 'YYYY-MM-DD HH24:MI:SS.FF'), '"+messageSize+"', '"+secondary_number+"' )";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAllPhones();
		TextMessage textMessage;
		int meid;
		Date sendTime;
		int messageSize;
		String secondary_number;
		for(int id = 0; id < 750; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			sendTime = new Date(Math.abs(System.currentTimeMillis() - new Random().nextInt(999999999)));
			messageSize = new Random().nextInt(140);
			secondary_number = Integer.toOctalString(new Random().nextInt(600) + 100) +""+(new Random().nextInt(641) + 100)+""+(new Random().nextInt(8999) + 1000);
			textMessage = new TextMessage(id, meid, sendTime, messageSize, secondary_number);
			textMessage.insert();
		}
	}

	//Deletes all instances of text_message in the database
	public static void deleteAll(){
		DBConnection.submitQueryBoolean("delete from text_message");
	}
}