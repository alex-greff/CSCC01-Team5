package com.team5.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.parser.MissingFieldException;
import com.team5.parser.TemplateParser;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;

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

		if (command == "Upload iCare File"){
			cLayout.show(content, "Upload iCare File");
		}
		else if (command == "Generate Report") {
			cLayout.show(content, "Generate Report");
		}
		else if (command == "Back"){
			cLayout.show(content, "Admin");
		}
		else if (command == "Select File") {
			JButton open = new JButton();
			JFileChooser reportFC = new JFileChooser();
			reportFC.setDialogTitle("Select File");
			reportFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (reportFC.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) { // Add path to textfield if 'open' button is clicked
				JTextField directoryTextField = ReportPanel.directoryTextFieldComponent;
				directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
			}
		}
		else if (command == "Generate") {
			//TODO: Add Report Generate functionality
			JTextField feedbackTextField = ReportPanel.feedbackTextFieldComponent;
			feedbackTextField.setText("Functionality not yet implemented.");
		}
		else if (command == "Select Template") {
			JButton open = new JButton();
			JFileChooser reportFC = new JFileChooser();
			reportFC.setDialogTitle("Select File");
			reportFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (reportFC.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) { // Add path to textfield if 'open' button is clicked
				JTextField directoryTextField = ICarePanel.directoryTextFieldComponent;
				directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
			}
		}
		else if (command == "Upload") {
			String filePath = ICarePanel.directoryTextFieldComponent.getText();
			
			JTextField feedbackTextField = ICarePanel.feedbackTextFieldComponent;
			
			ArrayList<JSONObject> parsedFile = null;
			
			try {
				parsedFile = TemplateParser.GetJsonArray(filePath, "client_profile.json", "iCare-template-system");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ConfigurationNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MissingFieldException e1) {
				feedbackTextField.setText(String.format("The following required items are missing: %s", e1.getMissingField()));
				e1.printStackTrace();
			}
			
			feedbackTextField.setText(parsedFile.toString());
			DatabaseDriver db = null;
			try {
				db = new MongoDriver(ConfigurationLoader.loadConfiguration("database-URI").get("test_db_saad").toString(),
						"test_db", "client_profile");
			} catch (ConfigurationNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.insertMany(parsedFile);
		}

	}

}
