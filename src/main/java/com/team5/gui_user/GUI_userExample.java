package gui_user;

import javax.swing.*;

public class GUI_userExample {
	private void prepareGUI_user(){    
		JFrame frame=new JFrame("User Interface");
		JLabel label = new JLabel();		
		label.setText("File:");
		label.setBounds(10, 10, 100, 100);
		JButton textfield= new JButton("Submit File");
		textfield.setBounds(110, 50, 130, 30);
		frame.add(textfield);
		frame.add(label);
		frame.setSize(500,500);    
		frame.setLayout(null);    
		frame.setVisible(true);    
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

	public GUI_userExample(){
		prepareGUI_user();
	}         
	
	
		public static void main(String[] args) {    
		    GUI_userExample gui_userexample = new GUI_userExample();    
		}    
 }