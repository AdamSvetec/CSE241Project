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

	//Query for all customers in database
	public static List<Customer> queryAllCustomers(){
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
			Logger.logError(sqle.getMessage());
		}
		return customerList;
	}

	//Insert given Customer into the database
	public boolean insert(){
		String query = "insert into customer values ( '"+customerId+"', '"+name+"', '"+address+"')";
		return DBConnection.submitQuery(query);
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
}