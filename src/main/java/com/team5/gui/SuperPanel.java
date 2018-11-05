package com.team5.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class SuperPanel extends JPanel {
	
	EventHandler eventHandler;
	GridBagConstraints defaultConstraint;
	GridBagConstraints textFieldConstraint;

	SuperPanel(JPanel content){
		// Setting EventHandler ---
		eventHandler = new EventHandler(content);
		
		// Customizing default layout ---
		setLayout(new GridBagLayout());
		defaultConstraint = new GridBagConstraints();
		
		// Internal button padding
		defaultConstraint.ipadx = 40;
		defaultConstraint.ipady = 40;
		
		// External button padding
		defaultConstraint.insets = new Insets(60, 0, 0, 0);
		
		// Customizing TextField layout ---
		textFieldConstraint = (GridBagConstraints) defaultConstraint.clone();
		textFieldConstraint.ipadx = 500;
	}
}
