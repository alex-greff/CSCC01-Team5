package com.team5.gui;

import java.awt.Label;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ICarePanel extends SuperPanel {
	
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static int directoryTextFieldIndex;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	private JButton selectFileButton = new JButton("Select Template");
	String[] templates = {"Placeholer 1", "Placeholder 2", "Placeholder 3"}; //TODO: Add template list
	private JComboBox templateDropDown = new JComboBox<String>(templates);
	
	private JTextField feedbackText = new JTextField();
	protected static int feedbackTextFieldIndex;
	
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
		directoryTextFieldIndex = Arrays.asList(getComponents()).indexOf(iCareNameTextField); //Get index of save TextField
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
		feedbackTextFieldIndex = Arrays.asList(getComponents()).indexOf(feedbackText); //Get index of feedback TextField
		
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
