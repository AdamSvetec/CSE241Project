//Adam Svetec
//CSE241

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

//DateFormatter that converts a Date into the format specified by Oracle SQL
public class DateFormatter{

	private static final Random rnd = new Random();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");

	//Converts a date into the given format
	public static String toString(Date date){
		return "TO_TIMESTAMP ('"+sdf.format(date)+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
	}

	//Gets a random date from the past 10ish years
	public static Date getRandomDate(){
		long ms = new Date().getTime() - 315360000000L + (Math.abs(rnd.nextLong()) % (10L * 365 * 24 * 60 * 60 * 1000));
		return new Date(ms);
	}
}