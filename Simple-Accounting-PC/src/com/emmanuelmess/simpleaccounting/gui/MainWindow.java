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
import com.emmanuelmess.simpleaccounting.Utils;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
import com.emmanuelmess.simpleaccounting.gui.components.BalanceTable;
import com.emmanuelmess.simpleaccounting.gui.components.BalanceTable.BalanceTableModel;
import com.emmanuelmess.simpleaccounting.gui.components.Item;
import com.emmanuelmess.simpleaccounting.gui.components.Menu;
import com.emmanuelmess.simpleaccounting.gui.components.MenuListener;
import com.emmanuelmess.simpleaccounting.gui.components.Toolbar;
import com.emmanuelmess.simpleaccounting.print.Print;

public class MainWindow extends JFrame implements MenuListener {
	
	private static final long serialVersionUID = 1L;
	private BalanceTable table;
	private TableGeneral db;
	
	public MainWindow(Data<Object> data, TableGeneral c) {
		super("Simple Accounting");
		
		db = c;
		
		setSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);//Centers
		      
		addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                db.close();
                System.exit(0);
            }
        });
		        
		table = new BalanceTable(data.getData(), data.getColumnNames());

        table.getModel().addTableModelListener(new TableModelListener() {	
        	@Override
	        public void tableChanged(TableModelEvent e) {
	            int row = e.getFirstRow();
	            int column = e.getColumn();
	            System.out.println("Table changed: " + row + "x" + column + "!");
	            if(row != -1 && column != -1 && column != 4) {
		            BalanceTable.BalanceTableModel model = (BalanceTable.BalanceTableModel)e.getSource();
		            Object data = model.getValueAt(row, column);
		            db.update(row, new String[] {TableGeneral.COLUMNS[column]}, 
		            		new Object[] {(column == 2 || column == 3? Utils.unformat(data.toString()) : data)});
		            
		            if(column == 2 || column == 3) {
		            	String s = Utils.format(data);
		            	if(s != data)
		            		model.setValueAt(s, row, column, false);
		            	recalculateBalance(row);
		            }
	            }
	        }
        });
        

	        
        setJMenuBar(new Menu(this));
        
        setLayout(new BorderLayout());
		add(new Toolbar(this), BorderLayout.PAGE_START);
		
		JScrollPane p1 = new JScrollPane(table);
		add(p1, BorderLayout.CENTER);
        
		setVisible(true);
		
		table.postVisibleActions();
	}

	private void recalculateBalance(int row) {
		TableModel model = table.getModel();
		
    	BigDecimal balance = BigDecimal.ZERO;
    	if(row != 0) 
    		balance = balance.add(new BigDecimal(Utils.unformat((model.getValueAt(row-1, 4).toString()).substring(2))));
    	
    	balance = balance.add(new BigDecimal(Utils.unformat(model.getValueAt(row, 2).toString())))
    					.subtract(new BigDecimal(Utils.unformat(model.getValueAt(row, 3).toString())));
    	model.setValueAt("$ " + Utils.format(balance), row, 4);
    	
    	if(model.getRowCount() > row+1)
    		recalculateBalance(row+1);
	}
	
	@Override
	public void onClick(Item i) {
		switch(i) {
		case NEW:
			System.out.println("New row");
			
			BalanceTableModel model = table.getModel();
			Date temp = new Date();
			Integer day = Integer.valueOf((new SimpleDateFormat("dd")).format(temp)),
					month = Integer.valueOf((new SimpleDateFormat("MM")).format(temp))-1,
					year = Integer.valueOf((new SimpleDateFormat("YYYY")).format(temp));
			
			String formattedZero = Utils.format("0.0");
			
			model.addRow(new Object []{String.format("%02d", day), "", formattedZero, formattedZero,  "$ " + formattedZero});
			db.addNew(day, month, year);
			recalculateBalance(model.getRowCount()-1);
			break;
		case DELETE:
			if(table.getSelectedRow() != -1) {
				int[] rows = table.getSelectedRows();
				int startRow = table.getSelectedRow(), 
						lastRow = table.getSelectedRows() [rows.length-1];
				
				System.out.println("Deleting rows inclusively between: " + startRow + " AND " + lastRow);
				db.delete(startRow, lastRow);
				
				if(table.getSelectedRows().length > 1) 
					table.getModel().deleteRows(startRow, lastRow+1);
				 else 
					table.getModel().deleteRow(table.getSelectedRow());
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