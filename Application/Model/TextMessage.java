//Adam Svetec
//CSE 241

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.sql.*;

//TextMessage object that will be intermediary between user interface and database text_message object
public class TextMessage{

	private int id;
	private int meid;
	private Date sendTime;
	private int messageSize;
	private String secondaryNumber;

	//Constructor
	private TextMessage(int id, int meid, Date sendTime, int messageSize, String secondaryNumber){
		this.id = id;
		this.meid = meid;
		this.sendTime = sendTime;
		this.messageSize = messageSize;
		this.secondaryNumber = secondaryNumber;
	}

	//Create an instance of TextMessage without inserting it, will set id to -1 until inserted
	public static TextMessage create(int meid, Date sendTime, int messageSize, String secondaryNumber){
		return new TextMessage(-1, meid, sendTime, messageSize, secondaryNumber);
	}

	//Insert given text_message into the database
	public int insert(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select max(id) as MAX from text_message");
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
		String query = "insert into text_message values ( '"+id+"', '"+meid+"', "+DateFormatter.toString(sendTime)+", '"+messageSize+"', '"+secondaryNumber+"' )";
		if(!DBConnection.submitQuery(query)){
			this.id = -1;
		}
		return this.id;
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAll();
		TextMessage textMessage;
		int meid;
		Date sendTime;
		int messageSize;
		String secondaryNumber;
		for(int id = 0; id < 750; id++){
			meid = phoneList.get(new Random().nextInt(phoneList.size())).getMeid();
			sendTime = DateFormatter.getRandomDate();
			messageSize = new Random().nextInt(140);
			secondaryNumber = Integer.toOctalString(new Random().nextInt(600) + 100) +""+(new Random().nextInt(641) + 100)+""+(new Random().nextInt(8999) + 1000);
			textMessage = new TextMessage(id, meid, sendTime, messageSize, secondaryNumber);
			textMessage.insert();
		}
	}

	//Deletes all instances of text_message in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from text_message");
	}

	//Delete given instance of TextMessage from the db
	public void delete(){
		DBConnection.submitQuery("delete from text_message where id = "+id);
	}

	//Get id
	public int getId(){
		return id;
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get sendTime
	public Date getSendTime(){
		return sendTime;
	}

	//Get messageSize
	public int getMessageSize(){
		return messageSize;
	}

	//Get secondaryNumber
	public String getSecondaryNumber(){
		return secondaryNumber;
	}
}