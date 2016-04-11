//Adam Svetec
//CSE241

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LoginController implements ActionListener{

	private LoginView view;

	//Routes the actions performed in the view to their respective actions in the controller
    public void actionPerformed(ActionEvent ac){
    	if(ac.getActionCommand().equals("Login")){
			login();
    	}
	}

	//Constuctor
	public LoginController(){
		view = new LoginView();
		view.addController(this);
    }

  	//Attempt to login user into the application
  	private void login(){
  		String username = view.getUserName();
		String password = view.getPassword();
		if(DBConnection.validate(username, password)){
			new InterfaceSelectController();
        	view.closeFrame();
		}else{
			//Add pop up frame or something similar
			Logger.logError("Invalid Login Credentials");
		}
  	}
}