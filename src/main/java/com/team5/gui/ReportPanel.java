package com.team5.gui;

import java.awt.Label;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReportPanel extends SuperPanel {
	
	private JLabel reportLabel = new JLabel("Report:", Label.RIGHT);
	private String[] reports = getFileNames("src\\main\\java\\com\\team5\\report\\implementations", ".java");
	private JComboBox<String> reportDropDown = new JComboBox<String>(reports);
	protected static JComboBox<String> reportDropDownComponent;
	
	private JLabel saveLabel = new JLabel("Report Save Location:", Label.RIGHT);
	private JTextField directoryTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	private JButton selectFileButton = new JButton("Select File");
	
	private JLabel nameLabel = new JLabel("Report name");
	private JTextField nameTextField = new JTextField();
	protected static JTextField nameTextFieldComponent;
	
	private JTextField feedbackText = new JTextField();
	protected static JTextField feedbackTextFieldComponent;
	
	private JButton generateButton = new JButton("Generate");
	
	private JButton back = new JButton("Back");
	
	ReportPanel(JPanel content){
		super(content);
		
		EventHandler eventHandler = new EventHandler(content);
		
		// Adding Action Listener to buttons
		selectFileButton.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		generateButton.addActionListener(eventHandler);
		
		// Adding buttons and labels
		add(reportLabel, defaultConstraint);
		add(reportDropDown, textFieldConstraint);
		reportDropDownComponent = reportDropDown;
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(saveLabel, defaultConstraint);
		add(directoryTextField, textFieldConstraint);
		directoryTextFieldComponent = directoryTextField;
		add(selectFileButton, defaultConstraint);
		
		defaultConstraint.gridy = 2;
		textFieldConstraint.gridy = 2;
		add(nameLabel, defaultConstraint);
		add(nameTextField, textFieldConstraint);
		nameTextFieldComponent = nameTextField;
		
		defaultConstraint.gridy = 3;
		defaultConstraint.gridx = 1;
		add(generateButton, defaultConstraint);
		
		textFieldConstraint.gridy = 4;
		textFieldConstraint.gridx = 0;
		textFieldConstraint.gridwidth = 4;
		textFieldConstraint.ipady = 100;
		textFieldConstraint.ipadx = 800;
		feedbackText.setEditable(false);
		add(feedbackText, textFieldConstraint);
		feedbackTextFieldComponent = feedbackText;
		
		defaultConstraint.gridy = 4;
		defaultConstraint.gridx = 2;
		add(back, defaultConstraint);
	}
	
	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.REPORT);
	}
}
