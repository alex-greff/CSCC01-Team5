package com.team5.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoClientException;
import com.mongodb.MongoTimeoutException;
import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.parser.MissingFieldException;
import com.team5.parser.TemplateParser;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.report.charts.Report;

public class EventHandler implements ActionListener {
	
	private CardLayout cLayout;
	private JPanel content;
	Boolean noExceptionRaised;

	EventHandler(JPanel contentJPanel) {
		content = contentJPanel;
		cLayout = (CardLayout) content.getLayout();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand().replaceAll("\\s+","");
		noExceptionRaised = true;
		Class a = getClass();
		
		Method method = null;
		try {
			method = getClass().getDeclaredMethod(command);
		} catch (NoSuchMethodException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SecurityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			method.invoke(this);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void UploadiCareFile() {
		cLayout.show(content, "Upload iCare File");
	}
	
	
	private void GenerateReport() {
		cLayout.show(content, "Generate Report");
	}
	
	
	private void Back() {
		cLayout.show(content, "Admin");
	}
	
	
	private void SelectFile() {
		JButton open = new JButton();
		JFileChooser reportFC = new JFileChooser();
		reportFC.setDialogTitle("Select File");
		reportFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (reportFC.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) { // Add path to textfield if 'open' button is clicked
			JTextField directoryTextField = ReportPanel.directoryTextFieldComponent;
			directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
		}	
	}
	
	
	private void Generate() {
		String reportClassName = "com.team5.report.implementations." + FilenameUtils.removeExtension(ReportPanel.reportDropDownComponent.getSelectedItem().toString());
		Class reportClass = null;
		
		try {
			reportClass = Class.forName(reportClassName);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block. Selected report type does not exist
			noExceptionRaised = false;
			ReportPanel.feedbackTextFieldComponent.setText("Report type not found");
			e1.printStackTrace();
		}
		
		String reportSaveDirectory = ReportPanel.directoryTextFieldComponent.getText().trim();
		if(!new File(reportSaveDirectory).exists()) {
			noExceptionRaised = false;
			ReportPanel.feedbackTextFieldComponent.setText("Directory not found");
		}
		
		String reportSaveLocation = reportSaveDirectory + "\\" + ReportPanel.nameTextFieldComponent.getText().trim() + ".png";
		if(new File(reportSaveLocation).exists()) {
			int confirm = JOptionPane.showConfirmDialog(null,
					"This file already exists. Are you sure you want to replace it?",
					"Warning",
					JOptionPane.YES_NO_OPTION);
			if(confirm == 1) {
				ReportPanel.feedbackTextFieldComponent.setText("File " + reportSaveLocation + " already exists");
				return;
			}
		}

		if (noExceptionRaised) {
			try {
				// Get an instance of the report
				final Report report = (Report) reportClass.newInstance();

				// Generate the report on a new thread to prevent blocking of the Admin GUI
				Thread t1 = new Thread(new Runnable() {
					public void run() {
						// Generate the report
						report.generate(reportSaveLocation);
					}}
				);

				// Run the thread
				t1.start();

			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				noExceptionRaised = false;
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				noExceptionRaised = false;
				e1.printStackTrace();
			}
		}

			// r.generate(reportName);
		if (noExceptionRaised) {
			ReportPanel.feedbackTextFieldComponent.setText(reportSaveLocation + " file successfully generated.");
		}
	}

	
	private void SelectTemplate() {
		JButton open = new JButton();
		JFileChooser reportFC = new JFileChooser();
		reportFC.setDialogTitle("Select File");
		reportFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (reportFC.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) { // Add path to textfield if 'open' button is clicked
			JTextField directoryTextField = ICarePanel.directoryTextFieldComponent;
			directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
		}
	}
	

	private void Upload() {
		String filePath = ICarePanel.directoryTextFieldComponent.getText(); // Inputted filepath to ICare file TODO: check that the file is actually an ICare file
		String selectedTemplateType = (String) ICarePanel.templateDropDownComponent.getSelectedItem(); // Selected template type
		JTextArea feedbackTextField = ICarePanel.feedbackTextFieldComponent; // Component for inserting feedback
		
		ArrayList<JSONObject> parsedFile = null;
		
		if(!new File(filePath).exists()) {
			ICarePanel.feedbackTextFieldComponent.setText("File does not exist");
			return;
		}
		
		// Parse the selected ICare file using the selected template type
		try {
			parsedFile = TemplateParser.GetJsonArray(filePath, selectedTemplateType+".json", "iCare-template-system");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			noExceptionRaised = false;
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			noExceptionRaised = false;
			e1.printStackTrace();
		} catch (ConfigurationNotFoundException e1) {
			// TODO Auto-generated catch block
			noExceptionRaised = false;
			e1.printStackTrace();
		} catch (MissingFieldException e1) {
			feedbackTextField.setText(String.format("The following required items are missing: %s", e1.getMissingField()));
			noExceptionRaised = false;
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("RANDOM!");
			noExceptionRaised = false;
			e1.printStackTrace();
		}
		
		DatabaseDriver db = null;
		if (noExceptionRaised) {
			// Connect to database
			try {
				db = new MongoDriver(ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString(),
						"icare_db", FilenameUtils.removeExtension(selectedTemplateType));
			} catch (ConfigurationNotFoundException e1) {
				// TODO Auto-generated catch block
				noExceptionRaised = false;
				e1.printStackTrace();
			} catch (MongoClientException e1) {
				// TODO Auto-generated catch block
				System.out.println("MONGO");
				noExceptionRaised = false;
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("RANDOM!");
				noExceptionRaised = false;
				e1.printStackTrace();
			}
		}
		
		if (noExceptionRaised) {
			try {
				// Insert parsed file into database
				db.insertMany(parsedFile);
				db.closeConnection();
				ICarePanel.feedbackTextFieldComponent.setText(filePath + " ICare file successfully uploaded");
			}catch (MongoTimeoutException e1) {
				// TODO Auto-generated catch block
				System.out.println("CAN'T CONNECT!");
					noExceptionRaised = false;
					e1.printStackTrace();
				}
			}
		}
}
