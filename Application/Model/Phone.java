//Adam Svetec
//CSE 241

import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//Phone object that will be intermediary between user interface and database phone object
public class Phone{

	private int meid;
	private String manufacturer;
	private String phoneModel;

	//Constructor
	private Phone(int meid, String manufacturer, String phoneModel){
		this.meid = meid;
		this.manufacturer = manufacturer;
		this.phoneModel = phoneModel;
	}

	//Create instance of Phone
	public static Phone create(int meid, String manufacturer, String phoneModel){
		return new Phone(meid, manufacturer, phoneModel);
	}

	//Insert given phone into the database
	public boolean insert(){
		//String query = "insert into phone values ( '"+meid+"', '"+manufacturer+"', '"+phoneModel+"' )";
		String query = "insert into phone values ( ?, ?, ? )";
		List<String> params = Arrays.asList(""+meid, manufacturer, phoneModel);
		return DBConnection.submitPreparedQuery(query, params);
	}

	//Query phone given an meid
	public static Phone query(int meid){
		Phone phone = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from phone where meid = " + meid);
			if(rs.next()){
				phone = new Phone(rs.getInt("meid"),rs.getString("manufacturer"),rs.getString("phone_model"));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return phone;
	}

	//Query for all phones in database
	public static List<Phone> queryAll(){
		List<Phone> phoneList = new ArrayList<Phone>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from phone");
			while(rs.next()){
				phoneList.add(new Phone(rs.getInt("meid"),rs.getString("manufacturer"),rs.getString("phone_model")));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return phoneList;
	}

	//Populates database with random data
	public static void populateDB(){
		List<String> manufacturerNames = Arrays.asList("Apple", "Blackberry", "LG");
		List<String> appleModelNames = Arrays.asList("Iphone 5", "Iphone 6");
		List<String> blackberryModelNames = Arrays.asList("Passport", "Leap");
		List<String> lgModelNames = Arrays.asList("Leon", "K7", "Vista");
		Phone phone;
		String manufacturer;
		for(int meidCounter = 1; meidCounter < 9999; meidCounter += new Random().nextInt(100)+1){
			manufacturer = manufacturerNames.get(new Random().nextInt(manufacturerNames.size()));
			if(manufacturer.equals("Apple")){
				phone = new Phone(meidCounter, manufacturer, appleModelNames.get(new Random().nextInt(appleModelNames.size())));
				phone.insert();
			}else if(manufacturer.equals("Blackberry")){
				phone = new Phone(meidCounter, manufacturer, blackberryModelNames.get(new Random().nextInt(blackberryModelNames.size())));
				phone.insert();
			}else if(manufacturer.equals("LG")){
				phone = new Phone(meidCounter, manufacturer, lgModelNames.get(new Random().nextInt(lgModelNames.size())));
				phone.insert();
			}
		}
	}

	//Deletes all instances of phone in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from phone");
	}

	//Deletes the instance of the phone in the db
	public void delete(){
		DBConnection.submitQuery("delete from phone where meid = "+meid);
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get manufacturer
	public String getManufacturer(){
		return manufacturer;
	}

	//Get model
	public String getModel(){
		return phoneModel;
	}

	//Get string description for this phone
	public String getDescription(){
		return "MEID: "+meid+", Manufacturer: "+manufacturer+", Model: "+phoneModel;
	}
}