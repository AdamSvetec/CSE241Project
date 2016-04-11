//Adam Svetec
//CSE 241

import java.io.*;
import java.sql.*;

//DBConnection is used to connect to the edgar1 database and validate users
//It also accepts queries and returns result sets
public class DBConnection{

	private static Connection conn;

	//Validate that the given password is correct
	public static boolean validate(String username, String passwd){
		try{
			conn = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", username, passwd);
			if(conn != null){
				return true;
			}
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return false;
	}

	//Submits a query and returns a boolean if successful
	public static boolean submitQuery(String query){
		try{
			Statement s = getConnection().createStatement();
			s.executeQuery(query);
			s.close();
			return true;
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return false;
	}

	//Gets the connection for edgar1 database
	//Returns null if error
	public static Connection getConnection(){
		return conn;
	}

	//Close Connection
	public static void closeConnection(){
		try{
			getConnection().close();
			conn = null;
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
	}
}