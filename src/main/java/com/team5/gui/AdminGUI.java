package com.team5.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class AdminGUI {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu help;

	public AdminGUI(String title) {
		frame = new JFrame(title);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes both width and height
		menuBar = new JMenuBar();
		file = new JMenu("file");
		help = new JMenu("help");
	}

	private void loadContent() {
		// Adding the menu items for each menu
		file.add(new JSeparator());
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);		
		
		// Add all the JMenus to the menu bar
		menuBar.add(file);
		menuBar.add(help);
		
		// Setting the menu bar on screen
		frame.setJMenuBar(menuBar);
		frame.validate();
	}

	public void load() {
		this.frame.setVisible(true);
		this.loadContent();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		AdminGUI gui = new AdminGUI("Admin Interface");
		gui.load();
	}
}
