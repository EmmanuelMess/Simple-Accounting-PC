package com.emmanuelmess.simpleaccounting.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.emmanuelmess.simpleaccounting.Data;
import com.emmanuelmess.simpleaccounting.Main;
import com.emmanuelmess.simpleaccounting.gui.components.BalanceTable;
import com.emmanuelmess.simpleaccounting.gui.components.Item;
import com.emmanuelmess.simpleaccounting.gui.components.Menu;
import com.emmanuelmess.simpleaccounting.gui.components.MenuListener;
import com.emmanuelmess.simpleaccounting.gui.components.Toolbar;

public class MainWindow extends JFrame implements MenuListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BalanceTable table;
	
	public MainWindow(Data<Object> data) {
		super("Simple Accounting");
		
		//System.setProperty("awt.useSystemAAFontSettings","on"); 
		//System.setProperty("swing.aatext", "true"); 
		
		setSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		table = new BalanceTable(data.getData(), data.getColumnNames());

        table.getModel().addTableModelListener(new TableModelListener() {	
        	@Override
	        public void tableChanged(TableModelEvent e) {
        		/*
	            int row = e.getFirstRow();
	            int column = e.getColumn();
	            TableModel model = (TableModel)e.getSource();
	            String columnName = model.getColumnName(column);
	            Object data = model.getValueAt(row, column);
	            */
	        }
        });
	
        Menu m = new Menu(this);
        
        setJMenuBar(m);
        
        setLayout(new BorderLayout());
		
		add(new Toolbar(this), BorderLayout.PAGE_START);
		
		JScrollPane p1 = new JScrollPane(table);
		add(p1, BorderLayout.CENTER);
        
		setVisible(true);
		
		table.postVisibleActions();
	}

	@Override
	public void onClick(Item i) {
		switch(i) {
		case NEW:
			System.out.println("New row");
			table.addRow(new Object []{new Integer(30), "", new Integer(0), new Integer(0), new Integer(0)});
			break;
		case DELETE:
			if(table.getSelectedRow() != -1) {
				System.out.println("Deleting row: " + table.getSelectedRow());
				table.deleteRow(table.getSelectedRow());
			}
			break;
		case UPDATE:
			break;
		case ABOUT:
			break;
		}
		
	}
}