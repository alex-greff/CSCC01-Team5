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
	
	public static int defaultSize = 40;

	SuperPanel(JPanel content){
		// Setting EventHandler ---
		eventHandler = new EventHandler(content);
		
		// Customizing default layout ---
		setLayout(new GridBagLayout());
		defaultConstraint = new GridBagConstraints();
		
		// Internal button padding
		defaultConstraint.ipadx = defaultSize;
		defaultConstraint.ipady = defaultSize;
		
		// External button padding
		defaultConstraint.insets = new Insets(60, 0, 0, 0);
		
		// Customizing TextField layout ---
		textFieldConstraint = (GridBagConstraints) defaultConstraint.clone();
		textFieldConstraint.ipadx = 500;
	}
	
	protected static String[] getFileNames(String filePath, String extension) {
		File directory = new File(".\\"+filePath);
		File[] fileList = directory.listFiles();
		ArrayList<String> fileNameList = new ArrayList<String>();

		// Add all the .json files into the list
		for(File file : fileList) {
			
			if (file.getName().endsWith(extension)) {
				fileNameList.add(file.getName());
			}
		}
		return fileNameList.toArray(new String[0]);
	}
}
