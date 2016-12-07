package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import com.emmanuelmess.simpleaccounting.gui.components.Item;

public class Toolbar extends JToolBar implements ActionListener {
    
	private final String nameNew = " + ", nameDel = " - ";
	private MenuListener listener;
	
	public Toolbar(MenuListener l) {
        super();
        
        this.listener = l;
        
		JButton addB = new JButton(" + ");
		JButton delB = new JButton(" - ");
		
		addB.addActionListener(this);
		delB.addActionListener(this);
		
        add(addB);
        add(delB);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton menuItem = (JButton) e.getSource();
			Item item;
			
			switch(menuItem.getText()) {
			case nameNew:
				item = Item.NEW;
				break;
			case nameDel:
				item = Item.DELETE;
				break;
				default:
					throw new IllegalStateException();
			}
			
			listener.onClick(item);
		}
	}
	
}
