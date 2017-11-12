package com.emmanuelmess.simpleaccounting.gui.components;

import javax.swing.*;

public class DifferentiableJMenuItem extends JMenuItem {

	public final int id;

	public DifferentiableJMenuItem(String text, int id) {
		super(text);
		this.id = id;
	}

	public DifferentiableJMenuItem(String text, int keyEvent, int id) {
		super(text, keyEvent);
		this.id = id;
	}

}
