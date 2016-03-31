//Adam Svetec
//CSE 241

import java.io.*;
import java.sql.*;

//DBConnection is used to connect to the edgar1 database and validate users
//It also accepts queries and returns result sets
public class DBConnection{

	private final static String username = "ajs217";
	private static String password;
	private static Connection conn;

	//Validate that the given password is correct
	public static boolean validate(String passwd){
		try{
			conn = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", username, passwd);
			if(conn != null){
				password = passwd;
				return true;
			}
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return false;
	}

	//Set password for connection
	public static void setPassword(String passwd){
		password = passwd;
	}

	//Submits a query and returns a boolean if successful
	public static boolean submitQueryBoolean(String query){
		try{
			Statement s = conn.createStatement();
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
			conn.close();
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
	}
}