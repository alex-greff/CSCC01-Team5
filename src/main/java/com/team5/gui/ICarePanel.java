package com.team5.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;

import com.team5.utilities.JSONLoader;

public class ICarePanel extends SuperPanel {
	private JLabel iCareLabel = new JLabel("iCare File:", Label.RIGHT);
	private JTextField iCareNameTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel templateTypeLabel = new JLabel("Template Type:", Label.RIGHT);
	File[] templates = getFiles("data\\templates\\iCare-templates", ".json");
	private JComboBox<String> templateDropDown = new JComboBox<String>(getDisplayNames(templates));
	protected static JComboBox<String> templateDropDownComponent;
	
	protected static JTextArea feedbackTextFieldComponent;
	
	private JButton uploadButton = new JButton("Upload");
	
	private JButton back = new JButton("Back");
	
	ICarePanel(JPanel content){
		super(content, "iCare-panel-desc");
		
		EventHandler eventHandler = new EventHandler(content);
		// Adding Action Listener to buttons
		selectFileButton.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		uploadButton.addActionListener(eventHandler);
	
		// Adding buttons and labels
	

	
		
		back.setBounds(666, 505, 113, 33);
		add(back);
		
		setLayout(null);
		
		JLabel lblFileUpload = new JLabel("ICARE FILE UPLOAD");
		lblFileUpload.setFont(new Font("SansSerif", Font.BOLD, 33));
		lblFileUpload.setBounds(398, 86, 326, 51);
		add(lblFileUpload);
		
		JLabel lblUploadFileTo = new JLabel("Upload File to the Database for Report Generation\r\n");
		lblUploadFileTo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblUploadFileTo.setBounds(386, 126, 385, 51);
		add(lblUploadFileTo);

		feedbackScrollPane.setBackground(Color.WHITE);
		feedbackScrollPane.setForeground(Color.BLACK);
		feedbackScrollPane.setBounds(339, 248, 296, 40);
		add(feedbackScrollPane);
		
	
		selectFileButton.setBounds(645, 246, 113, 42);
		add(selectFileButton);
		
		templateDropDown.setForeground(Color.BLACK);
		templateDropDown.setBounds(155, 261, 295, 43);
		add(templateDropDown);
		templateDropDownComponent = templateDropDown;

		uploadButton.setBounds(326, 340, 113, 43);
		add(uploadButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("images/uploadsign.png"));
		lblNewLabel.setBounds(245, 325, 74, 73);
		add(lblNewLabel);
	
		iCareNameTextField.setBounds(136, 409, 437, 92);
		add(iCareNameTextField);
		iCareNameTextField.setColumns(10);
		
		JLabel lblSelectTemplateType = new JLabel("Select Template Type");
		lblSelectTemplateType.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSelectTemplateType.setBounds(700, 255, 147, 51);
		add(lblSelectTemplateType);
		setLayout(null);
		setBackground(Color.WHITE);
	}
	
	private static String[] getDisplayNames(File[] files){
		int i;
		String[] displayNames = new String[files.length];
		for(i=0; i<files.length; i++) {
			try {
				displayNames[i] = (String) JSONLoader.parseJSONFile(files[i]).get("display-name");
			}catch(Exception e) {
				displayNames[i] = FilenameUtils.removeExtension(files[i].getName()); // If cannot get display name, use file name
			}
		}
		
		return displayNames;
	}
	
	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.ICARE);
	}
}
