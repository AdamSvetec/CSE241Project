//Adam Svetec
//CSE 241

import java.io.*;
import java.sql.*;

//DBConnection is used to connect to the edgar1 database and validate users
//It also accepts queries and returns result sets
public class DBConnection{

	private final static String username = "ajs217";
	private static String password;

	//Validate that the given password is correct
	public static boolean validate(String passwd){
		Connection connection;
		try{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", username, passwd);
			if(connection != null){
				connection.close();
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
		Connection conn = getConnection();
		if(conn == null){
			return false;
		}
		try{
			Statement s = conn.createStatement();
			s.executeQuery(query);
			s.close();
			conn.close();
			return true;
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return false;
	}

	//Submits a query and returns a ResultSet
	//Returns null if there is an error
	public static ResultSet submitQueryResultSet(String query){
		Connection conn = getConnection();
		if(conn == null){
			return null;
		}
		try{
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			s.close();
			conn.close();
			return rs;
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return null;
	}

	//Gets the connection for edgar1 database
	//Returns null if error
	public static Connection getConnection(){
		Connection connection;
		try{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", username, password);
			return connection;
		}catch(SQLException sqle){
			Logger.logError(sqle.getMessage());
		}
		return null;
	}
}