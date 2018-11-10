package com.team5.gui;

import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ICarePanel extends SuperPanel {
	
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	private JButton selectFileButton = new JButton("Select Template");
	ArrayList<String> templates = getFileNames("data\\templates\\iCare-templates");; //TODO: Add template list
	private JComboBox<String> templateDropDown = new JComboBox<String>(templates.toArray(new String[0]));
	
	private JTextField feedbackText = new JTextField();
	protected static JTextField feedbackTextFieldComponent;
	
	private JButton uploadButton = new JButton("Upload");
	
	private JButton back = new JButton("Back");
	
	ICarePanel(JPanel content){
		super(content);
		
		EventHandler eventHandler = new EventHandler(content);
		
		// Adding Action Listener to buttons
		selectFileButton.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		uploadButton.addActionListener(eventHandler);
		
		// Adding buttons and labels
		add(iCareLabel, defaultConstraint);
		add(iCareNameTextField, textFieldConstraint);
		directoryTextFieldComponent = iCareNameTextField;
		add(selectFileButton, defaultConstraint);
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(templateTypeLabel, defaultConstraint);
		add(templateDropDown, textFieldConstraint);
		
		defaultConstraint.gridy = 2;
		defaultConstraint.gridx = 1;
		add(uploadButton, defaultConstraint);
		
		textFieldConstraint.gridy = 3;
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
		admin_gui.load(GUIManager.ICARE);
	}
}
