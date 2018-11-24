package com.team5.gui;

import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ICarePanel extends SuperPanel {
	
	private JTextField descriptionLabel = new JTextField("test"); //TODO: Add description text in config
	
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	private JButton selectFileButton = new JButton("Select Template");
	String[] templates = getFileNames("data\\templates\\iCare-templates", ".json");
	private JComboBox<String> templateDropDown = new JComboBox<String>(templates);
	protected static JComboBox<String> templateDropDownComponent;
	
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
		descriptionLabel.setEditable(false);
		add(descriptionLabel, descriptionConstraint);
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(iCareLabel, defaultConstraint);
		add(iCareNameTextField, textFieldConstraint);
		directoryTextFieldComponent = iCareNameTextField;
		add(selectFileButton, defaultConstraint);

		defaultConstraint.gridy = 2;
		textFieldConstraint.gridy = 2;
		add(templateTypeLabel, defaultConstraint);
		add(templateDropDown, textFieldConstraint);
		templateDropDownComponent = templateDropDown;
		
		defaultConstraint.gridy = 3;
		defaultConstraint.gridx = 1;
		add(uploadButton, defaultConstraint);
		
		descriptionConstraint.gridy = 4;
		descriptionConstraint.gridx = 0;
		feedbackText.setEditable(false);
		add(feedbackText, descriptionConstraint);
		feedbackTextFieldComponent = feedbackText;
		
		defaultConstraint.gridy = 5;
		defaultConstraint.gridx = 2;
		add(back, defaultConstraint);
	}
	
	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.ICARE);
	}
}
