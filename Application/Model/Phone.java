//Adam Svetec
//CSE 241

import java.util.Random;
import java.util.List;
import java.util.Arrays;

//Phone object that will be intermediary between user interface and database phone object
public class Phone{

	private int meid;
	private String manufacturer;
	private String phoneModel;

	//Constructor
	private Phone(int meid, String manufacturer, String phoneModel){
		this.meid = meid;
		this.manufacturer = manufacturer;
		this.phoneModel = phoneModel;
	}

	//Insert given phone into the database
	public boolean insert(){
		String query = "insert into phone values ( '"+meid+"', '"+manufacturer+"', '"+phoneModel+"' )";
		return DBConnection.submitQueryBoolean(query);
	}

	//Populates database with random data
	public static void populateDB(){
		DBConnection.submitQueryBoolean("delete from phone");
		List<String> manufacturerNames = Arrays.asList("Apple", "Blackberry", "LG");
		List<String> appleModelNames = Arrays.asList("Iphone 5", "Iphone 6");
		List<String> blackberryModelNames = Arrays.asList("Passport", "Leap");
		List<String> lgModelNames = Arrays.asList("Leon", "K7", "Vista");
		Phone phone;
		String manufacturer;
		for(int meidCounter = 1; meidCounter < 999; meidCounter += new Random().nextInt(100)+1){
			manufacturer = manufacturerNames.get(new Random().nextInt(manufacturerNames.size()));
			if(manufacturer.equals("Apple")){
				phone = new Phone(meidCounter, manufacturer, appleModelNames.get(new Random().nextInt(appleModelNames.size())));
				phone.insert();
			}else if(manufacturer.equals("Blackberry")){
				phone = new Phone(meidCounter, manufacturer, blackberryModelNames.get(new Random().nextInt(blackberryModelNames.size())));
				phone.insert();
			}else if(manufacturer.equals("LG")){
				phone = new Phone(meidCounter, manufacturer, lgModelNames.get(new Random().nextInt(lgModelNames.size())));
				phone.insert();
			}
		}
	}
}