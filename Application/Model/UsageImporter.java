//Adam Svetec
//CSE 241

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsageImporter{

	private StringBuilder outputString;
	private StringBuilder errorString;
	private int lineCounter, phoneCallCount, textMessageCount, internetUseCount, errorCounter;

	public UsageImporter(){
		outputString = new StringBuilder();
		errorString = new StringBuilder();
		lineCounter = 0; 
		phoneCallCount = 0;
		textMessageCount = 0;
		internetUseCount = 0;
		errorCounter = 0;
	}

	//Import a usage file
	//Needs to be refactored
	public String importUsageFile(File file){
		
		if(file == null){
			outputString.append("Error reading file.\nPlease ensure file is not opened elsewhere");
			return outputString.toString();
		}
		
		outputString.append("Reading from file: "+file.getName()+"\n\n");

		FileReader input = null;
		try{
			input = new FileReader(file);
		}catch(FileNotFoundException fnfe){
			outputString.append("Error reading file.\nPlease ensure file is not opened elsewhere");
			return outputString.toString();
		}
		BufferedReader bufRead = new BufferedReader(input);

		String line;
		try{
			while ( (line = bufRead.readLine()) != null){   
				lineCounter++; 
    			String[] values = line.split(",");
    			if(values == null || values.length == 0){
    				continue;
    			}
    			//TEXT MESSAGE
    			if(values[0].equals("text message")){
    				importTextMessage(values);
    			//PHONE CALL
    			}else if(values[0].equals("phone call")){
    				importPhoneCall(values);
    			//INTERNET ACCESS
    			}else if(values[0].equals("internet access")){
    				importInternetAccess(values);
    			//WHITE SPACE
    			}else if(!values[0].matches(".*\\w.*")){
    				continue;
    			}else{
    				errorString.append("Error at line "+lineCounter+": type not recognized\n");
    				errorCounter++;
    			}
			}
		}catch(IOException ioe){
			outputString.append("Error at line "+lineCounter+": unable to be handled");
			return outputString.toString();
		}

		outputString.append("\tSUMMARY\n\n");
		outputString.append("Lines read: "+lineCounter+"\n");
		outputString.append("Phone Calls added: "+phoneCallCount+"\n");
		outputString.append("Text Messages added: "+textMessageCount+"\n");
		outputString.append("Internet Accesses added: "+internetUseCount+"\n");
		outputString.append("Errors encountered: "+errorCounter+"\n\n");
		outputString.append("\tERROR SUMMARY\n\n");
		outputString.append(errorString.toString());
		return outputString.toString();
	}

	//Import text message line
	private void importTextMessage(String [] values){
		if(values.length != 5){
			errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
			errorCounter++;
			return;
		}
		int meid;
		try{
			values[1] = values[1].replaceAll("\\s+","");
			meid = Integer.parseInt(values[1]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
			errorCounter++;
			return;
		}
		Date sendTime;
		try{
			sendTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse send time\n");
			errorCounter++;
			return;
		}
		int messageSize;
		try{
			values[3] = values[3].replaceAll("\\s+","");
			messageSize = Integer.parseInt(values[3]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse message size\n");
			errorCounter++;
			return;
		}
		String secondaryNumber;
		values[4] = values[4].replaceAll("\\s+","");
		if(values[4].length() == 10 && values[4].matches("[0-9]+")){
			secondaryNumber = values[4];
		}else{
			errorString.append("Error at line "+lineCounter+": unable to parse secondary number\n");
			errorCounter++;
			return;
		}

		TextMessage textMessage = TextMessage.create(meid, sendTime, messageSize, secondaryNumber);
		if(-1 != textMessage.insert()){
			textMessageCount++;
		}else{
			errorString.append("Error at line "+lineCounter+": unable to insert text message.\n\t-Check that meid is valid and message size is not too large\n");
			errorCounter++;
			return;
		}
	}

	//Import phone call line
	private void importPhoneCall(String [] values){
		if(values.length != 5){
			errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
			errorCounter++;
			return;
		}
		int meid;
		try{
			values[1] = values[1].replaceAll("\\s+","");
			meid = Integer.parseInt(values[1]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
			errorCounter++;
			return;
		}
		Date startTime;
		try{
			startTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse start time\n");
			errorCounter++;
			return;
		}
		Date endTime;
		try{
			endTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[3]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse end time\n");
			errorCounter++;
			return;
		}
		String secondaryNumber;
		values[4] = values[4].replaceAll("\\s+","");
		if(values[4].length() == 10 && values[4].matches("[0-9]+")){
			secondaryNumber = values[4];
		}else{
			errorString.append("Error at line "+lineCounter+": unable to parse secondary number\n");
			errorCounter++;
			return;
		}

		PhoneCall phoneCall = PhoneCall.create(meid, startTime, endTime, secondaryNumber);
		if(-1 != phoneCall.insert()){
			phoneCallCount++;
		}else{
			errorString.append("Error at line "+lineCounter+": unable to insert phone call.\n\t-Check that meid is valid\n");
			errorCounter++;
			return;
		}
	}

	//Import internet access
	private void importInternetAccess(String [] values){
		if(values.length != 4){
			errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
			errorCounter++;
			return;
		}
		int meid;
		try{
			values[1] = values[1].replaceAll("\\s+","");
			meid = Integer.parseInt(values[1]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
			errorCounter++;
			return;
		}
		Date usageTime;
		try{
			usageTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse usage time\n");
			errorCounter++;
			return;
		}
		int accessBytes;
		try{
			values[3] = values[3].replaceAll("\\s+","");
			accessBytes = Integer.parseInt(values[3]);
		}catch(Exception ex){
			errorString.append("Error at line "+lineCounter+": unable to parse access bytes\n");
			errorCounter++;
			return;
		}

		InternetAccess internetAccess = InternetAccess.create(meid, usageTime, accessBytes);
		if(-1 != internetAccess.insert()){
			internetUseCount++;
		}else{
			errorString.append("Error at line "+lineCounter+": unable to insert internet access.\n\t-Check that meid is valid and access bytes may be too large\n");
			errorCounter++;
			return;
		}
	}
}