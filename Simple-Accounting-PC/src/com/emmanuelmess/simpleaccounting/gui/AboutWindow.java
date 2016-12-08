package com.emmanuelmess.simpleaccounting.gui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.emmanuelmess.simpleaccounting.Main;

public class AboutWindow  extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AboutWindow() {
		super("Simple Accounting");
		setSize(250, 200);
		JTextArea t = new JTextArea("\n\n\nCreated by Emmanuel Messulam\n\nVersion: " + Main.VERSION_NAME);
		add(t);
		setVisible(true);
	}
}
