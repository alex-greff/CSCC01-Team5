package com.team5.driver;

import com.team5.gui.GUIManager;

/**
 * The driver class.
 */
public class Driver {
  // Main entry point
  public static void main(String[] args) {
    GUIManager admin_gui = new GUIManager("Admin");
		admin_gui.load();
  }
}
