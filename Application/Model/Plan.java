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

	//Convert a given string into it's corresponding Plan Type
	public static PlanType stringToPlanType(String type){
		if(type.equals(PlanType.PayPerUse.toString())){
			return PlanType.PayPerUse;
		}else if(type.equals(PlanType.Unlimited.toString())){
			return PlanType.Unlimited;
		}
		return null;
	}

	private PlanType planType;

	//Constructor
	private Plan(PlanType planType){
		this.planType = planType;
	}

	//Insert given plan_type into the database
	private boolean insert(){
		//String query = "insert into plan values ( '"+planType.toString()+"' )";
		String query = "insert into plan values ( ? )";
		List<String> params = Arrays.asList(planType.toString());
		return DBConnection.submitPreparedQuery(query, params);
	}

	//Populates database with random data
	public static void populateDB(){
		new Plan(PlanType.PayPerUse).insert();
		new Plan(PlanType.Unlimited).insert();
	}

	//Deletes all instances of plan in the database
	public static void deleteAll(){
		DBConnection.submitQuery("delete from plan");
	}

	//Get planType
	public PlanType getPlanType(){
		return planType;
	}
}