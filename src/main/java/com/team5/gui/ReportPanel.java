package com.team5.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReportPanel extends JPanel {
	
	private JButton back;
	
	ReportPanel(JPanel content){
		super();
		
		EventHandler eventHandler = new EventHandler(content);
		
		// Adding Action Listener to buttons
		back = new JButton("Back");
		back.addActionListener(eventHandler);
		
		// Adding buttons
		add(back);
	}
}
