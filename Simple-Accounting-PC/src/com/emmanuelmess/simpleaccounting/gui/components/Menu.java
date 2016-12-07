package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu extends JMenuBar implements ActionListener {	
	
	MenuListener listener;
	
	protected final String nameNew = "New";
	protected final String nameDel = "Delete";
	protected final String nameUpdate = "Check for updates";
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
			menuItem = new JMenuItem(nameNew, KeyEvent.VK_T);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription("New row");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			menuItem = new JMenuItem(nameDel, KeyEvent.VK_D);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
					ActionEvent.CTRL_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription("Delete row");
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		{
			menuItem = new JMenuItem(nameUpdate);		
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
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
			case nameUpdate:
				item = Item.UPDATE;
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
