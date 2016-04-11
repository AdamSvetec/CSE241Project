import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;

public class LoginView{

    private JFrame f;
    private JPanel p;
    private FlowLayout fl;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    //Constructor
    public LoginView(){
        this.f = new JFrame("Login");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        f.setSize((width/2) - 600, (height/2) - 250);
        f.setLocationRelativeTo(null);
        p = new JPanel();
        fl = new FlowLayout(FlowLayout.CENTER);
        usernameLabel = new JLabel("Enter Username: ");
        usernameField = new JTextField(10);
        usernameField.setText("ajs217");
        passwordLabel = new JLabel("Enter Password: ");
        passwordField = new JPasswordField(12);
        loginButton = new JButton("Login");

        p.add(usernameLabel);
        p.add(usernameField);
        p.add(passwordLabel);
        p.add(passwordField);
        p.add(loginButton);
        p.setLayout(fl);

        f.setSize(200,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(p);
        f.setVisible(true);
        f.getRootPane().setDefaultButton(loginButton); //Allows user to hit enter button to login
    }

    //Adds action listener to loginButton
    public void addController(ActionListener controller){
        loginButton.addActionListener(controller);
    }

    //Gets the user name from the username text field
    public String getUserName(){
        return usernameField.getText();
    }

    //Gets the password from the password text field
    public String getPassword(){
        return passwordField.getText();
    }

    //Close this view
    public void closeFrame(){
        f.setVisible(false);
        f.dispose();
    }
}