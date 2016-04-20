//Adam Svetec
//CSE241

public class InterfaceSelectController{
	//Constuctor
	public InterfaceSelectController(){
		selectInterface();
    }

    //Gets the user to select the interface they would like to use
    public void selectInterface(){
    	String interfaceString = CommandLineView.getString("Please enter interface you would like to use: \n\tinteractive, stream, or reporting\n");
    	while(true){
    		if(interfaceString.equals("interactive")){
    			//new InteractiveController();
    		}else if(interfaceString.equals("stream")){
    			new UsageReaderController();
    		}else if(interfaceString.equals("reporting")){
    			//new ReportingController();
    		}else{
    			interfaceString = CommandLineView.getString("Did not recognize selection.\nPlease enter interface you would like to use:\n\tinteractive, stream, or reporting\n");
    			continue;
    		}
    		return;
    	}
    }
}