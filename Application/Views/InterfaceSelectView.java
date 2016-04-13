//Adam Svetec
//CSE241

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InterfaceSelectView{

    private JFrame f;
    private JPanel p;
    private FlowLayout fl;
    private JLabel promptLabel;
    private JButton interactiveButton;
    private JButton streamButton;
    private JButton reportingButton;
    
    //Constructor
    public InterfaceSelectView(){
        this.f = new JFrame("Interface Selector");
        p = new JPanel();
        fl = new FlowLayout(FlowLayout.CENTER);
        promptLabel = new JLabel("Please select the interface you would like to enter:");
        interactiveButton = new JButton("Interactive Interface");
        streamButton = new JButton("Stream Input");
        reportingButton = new JButton("Reporting System");

        p.add(promptLabel);
        p.add(interactiveButton);
        p.add(streamButton);
        p.add(reportingButton);
        p.setLayout(fl);

        f.setSize(450,200);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(p);
        f.setVisible(true);
    }

    //Adds action listener to loginButton
    public void addController(ActionListener controller){
        interactiveButton.addActionListener(controller);
        streamButton.addActionListener(controller);
        reportingButton.addActionListener(controller);
    }

    //Close this view
    public void closeFrame(){
        f.setVisible(false);
        f.dispose();
    }
}