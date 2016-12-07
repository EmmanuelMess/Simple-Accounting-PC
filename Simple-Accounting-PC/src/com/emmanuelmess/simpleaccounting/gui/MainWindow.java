package com.emmanuelmess.simpleaccounting.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.emmanuelmess.simpleaccounting.Data;
import com.emmanuelmess.simpleaccounting.gui.components.BalanceTable;
import com.emmanuelmess.simpleaccounting.gui.components.Item;
import com.emmanuelmess.simpleaccounting.gui.components.Menu;
import com.emmanuelmess.simpleaccounting.gui.components.MenuListener;
import com.emmanuelmess.simpleaccounting.gui.components.Toolbar;
import com.emmanuelmess.simpleaccounting.print.Print;

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
        		System.out.println("Table changed!");
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
			Integer day = Integer.valueOf((new SimpleDateFormat("dd")).format(new Date()));
			table.addRow(new Object []{day, "", new Integer(0), new Integer(0), new Integer(0)});
			
			break;
		case DELETE:
			if(table.getSelectedRow() != -1) {
				System.out.println("Deleting row: " + table.getSelectedRow());
				table.deleteRow(table.getSelectedRow());
			}
			break;
		case UPDATE:
			break;
		case PRINT:
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new Print());
			if (job.printDialog()) {
			    try {
			        job.print();
			    } catch (PrinterException e) {
			    	if(e.getMessage() != null)
			    		JOptionPane.showMessageDialog(this,
			    				"An error occurred during print: \n" + e.getLocalizedMessage(),
			    				"Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			break;
		case ABOUT:
			new AboutWindow();
			break;
		}
		
	}
}