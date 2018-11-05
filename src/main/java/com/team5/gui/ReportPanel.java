package com.team5.gui;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReportPanel extends JPanel {
	
	private JButton back;
	
	ReportPanel(JPanel content, CardLayout cLayout){
		super();
		
		EventHandler eventHandler = new EventHandler(content, cLayout);
		
		// Adding Action Listener to buttons
		back = new JButton("Back");
		back.addActionListener(eventHandler);
		
		// Adding buttons
		add(back);
	}
}
