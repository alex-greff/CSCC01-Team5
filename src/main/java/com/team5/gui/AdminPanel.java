package com.team5.gui;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPanel extends JPanel {
	
	// Uploading iCare file | Generating report | Managing templates in template editor
	private JButton upload, generate;
	
	AdminPanel(JPanel content, CardLayout cLayout){
		super();
		
		EventHandler eventHandler = new EventHandler(content, cLayout);
		
		// Adding Action Listener to buttons
		upload = new JButton("Upload iCare File");
		upload.addActionListener(eventHandler);
		
		generate = new JButton("Generate Report");
		generate.addActionListener(eventHandler);
		
		// Adding buttons
		add(upload);
		add(generate);
	}

}
