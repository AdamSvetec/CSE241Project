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
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
		}
		return false;
	}

	//Set password for connection
	public static void setPassword(String passwd){
		password = passwd;
	}
}