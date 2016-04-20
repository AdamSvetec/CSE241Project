//Adam Svetec
//CSE241

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsageReaderController{

	//Constructor
	public UsageReaderController(){
		FileInputStream file = getFileToParse();
		parseFile(file);
		new InterfaceSelectController();
	}

	//Gets the file to parse for reader
	private FileInputStream getFileToParse(){
		do{
			try{
				String pathname = CommandLineView.getString("Please enter file path to be imported: ");
				return new FileInputStream(pathname);
			}catch(FileNotFoundException fnfe){
				System.out.println("Error: file path not found.");
			}
		}while(true);
	}

	//Parse the selected file
	private void parseFile(FileInputStream file){
		String output = new UsageImporter().importUsageFile(file);
		System.out.print(output);
	}		
}