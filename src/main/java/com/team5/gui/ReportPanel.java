package com.team5.gui;

import java.awt.Label;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReportPanel extends SuperPanel {
	
	private JLabel reportLabel = new JLabel("Report:", Label.RIGHT);
	private JTextField reportText = new JTextField();
	
	private JLabel saveLabel = new JLabel("Report Save Location:", Label.RIGHT);
	private JTextField directoryTextField = new JTextField();
	protected static int directoryTextFieldIndex;
	private JButton selectFileButton = new JButton("Select File");
	
	private JTextField feedbackText = new JTextField();
	protected static int feedbackTextFieldIndex;
	
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
		add(reportText, textFieldConstraint);
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(saveLabel, defaultConstraint);
		add(directoryTextField, textFieldConstraint);
		directoryTextFieldIndex = Arrays.asList(getComponents()).indexOf(directoryTextField); //Get index of save TextField
		add(selectFileButton, defaultConstraint);
		
		defaultConstraint.gridy = 2;
		defaultConstraint.gridx = 1;
		add(generateButton, defaultConstraint);
		
		textFieldConstraint.gridy = 3;
		textFieldConstraint.gridx = 0;
		textFieldConstraint.gridwidth = 4;
		textFieldConstraint.ipady = 100;
		textFieldConstraint.ipadx = 800;
		add(feedbackText, textFieldConstraint);
		feedbackTextFieldIndex = Arrays.asList(getComponents()).indexOf(feedbackText); //Get index of feedback TextField
		
		defaultConstraint.gridy = 4;
		defaultConstraint.gridx = 2;
		add(back, defaultConstraint);
	}
}
