package com.team5.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class EventHandler implements ActionListener {
	
	private CardLayout cLayout;
	private JPanel content;

	EventHandler(JPanel contentJPanel) {
		content = contentJPanel;
		cLayout = (CardLayout) content.getLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command == "Upload iCare File"){ cLayout.show(content, "Upload iCare File"); }
		else if (command == "Generate Report") { cLayout.show(content, "Generate Report"); }
		else if (command == "Back") { cLayout.show(content, "Admin"); }

	}

}
