//Adam Svetec
//CSE 241

import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//InventoryItem object that will be intermediary between user interface and database inventory_item object
public class InventoryItem{

	private int meid;
	private String address;

	//Constructor
	private InventoryItem(int meid, String address){
		this.meid = meid;
		this.address = address;
	}

	//Create new InventoryItem not yet added to the database
	public static InventoryItem create(int meid, String address){
		return new InventoryItem(meid, address);
	}

	//Insert given InventoryItem into the database
	public boolean insert(){
		String query = "insert into inventory_item values ( '"+meid+"', '"+address+"' )";
		return DBConnection.submitQuery(query);
	}

	//Query db for given instance of inventory_item
	public static InventoryItem query(int meid){
		InventoryItem item = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from inventory_item where meid = "+meid);
			if(rs.next()){
				item = new InventoryItem(rs.getInt("meid"),rs.getString("address"));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return item;
	}

	//Update the given inventory_item in the database with new address
	public boolean update(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("update inventory_item set address = '"+address+"' where meid = "+meid);;
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
			return false;
		}
		return true;
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAll();
		Phone currentPhone;
		List<Store> storeList = Store.queryAll();
		Store currentStore;
		InventoryItem item;
		for(int counter = 1; counter < phoneList.size()*0.8; counter++){
			currentPhone = phoneList.get(new Random().nextInt(phoneList.size()));
			phoneList.remove(currentPhone);
			currentStore = storeList.get(new Random().nextInt(storeList.size()));
			item = new InventoryItem(currentPhone.getMeid(),currentStore.getAddress());
			item.insert();
		}
	}

	//Deletes all instances of inventory_item in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from inventory_item");
	}

	//Delete given instance of inventory_item from the database
	public void delete(){
		DBConnection.submitQuery("delete from inventory_item where meid = "+meid);
	}

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get address
	public String getAddress(){
		return address;
	}
}