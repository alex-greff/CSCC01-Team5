package com.team5.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The Interface for the admin user
 */
@SuppressWarnings("serial")
public class AdminInterface extends JFrame {

	private JPanel content = new JPanel();
	CardLayout cLayout = new CardLayout();

	/**
	 * Constructs admin interface
	 * @param title The title of the interface window.
	 */
	public AdminInterface(String title) {
		super(title);
		setSize(getScreenDimension().width/2, getScreenDimension().height/2);
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
	private void loadContent() {
		
		JPanel adminPanel = new AdminPanel(content);
		JPanel icarePanel = new ICarePanel(content);
		JPanel reportPanel = new ReportPanel(content);
		
		content.add(adminPanel, "Admin");
		content.add(icarePanel, "Upload iCare File");
		content.add(reportPanel, "Generate Report");
		
		cLayout.show(content, "Admin");
		
		this.add(content);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
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

	// DEMO
	public static void main(String[] args) {
		AdminInterface admin_gui = new AdminInterface("Admin");
		admin_gui.load();
	}
}
