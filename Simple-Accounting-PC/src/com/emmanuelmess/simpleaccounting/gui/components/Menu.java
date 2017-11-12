package com.emmanuelmess.simpleaccounting.gui.components;

import com.emmanuelmess.simpleaccounting.Localization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu extends JMenuBar implements ActionListener {	
	
	private static final long serialVersionUID = 1L;

	private MenuListener listener;

	public Menu(MenuListener l) {
		super();

		listener = l;

		String nameNew = Localization.getString("newRow");
		String nameDel = Localization.getString("deleteRow");
		String nameGetUpdates = Localization.getString("checkUpdates");
		String namePrint = Localization.getString("print");
		String nameAbout = Localization.getString("about");

		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu(Localization.getString("file"));
		menu.setMnemonic(KeyEvent.VK_F);
		add(menu);

		{
			menuItem = new DifferentiableJMenuItem(nameNew, KeyEvent.VK_N, Item.NEW.ordinal());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					InputEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(nameNew);
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			menuItem = new DifferentiableJMenuItem(nameDel, KeyEvent.VK_D, Item.DELETE.ordinal());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
					InputEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(nameDel);
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			menuItem = new DifferentiableJMenuItem(namePrint, KeyEvent.VK_P, Item.PRINT.ordinal());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
					InputEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(namePrint);
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}

		menu = new JMenu(Localization.getString("help"));
		menu.setMnemonic(KeyEvent.VK_H);
		{
			/*
			menuItem = new JMenuItem(nameUpdate, Item.UPDATES.ordinal());
			menuItem.addActionListener(this);
			menu.add(menuItem);
			*/
			menuItem = new DifferentiableJMenuItem(nameAbout, Item.ABOUT.ordinal());
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}
		
		add(menu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JMenuItem) {
			DifferentiableJMenuItem menuItem = (DifferentiableJMenuItem) e.getSource();
			Item item = Item.values()[menuItem.id];

			listener.onButtonClick(item);
		}
	}
	
}
