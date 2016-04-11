//Adam Svetec
//CSE241

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceSelectController implements ActionListener{

	private InterfaceSelectView view;

	//Routes the actions performed in the view to their respective actions in the controller
    public void actionPerformed(ActionEvent ac){
    	if(ac.getActionCommand().equals("Interactive Interface")){
			//new UsageReaderController();
			//view.closeFrame();
    	}else if(ac.getActionCommand().equals("Stream Input")){
			new UsageReaderController();
			view.closeFrame();
		}else if(ac.getActionCommand().equals("Reporting System")){
			//new UsageReaderController();
			//view.closeFrame();
		}
	}

	//Constuctor
	public InterfaceSelectController(){
		view = new InterfaceSelectView();
		view.addController(this);
    }
}