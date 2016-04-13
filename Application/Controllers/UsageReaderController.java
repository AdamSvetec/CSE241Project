//Adam Svetec
//CSE241

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsageReaderController implements ActionListener{

	private UsageReaderView view;

	//Routes the actions performed in the view to their respective actions in the controller
    public void actionPerformed(ActionEvent ac){
    	if(ac.getActionCommand().equals("Choose File")){
    		File file = view.openFileChooser();
    		parseFile(file);
		}else if(ac.getActionCommand().equals("Exit")){
			new InterfaceSelectController();
			view.closeFrame();
		}
	}

	//Constructor
	public UsageReaderController(){
		view = new UsageReaderView();
		view.addController(this);
	}

	//Parse the selected file
	private void parseFile(File file){
		if(file == null){
			return;
		}
		StringBuilder outputString = new StringBuilder();
		StringBuilder errorString = new StringBuilder();
		outputString.append("Reading from file: "+file.getName()+"\n\n");

		FileReader input = null;
		try{
			input = new FileReader(file);
		}catch(FileNotFoundException fnfe){
			outputString.append("Error reading file.\nPlease ensure file is not opened elsewhere");
			new OutputView(outputString.toString(), "Error");
			return;
		}
		BufferedReader bufRead = new BufferedReader(input);

		int lineCounter = 0, phoneCallCount = 0, textMessageCount = 0, internetUseCount = 0, errorCounter = 0;
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
    				if(values.length != 5){
    					errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
    					errorCounter++;
    					continue;
    				}
    				int meid;
    				try{
    					values[1] = values[1].replaceAll("\\s+","");
    					meid = Integer.parseInt(values[1]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
    					errorCounter++;
    					continue;
    				}
    				Date sendTime;
    				try{
    					sendTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse send time\n");
    					errorCounter++;
    					continue;
    				}
    				int messageSize;
    				try{
    					values[3] = values[3].replaceAll("\\s+","");
    					messageSize = Integer.parseInt(values[3]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse message size\n");
    					errorCounter++;
    					continue;
    				}
    				String secondaryNumber;
    				values[4] = values[4].replaceAll("\\s+","");
    				if(values[4].length() == 10 && values[4].matches("[0-9]+")){
    					secondaryNumber = values[4];
    				}else{
    					errorString.append("Error at line "+lineCounter+": unable to parse secondary number\n");
    					errorCounter++;
    					continue;
    				}

    				TextMessage textMessage = TextMessage.create(meid, sendTime, messageSize, secondaryNumber);
    				if(-1 != textMessage.insert()){
    					textMessageCount++;
    				}else{
    					errorString.append("Error at line "+lineCounter+": unable to insert text message.\n\t-Check that meid is valid and message size is not too large\n");
    					errorCounter++;
    					continue;
    				}
    			//PHONE CALL
    			}else if(values[0].equals("phone call")){
    				if(values.length != 5){
    					errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
    					errorCounter++;
    					continue;
    				}
    				int meid;
    				try{
    					values[1] = values[1].replaceAll("\\s+","");
    					meid = Integer.parseInt(values[1]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
    					errorCounter++;
    					continue;
    				}
    				Date startTime;
    				try{
    					startTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse start time\n");
    					errorCounter++;
    					continue;
    				}
    				Date endTime;
    				try{
    					endTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[3]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse end time\n");
    					errorCounter++;
    					continue;
    				}
    				String secondaryNumber;
    				values[4] = values[4].replaceAll("\\s+","");
    				if(values[4].length() == 10 && values[4].matches("[0-9]+")){
    					secondaryNumber = values[4];
    				}else{
    					errorString.append("Error at line "+lineCounter+": unable to parse secondary number\n");
    					errorCounter++;
    					continue;
    				}

    				PhoneCall phoneCall = PhoneCall.create(meid, startTime, endTime, secondaryNumber);
    				if(-1 != phoneCall.insert()){
    					phoneCallCount++;
    				}else{
    					errorString.append("Error at line "+lineCounter+": unable to insert phone call.\n\t-Check that meid is valid\n");
    					errorCounter++;
    					continue;
    				}
    			//INTERNET ACCESS
    			}else if(values[0].equals("internet access")){
    				if(values.length != 4){
    					errorString.append("Error at line "+lineCounter+": number of values incorrect\n");
    					errorCounter++;
    					continue;
    				}
    				int meid;
    				try{
    					values[1] = values[1].replaceAll("\\s+","");
    					meid = Integer.parseInt(values[1]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse meid\n");
    					errorCounter++;
    					continue;
    				}
    				Date usageTime;
    				try{
    					usageTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(values[2]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse usage time\n");
    					errorCounter++;
    					continue;
    				}
    				int accessBytes;
    				try{
    					values[3] = values[3].replaceAll("\\s+","");
    					accessBytes = Integer.parseInt(values[3]);
    				}catch(Exception ex){
    					errorString.append("Error at line "+lineCounter+": unable to parse access bytes\n");
    					errorCounter++;
    					continue;
    				}

    				InternetAccess internetAccess = InternetAccess.create(meid, usageTime, accessBytes);
    				if(-1 != internetAccess.insert()){
    					internetUseCount++;
    				}else{
    					errorString.append("Error at line "+lineCounter+": unable to insert internet access.\n\t-Check that meid is valid and access bytes may be too large\n");
    					errorCounter++;
    					continue;
    				}
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
			new OutputView(outputString.toString(), "Error");
			return;
		}

		outputString.append("\tSUMMARY\n\n");
		outputString.append("Lines read: "+lineCounter+"\n");
		outputString.append("Phone Calls added: "+phoneCallCount+"\n");
		outputString.append("Text Messages added: "+textMessageCount+"\n");
		outputString.append("Internet Accesses added: "+internetUseCount+"\n");
		outputString.append("Errors encountered: "+errorCounter+"\n\n");
		outputString.append("\tERROR SUMMARY\n\n");
		outputString.append(errorString.toString());
		new OutputView(outputString.toString(), "Reader Summary");
	}		
}