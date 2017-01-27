package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu extends JMenuBar implements ActionListener {	
	
	private static final long serialVersionUID = 1L;

	MenuListener listener;
	
	protected final String nameNew = "New row";
	protected final String nameDel = "Delete row";
	protected final String nameGetUpdates = "Check for updates";
	protected final String namePrint = "Print...";
	protected final String nameAbout = "About...";
	
	public Menu(MenuListener l) {
		super();
		
		listener = l;
		
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		add(menu);

		{
			menuItem = new JMenuItem(nameNew, KeyEvent.VK_N);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(nameNew);
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			menuItem = new JMenuItem(nameDel, KeyEvent.VK_D);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
					ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(nameDel);
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			menuItem = new JMenuItem(namePrint, KeyEvent.VK_P);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
					ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(namePrint);
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		{
			/*
			menuItem = new JMenuItem(nameUpdate);		
			menuItem.addActionListener(this);
			menu.add(menuItem);
			*/
			menuItem = new JMenuItem(nameAbout);
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}
		
		add(menu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JMenuItem) {
			JMenuItem menuItem = (JMenuItem) e.getSource();
			Item item;
			
			switch(menuItem.getText()) {
			case nameNew:
				item = Item.NEW;
				break;
			case nameDel:
				item = Item.DELETE;
				break;
			case nameGetUpdates:
				item = Item.GET_UPDATES;
				break;
			case namePrint:
				item = Item.PRINT;
				break;
			case nameAbout:
				item = Item.ABOUT;
				break;
				default:
					throw new IllegalStateException();
			}
			
			
			listener.onClick(item);
		}
	}
	
}
