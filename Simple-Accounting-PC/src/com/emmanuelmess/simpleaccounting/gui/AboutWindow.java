package com.emmanuelmess.simpleaccounting.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutWindow  extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AboutWindow() {
		super("Simple Accounting");
		setSize(250, 200);
		JTextArea t = new JTextArea("\n\n\n\nCreated by Emmanuel Messulam");
		add(t);
		setVisible(true);
	}
}
