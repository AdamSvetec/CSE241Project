//Adam Svetec
//CSE 241

import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.*;

//Plan object that will be intermediary between user interface and database plan object
public class Plan{

	//Enum to store plan_types
	public enum PlanType {
    	PayPerUse,
    	Unlimited
	}

	private PlanType planType;

	//Constructor
	private Plan(PlanType planType){
		this.planType = planType;
	}

	//Get planType
	public PlanType getPlanType(){
		return planType;
	}

	//Insert given plan_type into the database
	private boolean insert(){
		String query = "insert into plan values ( '"+planType.toString()+"' )";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		new Plan(PlanType.PayPerUse).insert();
		new Plan(PlanType.Unlimited).insert();
	}

	//Deletes all instances of plan in the database
	public static void deleteAll(){
		DBConnection.submitQueryBoolean("delete from plan");
	}
}