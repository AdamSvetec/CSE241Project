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
		String output = new UsageImporter().importUsageFile(file);
		new OutputView(output, "Usage Import");
	}		
}