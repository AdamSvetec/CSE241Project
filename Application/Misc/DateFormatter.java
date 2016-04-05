//Adam Svetec
//CSE241

import java.util.Date;
import java.text.SimpleDateFormat;

//DateFormatter that converts a Date into the format specified by Oracle SQL
public class DateFormatter{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");

	//Converts a date into the given format
	public static String toString(Date date){
		return sdf.format(date);
	}
}