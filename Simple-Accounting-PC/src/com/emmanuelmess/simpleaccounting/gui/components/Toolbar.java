package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {
    
	private final String nameNew = " + ", nameDel = " - ", namePrint = "Print";
	private MenuListener listener;
	
	public Toolbar(MenuListener l) {
        super();
        
        this.listener = l;
        
		JButton addB = new JButton(nameNew),
				delB = new JButton(nameDel),
				printB = new JButton(namePrint);
		
		addB.addActionListener(this);
		delB.addActionListener(this);
		printB.addActionListener(this);
		
        add(addB);
        add(delB);
        add(printB);
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
			case namePrint:
				item = Item.PRINT;
				break;
			default:
				throw new IllegalStateException();
			}
			
			listener.onClick(item);
		}
	}
	
}
