package com.team5.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class AdminPanel extends SuperPanel {
	
	// Uploading iCare file | Generating report | Managing templates in template editor
	private JButton upload = new JButton("Upload iCare File");
	private JButton generate = new JButton("Generate Report");
	
	AdminPanel(JPanel content){
		super(content, "admin-panel-desc");
		
		// Adding Action Listener to buttons
		upload.addActionListener(eventHandler);
		generate.addActionListener(eventHandler);
		upload.setBackground(UIManager.getColor("ToolBar.dockingForeground"));
		upload.setBounds(189, 485, 260, 60);
		add(upload);
		generate.setBackground(UIManager.getColor("ToolBar.dockingForeground"));
		generate.setBounds(602, 485, 260, 60);
		add(generate);
		
		// Adding buttons

	
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("images/growth.png"));
		lblNewLabel.setBounds(602, 243, 323, 236);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("images/upload.png"));
		lblNewLabel_1.setBounds(189, 243, 275, 236);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("images/unnamed.png"));
		lblNewLabel_2.setBounds(293, 34, 533, 102);
		add(lblNewLabel_2);
		
		
	
	}

	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load(GUIManager.ADMIN);
	}
}
