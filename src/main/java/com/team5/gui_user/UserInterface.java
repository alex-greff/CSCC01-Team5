package com.team5.gui_user;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.*;


@SuppressWarnings("serial")
public class UserInterface extends JFrame {
	private JLabel status;
	private JTextField filename;
	private JButton submit;
	
	public UserInterface() {
		super("UserInterface");
		setSize(getScreenDimension().width/2, getScreenDimension().height/2);
		setResizable(false);
		setLocationRelativeTo(null); // Starts interface at center of screen
		setLayout(new GridBagLayout());
		
		status = new JLabel("Enter File Path:");
		filename = new JTextField();
		submit = new JButton("Upload iCare File");
		submit.addActionListener(new EventHandler());
		

	}
	/**
	 * Gets the screen size of the monitor that interface is being run on.
	 * @return Returns the dimension of the screen.
	 */
	public Dimension getScreenDimension() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		return dim;
	}

	/**
	 * Loads the contents on the interface i.e., the buttons and layouts. 
	 */
	private void loadContent() {
		GridBagConstraints constraint = new GridBagConstraints();
		GridBagConstraints constraintfile = new GridBagConstraints();
		// Internal button padding
		constraint.ipadx = 40;
		constraint.ipady = 40;
		constraintfile.ipadx = 130;
		constraintfile.ipady = 30;
		
		// Adding buttons
		add(status,constraint);
		add(filename,constraintfile);
		constraint.gridy = 2;
		add(submit, constraint);
		
	}

	/**
	 * Shows the interface and its contents.
	 */
	public void load() {
		setVisible(true);
		loadContent();
		revalidate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {    
		UserInterface gui_userexample = new UserInterface();
		gui_userexample.load();
	}    
 }