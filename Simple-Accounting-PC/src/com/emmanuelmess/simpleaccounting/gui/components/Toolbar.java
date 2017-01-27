package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import com.emmanuelmess.simpleaccounting.Main;
import com.emmanuelmess.simpleaccounting.Utils;

public class Toolbar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final String nameNew = " + ", nameDel = " - ", namePrint = "Print";
	private MenuListener listener;
	
	public Toolbar(MenuListener l) {
        super();
        
        this.listener = l;
        
		JButton addB = new JButton(nameNew),
				delB = new JButton(nameDel),
				printB = new JButton(namePrint);
		
		String month = Utils.capitalize(new SimpleDateFormat("MMMMM yyyy").format(new Date()));
		
		String[] monthStrings = {Main.MONTHS[10] + " 2016", month}; 
		
		JComboBox<String> monthsList = new JComboBox<>(monthStrings);
		monthsList.setSelectedIndex(monthsList.getItemCount()-1);
		
		addB.addActionListener(this);
		delB.addActionListener(this);
		printB.addActionListener(this);
		monthsList.addActionListener(this);
		
        add(addB);
        add(delB);
        add(printB);
        add(monthsList);
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
			
			listener.onButtonClick(item);
		} else if (e.getSource() instanceof JComboBox<?>) {
			String text = ((JComboBox<String>)e.getSource()).getSelectedItem().toString();
			for(int i = 0; i < Main.MONTHS.length; i++)
				if(text.substring(0, text.indexOf(" ")).equals(Main.MONTHS[i]))
					listener.onComboBoxClick(i, Integer.parseInt(text.substring(text.indexOf(" ")+1)));
		}
	}
	
}
