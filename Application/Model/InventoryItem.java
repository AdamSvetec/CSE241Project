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

	//Get meid
	public int getMeid(){
		return meid;
	}

	//Get address
	public String getAddress(){
		return address;
	}

	//Insert given InventoryItem into the database
	public boolean insert(){
		String query = "insert into inventory_item values ( '"+meid+"', '"+address+"' )";
		System.out.println(query);
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		List<Phone> phoneList = Phone.queryAllPhones();
		Phone currentPhone;
		List<Store> storeList = Store.queryAllStores();
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
		DBConnection.submitQueryBoolean("delete from inventory_item");
	}
}