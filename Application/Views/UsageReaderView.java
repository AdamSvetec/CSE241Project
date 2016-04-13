//Adam Svetec
//CSE241

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UsageReaderView{

    private JFrame f;
    private JPanel p;
    private FlowLayout fl;
    private JLabel prompt;
    private JButton openFileChooser;
    private JFileChooser fileChooser;
    
    //Constructor
    public UsageReaderView(){
        this.f = new JFrame("Login");
        p = new JPanel();
        fl = new FlowLayout(FlowLayout.CENTER);
        p.setLayout(fl);

        prompt = new JLabel("Please choose csv file to read usage entries:");
        openFileChooser = new JButton("Choose File");
        fileChooser = new JFileChooser();

        p.add(prompt);
        p.add(openFileChooser);

        f.setSize(300,300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(p);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    //Adds action listener to loginButton
    public void addController(ActionListener controller){
    	openFileChooser.addActionListener(controller);
    }

    //Opens the file chooser
    public File openFileChooser(){
    	File file = null;
		int returnVal = fileChooser.showOpenDialog(f);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
        }
        return file;
    }

    //Close this view
    public void closeFrame(){
        f.setVisible(false);
        f.dispose();
    }
}