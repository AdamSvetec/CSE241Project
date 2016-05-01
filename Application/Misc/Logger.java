//Adam Svetec
//CSE 241

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Date;

//Logger object that will be called to output errors to whichever medium desired
public class Logger{

	//Populates database with random data
	public static void logError(String error){
		System.out.println("Error: "+error);

		try {
			File yourFile = new File("RuntimeLog.txt");
    		yourFile.createNewFile();
            Files.write(Paths.get(yourFile.getPath()), (""+new Date()+" : "+error+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException ioe) {
            System.out.println("Error: logging failed");
        }
	}
}