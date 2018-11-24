package com.team5.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Interface for the admin user
 */
@SuppressWarnings("serial")
public class GUIManager extends JFrame {
	
	
	final static String ADMIN = "Admin";
	final static String ICARE = "Upload iCare File";
	final static String REPORT = "Generate Report";

	private JPanel content = new JPanel();
	CardLayout cLayout = new CardLayout();

	/**
	 * Constructs admin interface
	 * @param title The title of the interface window.
	 */
	public GUIManager(String title) {
		super(title);
		int size = SuperPanel.defaultSize*30;
		setSize(size, size/2);
		setResizable(false);
		setLocationRelativeTo(null); // Starts interface at center of screen
		
		content.setLayout(cLayout);
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
	private void loadContent(String page) {
		
		JPanel adminPanel = new AdminPanel(content);
		JPanel icarePanel = new ICarePanel(content);
		JPanel reportPanel = new ReportPanel(content);
		
		content.add(adminPanel, ADMIN);
		content.add(icarePanel, ICARE);
		content.add(reportPanel, REPORT);
		
		cLayout.show(content, page);
		
		this.add(content);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Shows the interface and its contents.
	 */
	public void load() {
		load("Admin");
	}
	public void load(String page) {
		setVisible(true);
		loadContent(page);
		revalidate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// DEMO
	public static void main(String[] args) {
		GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load();
	}
}
