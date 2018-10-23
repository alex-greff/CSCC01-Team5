package com.team5.gui_user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Upload iCare File")) {
			// TODO Integrate the file upload feature
		} else if (e.getActionCommand().equals("Generate Report")) {
			// TODO Integrate the generate report feature
		} else if (e.getActionCommand().equals("Manage Templates")){
			// TODO Integrate the Manage Template Feature
		}
	}

}