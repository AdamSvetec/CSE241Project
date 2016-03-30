//Adam Svetec
//CSE 241

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//Store object that will be intermediary between user interface and database store object
public class Store{

	private String address;
	private boolean isOnline;
	private int stockLimit;

	//Constructor
	private Store(String address, boolean isOnline, int stockLimit){
		this.address = address;
		this.isOnline = isOnline;
		this.stockLimit = stockLimit;
	}

	//Get address
	public String getAddress(){
		return address;
	}

	//Get isOnline
	public boolean getIsOnline(){
		return isOnline;
	}

	//Get stockLimit
	public int getStockLimit(){
		return stockLimit;
	}

	//Query for all stores in database
	public static List<Store> queryAllStores(){
		List<Store> storeList = new ArrayList<Store>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from store");
			while(rs.next()){
				storeList.add(new Store(rs.getString("address"),(rs.getInt("is_online") != 0),rs.getInt("stock_limit")));
			}
			rs.close();
			s.close();
			conn.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return storeList;
	}

	//Insert given Store into the database
	public boolean insert(){
		String query = "insert into store values ( '"+address+"', '"+(isOnline ? 1 : 0)+"', '"+stockLimit+"')";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		new Store("Online Store", true, 99999999).insert();
		new Store("627 Oxford Street", false, 100).insert();
		new Store("234 North Street", false, 124).insert();
		new Store("376 Mulberry Lane", false, 158).insert();
		new Store("22 East Second Street", false, 200).insert();
		new Store("518 North Kingston Drive", false, 105).insert();
	}

	//Deletes all instances of store in the database
	public static void deleteAll(){
		DBConnection.submitQueryBoolean("delete from store");
	}
}