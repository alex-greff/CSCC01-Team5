package com.team5.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.FilenameUtils;

import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;

public class SuperPanel extends JPanel {
	
	protected String config = "gui";
	
	EventHandler eventHandler;
	
	GridBagConstraints defaultConstraint;
	GridBagConstraints textFieldConstraint;
	GridBagConstraints descriptionConstraint;
	GridBagConstraints feedbackConstraint;
	
	protected JButton selectFileButton;
	protected JTextArea descriptionLabel;
	protected JTextArea feedbackText = new JTextArea();
	protected JScrollPane feedbackScrollPane = new JScrollPane(
			feedbackText,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public static int defaultSize = 30;

	SuperPanel(JPanel content, String panelConfig){
		// Setting EventHandler ---
		eventHandler = new EventHandler(content);
		
		// Customizing default layout ---
		setLayout(new GridBagLayout());
		defaultConstraint = new GridBagConstraints();
		defaultConstraint.weightx = 0.0;
		
		// Internal button padding
		defaultConstraint.ipadx = defaultSize;
		defaultConstraint.ipady = defaultSize;
		
		// External button padding
		defaultConstraint.insets = new Insets(0, 0, 0, 0);
		
		// Customizing TextField layout ---
		textFieldConstraint = (GridBagConstraints) defaultConstraint.clone();
		textFieldConstraint.ipadx = 300;
		
		// Customizing description layout ---
		descriptionConstraint =  (GridBagConstraints) defaultConstraint.clone();
		descriptionConstraint.gridwidth = 4;
		
		// Customizing feedback field layout ---
		feedbackConstraint = (GridBagConstraints) descriptionConstraint.clone();
		feedbackConstraint.ipady = 100;
		feedbackConstraint.ipadx = 800;
		
		// Set up buttons and panels
		BufferedImage selectButtonIcon;
		try {
			selectButtonIcon = ImageIO.read(new File(ConfigurationLoader.loadConfiguration(config).get("select-file-image").toString()));
			selectFileButton = new JButton(new ImageIcon(selectButtonIcon));
			selectFileButton.setBorderPainted(false);
			selectFileButton.setContentAreaFilled(false);
		} catch (IOException e) {
			selectButtonIcon = null;
			e.printStackTrace();
		} catch (ConfigurationNotFoundException e) {
			selectFileButton = new JButton();
			e.printStackTrace();
		}
		
		
		try {
			descriptionLabel = new JTextArea(ConfigurationLoader.loadConfiguration(config).get(panelConfig).toString());
		} catch (ConfigurationNotFoundException e) {
			descriptionLabel = new JTextArea("Description not found");
			e.printStackTrace();
		}
		descriptionLabel.setEditable(false);
		descriptionLabel.setOpaque(false);
		
		feedbackText.setLineWrap(true);
		feedbackText.setEditable(false);
	}
	
	protected static File[] getFiles(String filePath, String extension) {
		File directory = new File(".\\"+filePath);
		File[] fileList = directory.listFiles();
		ArrayList<File> selectiveFileList = new ArrayList<File>();

		// Add all the .json files into the list
		for(File file : fileList) {
			
			if (file.getName().endsWith(extension)) {
				selectiveFileList.add(file);
			}
		}
		return selectiveFileList.toArray(new File[0]);
	}
}
