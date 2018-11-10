package com.team5.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;

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
	
	protected static ArrayList<String> getFileNames(String filePath) {
		File directory = new File(".\\data\\templates\\iCare-templates");
		File[] fileList = directory.listFiles();
		ArrayList<String> fileNameList = new ArrayList<String>();

		// Add all the .json files into the list
		for(File file : fileList) {
			
			if (file.getName().endsWith(".json")) {
				fileNameList.add(file.getName());
			}
		}
		return fileNameList;
	}
}
