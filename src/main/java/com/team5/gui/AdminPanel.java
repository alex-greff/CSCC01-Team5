package com.team5.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPanel extends SuperPanel {
	
	// Uploading iCare file | Generating report | Managing templates in template editor
	private JButton upload = new JButton("Upload iCare File");
	private JButton generate = new JButton("Generate Report");
	
	AdminPanel(JPanel content){
		super(content);
		
		// Adding Action Listener to buttons
		upload.addActionListener(eventHandler);
		generate.addActionListener(eventHandler);
		
		// Adding buttons
		add(upload, defaultConstraint);
		add(generate, defaultConstraint);
	}

}
