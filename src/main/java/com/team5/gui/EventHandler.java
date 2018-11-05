package com.team5.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
				JTextField directoryTextField = (JTextField) ((Container) content.getComponent(2)).getComponent(ReportPanel.directoryTextFieldIndex); //Populate save TextField using index
				directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
			}
		}
		else if (command == "Generate") {
			//TODO: Add Report Generate functionality
			JTextField feedbackTextField = (JTextField) ((Container) content.getComponent(2)).getComponent(ReportPanel.feedbackTextFieldIndex); //Populate feedback TextField using index
			feedbackTextField.setText("Functionality not yet implemented.");
		}
		else if (command == "Select Template") {
			JButton open = new JButton();
			JFileChooser reportFC = new JFileChooser();
			reportFC.setDialogTitle("Select File");
			reportFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (reportFC.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) { // Add path to textfield if 'open' button is clicked
				JTextField directoryTextField = (JTextField) ((Container) content.getComponent(1)).getComponent(ICarePanel.directoryTextFieldIndex); //Populate save TextField using index
				directoryTextField.setText(reportFC.getSelectedFile().getAbsolutePath());
			}
		}
		else if (command == "Upload") {
			//TODO: Add iCare file Upload functionality
			JTextField feedbackTextField = (JTextField) ((Container) content.getComponent(1)).getComponent(ICarePanel.feedbackTextFieldIndex); //Populate feedback TextField using index
			feedbackTextField.setText("Functionality not yet implemented.");
		}

	}

}
