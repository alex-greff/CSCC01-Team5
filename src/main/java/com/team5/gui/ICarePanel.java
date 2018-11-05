package com.team5.gui;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ICarePanel extends JPanel {
	
	private JButton back;
	
	ICarePanel(JPanel content, CardLayout cLayout){
		super();
		
		EventHandler eventHandler = new EventHandler(content, cLayout);
		
		// Adding Action Listener to buttons
		back = new JButton("Back");
		back.addActionListener(eventHandler);
		
		// Adding buttons
		add(back);
	}
}
