package com.team5.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The Interface for the admin user
 */
@SuppressWarnings("serial")
public class AdminInterface extends JFrame {

	// Uploading iCare file | Generating report | Managing templates in template editor
	private JButton upload, generate, manage;

	/**
	 * Constructs admin interface
	 * @param title The title of the interface window.
	 */
	public AdminInterface(String title) {
		super(title);
		setSize(getScreenDimension().width/2, getScreenDimension().height/2);
		setResizable(false);
		setLocationRelativeTo(null); // Starts interface at center of screen
		setLayout(new GridBagLayout());
		
		upload = new JButton("Upload iCare File");
		upload.addActionListener(new EventHandler());
		
		generate = new JButton("Generate Report");
		generate.addActionListener(new EventHandler());
		
		manage = new JButton("Manage Templates");
		manage.addActionListener(new EventHandler());
	}

	/**
	 * Gets the screen size of the monitor that interface is being run on.
	 * @return Returns the dimension of the screen.
	 */
	public Dimension getScreenDimension() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		return dim;
	}

	/**
	 * Loads the contents on the interface i.e., the buttons and layouts. 
	 */
	private void loadContent() {
		GridBagConstraints constraint = new GridBagConstraints();
		// Internal button padding
		constraint.ipadx = 40;
		constraint.ipady = 40;
		
		// Adding buttons
		add(upload, constraint);
		
		constraint.insets = new Insets(60, 0, 0, 0); // External button padding
		constraint.gridy = 1; // Make button below button above
		add(generate, constraint);
		
		constraint.gridy = 2; // Below generate button
		add(manage, constraint);
	}

	/**
	 * Shows the interface and its contents.
	 */
	public void load() {
		setVisible(true);
		loadContent();
		revalidate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
