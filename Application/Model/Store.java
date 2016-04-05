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

	//Create new instance of Store without inserting it into db
	public static Store create(String address, int stockLimit){
		return new Store(address, false, stockLimit);
	}

	//Insert given Store into the database
	public boolean insert(){
		String query = "insert into store values ( '"+address+"', '"+(isOnline ? 1 : 0)+"', '"+stockLimit+"')";
		return DBConnection.submitQuery(query);
	}

	//Query Store from db given address
	public static Store query(String address){
		Store store = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from store where address = '" + address + "'");
			if(rs.next()){
				store = new Store(rs.getString("address"),(rs.getInt("is_online")!=0),rs.getInt("stock_limit"));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return store;
	}

	//Update given Store's stock limit
	public boolean update(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("update store set stock_limit = '"+stockLimit+"' where address = '"+address+"'");
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return false;
		}
		return true;
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
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return storeList;
	}

	//Populates database with random data
	public static void populateDB(){
		new Store("Online Store", true, 99999999).insert();
		new Store("834 Lafayette Avenue, Kokomo, IN 46901", false, 100).insert();
		new Store("818 Maple Lane, Mesa, AZ 85203", false, 124).insert();
		new Store("555 Hickory Street, Jackson Heights, NY 11372", false, 158).insert();
		new Store("994 Windsor Drive, Ashtabula, OH 44004", false, 200).insert();
		new Store("959 Holly Drive, Georgetown, SC 29440", false, 105).insert();
	}

	//Deletes all instances of store in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from store");
	}

	//Deletes a given instance of store from the database
	public void delete(){
		DBConnection.submitQuery("delete from store where address = '"+address+"'");
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

	//Set stockLimit
	public void setStockLimit(int stockLimit){
		this.stockLimit = stockLimit;
	}
}