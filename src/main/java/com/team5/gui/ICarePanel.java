package com.team5.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;

import com.team5.utilities.JSONLoader;

public class ICarePanel extends SuperPanel {
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	static File[] templates = getFiles("data\\templates\\iCare-templates", ".json");
	static String[] templateFileNames = getFileNames(templates);
	private JComboBox<String> templateDropDown = new JComboBox<String>(getDisplayNames(templates));
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
		feedbackConstraint.gridwidth = 3;
		feedbackTextFieldComponent = feedbackText;
		add(feedbackScrollPane, feedbackConstraint);
		
		defaultConstraint.gridy = 5;
		defaultConstraint.gridx = 2;
		add(back, defaultConstraint);
	}
	
	private static String[] getDisplayNames(File[] files){
		int i;
		String[] displayNames = new String[files.length];
		for(i=0; i<files.length; i++) {
			try {
				displayNames[i] = (String) JSONLoader.parseJSONFile(files[i]).get("display-name");
			}catch(Exception e) {
				displayNames[i] = FilenameUtils.removeExtension(files[i].getName()); // If cannot get display name, use file name
			}
		}
		
		return displayNames;
	}
	
	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.ICARE);
	}
}
