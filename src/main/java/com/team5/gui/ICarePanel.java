package com.team5.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ICarePanel extends JPanel {
	
	private JButton back;
	
	ICarePanel(JPanel content){
		super();
		
		EventHandler eventHandler = new EventHandler(content);
		
		// Adding Action Listener to buttons
		back = new JButton("Back");
		back.addActionListener(eventHandler);
		
		// Adding buttons
		add(back);
	}
}
