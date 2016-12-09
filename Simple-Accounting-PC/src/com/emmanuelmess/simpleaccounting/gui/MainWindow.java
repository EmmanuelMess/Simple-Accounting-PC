package com.emmanuelmess.simpleaccounting.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.emmanuelmess.simpleaccounting.Data;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
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
	private TableGeneral db;
	
	public MainWindow(Data<Object> data, TableGeneral c) {
		super("Simple Accounting");
		
		//System.setProperty("awt.useSystemAAFontSettings","on"); 
		//System.setProperty("swing.aatext", "true"); 
		
		db = c;
		
		setSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		table = new BalanceTable(data.getData(), data.getColumnNames());

        table.getModel().addTableModelListener(new TableModelListener() {	
        	@Override
	        public void tableChanged(TableModelEvent e) {
	            int row = e.getFirstRow();
	            int column = e.getColumn();
	            System.out.println("Table changed: " + row + "x" + column + "!");
	            if(row != -1 && column != -1 && column != 4) {
		            TableModel model = (TableModel)e.getSource();
		            Object data = model.getValueAt(row, column);
		            db.update(row, new String[] {TableGeneral.COLUMNS[column]}, new Object[] {data});
		            
		            if(column == 2 || column == 3) 
		            	recalculateBalance(row);
	            }
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

	private void recalculateBalance(int row) {
		TableModel model = table.getModel();
		
    	BigDecimal balance = new BigDecimal(0);
    	if(row != 0) 
    		balance = balance.add(new BigDecimal(((String) model.getValueAt(row-1, 4)).substring(2)));
    	balance = balance.add(new BigDecimal((Double) model.getValueAt(row, 2)));
    	balance = balance.subtract(new BigDecimal((Double) model.getValueAt(row, 3)));
    	model.setValueAt("$ " + balance.toPlainString(), row, 4);
    	
    	if(model.getRowCount() > row+1)
    		recalculateBalance(row+1);
	}
	
	@Override
	public void onClick(Item i) {
		switch(i) {
		case NEW:
			System.out.println("New row");
			
			Date temp = new Date();
			Integer day = Integer.valueOf((new SimpleDateFormat("dd")).format(temp)),
					month = Integer.valueOf((new SimpleDateFormat("MM")).format(temp))-1,
					year = Integer.valueOf((new SimpleDateFormat("YYYY")).format(temp));
			table.addRow(new Object []{day, "", new Double(0.0d), new Double(0.0d), "$ 0.0"});
			TableModel model = table.getModel();
			db.addNew(day, month, year);
			recalculateBalance(model.getRowCount()-1);
			break;
		case DELETE:
			if(table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				System.out.println("Deleting row: " + row);
				table.deleteRow(row);
				db.delete(row);
			}
			break;
		case GET_UPDATES:
			break;
		case PRINT:
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new Print(db));
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