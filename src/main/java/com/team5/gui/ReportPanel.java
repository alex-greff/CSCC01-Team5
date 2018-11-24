package com.team5.gui;

import java.awt.Label;
import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FilenameUtils;

import com.team5.report.charts.Report;

public class ReportPanel extends SuperPanel {

	private JLabel reportLabel = new JLabel("Report:", Label.RIGHT);
	private static File[] reports = getFiles("src\\main\\java\\com\\team5\\report\\implementations", ".java");
	public static String[] reportFileNames = getFileNames(reports);
	private JComboBox<String> reportDropDown = new JComboBox<String>(getDisplayNames(reports));
	protected static JComboBox<String> reportDropDownComponent;
	
	private JLabel saveLabel = new JLabel("Report Save Location:", Label.RIGHT);
	private JTextField directoryTextField = new JTextField();
	protected static JTextField directoryTextFieldComponent;
	
	private JLabel nameLabel = new JLabel("Report name");
	private JTextField nameTextField = new JTextField();
	protected static JTextField nameTextFieldComponent;
	
	protected static JTextArea feedbackTextFieldComponent;
	
	private JButton generateButton = new JButton("Generate");
	
	private JButton back = new JButton("Back");
	
	ReportPanel(JPanel content){
		super(content, "report-panel-desc");
		
		EventHandler eventHandler = new EventHandler(content);
		
		// Adding Action Listener to buttons
		selectFileButton.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		generateButton.addActionListener(eventHandler);
		
		// Adding buttons and labels
		add(descriptionLabel, descriptionConstraint);
		
		defaultConstraint.gridy = 1;
		textFieldConstraint.gridy = 1;
		add(reportLabel, defaultConstraint);
		add(reportDropDown, textFieldConstraint);
		reportDropDownComponent = reportDropDown;
		
		defaultConstraint.gridy = 2;
		textFieldConstraint.gridy = 2;
		add(saveLabel, defaultConstraint);
		add(directoryTextField, textFieldConstraint);
		directoryTextFieldComponent = directoryTextField;
		selectFileButton.setText("Select File");
		add(selectFileButton, defaultConstraint);
		
		defaultConstraint.gridy = 3;
		textFieldConstraint.gridy = 3;
		add(nameLabel, defaultConstraint);
		add(nameTextField, textFieldConstraint);
		nameTextFieldComponent = nameTextField;
		
		defaultConstraint.gridy = 4;
		defaultConstraint.gridx = 1;
		add(generateButton, defaultConstraint);
		
		feedbackConstraint.gridy = 5;
		add(feedbackScrollPane, feedbackConstraint);
		feedbackTextFieldComponent = feedbackText;
		
		defaultConstraint.gridy = 6;
		defaultConstraint.gridx = 2;
		add(back, defaultConstraint);
	}
	
	private String[] getDisplayNames(File[] files) {
		Class reportClass = null;
		
		int i;
		String[] displayNames = new String[files.length];
		for(i=0; i<files.length; i++) {
			String reportClassName = FilenameUtils.removeExtension(files[i].getName());
			String reportClassPath = "com.team5.report.implementations." + reportClassName;
			try {
				reportClass = Class.forName(reportClassPath);
				Report report = (Report) reportClass.newInstance();
				displayNames[i] = report.getReportName();
			}catch(Exception e) {
				displayNames[i] = reportClassName;
			}
		}
		
		return displayNames;
	}

	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.REPORT);
	}
}
