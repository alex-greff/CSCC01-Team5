package com.team5.gui;

import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ICarePanel extends SuperPanel {
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	String[] templates = getFileNames("data\\templates\\iCare-templates", ".json");
	private JComboBox<String> templateDropDown = new JComboBox<String>(templates);
	protected static JComboBox<String> templateDropDownComponent;
	
	protected static JTextArea feedbackTextFieldComponent;
	
	private JButton uploadButton = new JButton("Upload");
	
	private JButton back = new JButton("Back");
	
	ICarePanel(JPanel content){
		super(content, "iCare-panel-desc");
		
		EventHandler eventHandler = new EventHandler(content);
				
		// Adding Action Listener to buttons
		selectFileButton.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		uploadButton.addActionListener(eventHandler);

		// Adding buttons and labels
		add(descriptionLabel, descriptionConstraint);
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(iCareLabel, defaultConstraint);
		add(iCareNameTextField, textFieldConstraint);
		directoryTextFieldComponent = iCareNameTextField;
		selectFileButton.setText("Select Template");
		add(selectFileButton, defaultConstraint);

		defaultConstraint.gridy = 2;
		textFieldConstraint.gridy = 2;
		add(templateTypeLabel, defaultConstraint);
		add(templateDropDown, textFieldConstraint);
		templateDropDownComponent = templateDropDown;
		
		defaultConstraint.gridy = 3;
		defaultConstraint.gridx = 1;
		add(uploadButton, defaultConstraint);
		
		feedbackConstraint.gridy = 4;
		feedbackTextFieldComponent = feedbackText;
		add(feedbackScrollPane, feedbackConstraint);
		
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
