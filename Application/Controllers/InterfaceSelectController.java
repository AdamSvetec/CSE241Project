//Adam Svetec
//CSE241

public class InterfaceSelectController{
	//Constuctor
	public InterfaceSelectController(){
		selectInterface();
    }

    //Gets the user to select the interface they would like to use
    public void selectInterface(){
    	int interfaceChoice = CommandLineView.getInt("Please enter interface you would like to explore: \n\t1 : Interactive\n\t-Employee handles requests from customer in store\n\t2 : Streaming\n\t-Employee reads in usage record file\n\t3 : Exit\n");
    	while(true){
    		if(interfaceChoice == 1){
    			new InteractiveController();
    		}else if(interfaceChoice == 2){
    			new UsageReaderController();
    		}else if(interfaceChoice == 3){
		        return;
		}else{
                System.out.println("Error: did not recognize choice, please try again");
    			selectInterface();
    		}
    		return;
    	}
    }
}