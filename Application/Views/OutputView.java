//Adam Svetec
//CSE241

import javax.swing.*;
import java.awt.FlowLayout;

public class OutputView{

	private JFrame f;
    private JPanel p;
    private FlowLayout fl;
    private JTextArea outputField;

	//Constructor
	public OutputView(String outputString, String frameName){
		this.f = new JFrame(frameName);
        p = new JPanel();
        fl = new FlowLayout(FlowLayout.CENTER);

        outputField = new JTextArea(outputString);

        p.add(outputField);
        p.setLayout(fl);

        f.setContentPane(p);
        f.setLocationRelativeTo(null);  
        f.setVisible(true);
        f.setSize(outputField.getWidth()+10, outputField.getHeight()+20);
        f.setLocationRelativeTo(null);
	}
}