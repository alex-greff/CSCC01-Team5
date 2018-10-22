package com.team5.gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class AdminInterface extends JFrame {

	public AdminInterface(String title) {
		super(title);
		this.setSize(500, 500); // Set basic size for Interface
	}

	private void loadContent() {
		this.validate();
	}

	public void load() {
		this.setVisible(true);
		this.loadContent();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		AdminInterface gui = new AdminInterface("Admin Interface");
		gui.load();
	}
}
