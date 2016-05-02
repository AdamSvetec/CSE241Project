//Adam Svetec
//CSE241

public class LoginController{

	//Constuctor
	public LoginController(){
		login();
    }

  	//Attempt to login user into the application
  	private void login(){
 		do{
  			String username = CommandLineView.getString("Please enter oracle username: ");
			String password = CommandLineView.getString("Please enter oracle password: ");
			if(DBConnection.validate(username, password)){
				new InterfaceSelectController();
				return;
			}else{
				System.out.println("Error: username/password invalid");
			}
		}while(true);
  	}
}