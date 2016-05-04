//Adam Svetec
//CSE 241

import java.sql.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

//Customer object that will be intermediary between user interface and database customer object
public class Customer{

	private int customerId;
	private String name;
	private String address;

	//Constructor
	private Customer(int customerId, String name, String address){
		this.customerId = customerId;
		this.name = name;
		this.address = address;
	}

	//Create given Customer
	//Set customerId to -1 until inserted
	public static Customer create(String name, String address){
		return new Customer(-1,name,address);
	}

	//Insert the given Customer into the database
	//Use PLSQL trigger to automatically increment id
	//TODO: ID needs to be updated when doing this...
	public boolean insert(){
		//String query = "insert into customer values ( '"+customerId+"', '"+name+"', '"+address+"')";
		String query = "insert into customer values ( ?, ?, ?)";
		List<String> params = Arrays.asList(""+customerId, name, address);
		return DBConnection.submitPreparedQuery(query, params);
	}

	//Query a given Customer from the db given an id
	public static Customer query(int id){
		Customer customer = null;
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from customer where customer_id = " + id);
			if(rs.next()){
				customer = new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("address"));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.log(sqle.getMessage());
		}
		return customer;
	}

	//Update a given Customer with the new values
	public boolean update(){
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("update customer set name = '"+name+"', address = '"+address+"' where customer_id = "+customerId);
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.log(sqle.getMessage());
			return false;
		}
		return true;
	}

	//Query for all customers in database
	public static List<Customer> queryAll(){
		List<Customer> customerList = new ArrayList<Customer>();
		Connection conn = DBConnection.getConnection();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from customer");
			while(rs.next()){
				customerList.add(new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("address")));
			}
			rs.close();
			s.close();
		}catch(SQLException sqle){
			Logger.log(sqle.getMessage());
		}
		return customerList;
	}

	//Populates database with random data
	public static void populateDB(){
		List<String> nameList = Arrays.asList("Adam Smith", "Johnny Appleseed", "Henry Perez", "Eli Hess","Chris Park","James Cirgnano","Tom Deeble","Jeff Tilley","Spencer Lowlicht","Lance Armstrong");
		List<String> addressList = Arrays.asList("829 Summit Avenue, New Haven, CT 06511","187 Country Club Drive, Matthews, NC 28104","338 Heather Lane, Roslindale, MA 02131","736 Sycamore Street, Deland, FL 32720","275 Strawberry Lane, Chippewa Falls, WI 54729","281 Route 5, Pueblo, CO 81001","278 Windsor Court, Glen Allen, VA 23059","654 Eagle Street, Painesville, OH 44077","208 Railroad Avenue, Libertyville, IL 60048","999 Madison Avenue, Long Branch, NJ 07740");
		Customer cust;
		for(int counter = 0; counter < 10; counter++){
			cust = new Customer(counter, nameList.get(counter),addressList.get(counter));
			cust.insert();
		}
	}

	//Deletes all instances of customer in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from customer");
	}

	//Delete given instance of customer in the database
	public void delete(){
		DBConnection.submitQuery("delete from customer where customer_id = "+customerId);
	}

	//Get customerId
	public int getCustomerId(){
		return customerId;
	}

	//Get name
	public String getName(){
		return name;
	}

	//Get address
	public String getAddress(){
		return address;
	}

	//Set name
	public void setName(String name){
		this.name = name;
	}

	//Set address
	public void setAddress(String address){
		this.address = address;
	}

	//Get description string
	public String getDescription(){
		return "ID: "+customerId+", Name: "+name+", Address: "+address;
	}
}