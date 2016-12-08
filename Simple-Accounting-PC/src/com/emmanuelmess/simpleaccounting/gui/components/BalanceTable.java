package com.emmanuelmess.simpleaccounting.gui.components;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.emmanuelmess.simpleaccounting.Main;

public class BalanceTable extends JTable {

	private static final long serialVersionUID = 1L;

	public BalanceTable(ArrayList<Object[]> data, String[] columnNames) {
		super(new BalanceTableModel(columnNames, data));
		
	}
	
	public void postVisibleActions() {
		centerCells();
		resizeColumns();
	}
	
    public void addRow(Object[] row) {
    	((BalanceTableModel) getModel()).addRow(row);
    }
    
    public void deleteRow(int row) {
    	((BalanceTableModel) getModel()).deleteRow(row);
    }
	
	private void centerCells() {
		setPreferredScrollableViewportSize(new Dimension(500, 70));
        setFillsViewportHeight(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int x = 0; x < Main.columnNames.length; x++) {
        	if(x == 1) continue;
        	getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
	}
	
	//SUMS 1
	float[] columnWidthPercentage = {0.05f, 0.55f, 0.1f, 0.1f, 0.1f, 0.1f};

	private void resizeColumns() {
	    int tW = getParent().getWidth();
	    TableColumn column;
	    TableColumnModel jTableColumnModel = getColumnModel();
	    int cantCols = jTableColumnModel.getColumnCount();
	    for (int i = 0; i < cantCols; i++) {
	        column = jTableColumnModel.getColumn(i);
	        int pWidth = Math.round(columnWidthPercentage[i] * tW);
	        column.setPreferredWidth(pWidth);
	    }
	}
	
}

class BalanceTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
    private ArrayList<Object[]> data;
    
    BalanceTableModel(String[] col, ArrayList<Object[]> data) {
    	this.columnNames = col;
    	this.data = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return !(col == 4);

    }

    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public void addRow(Object[] row) {
    	data.add(row);
    	fireTableDataChanged();
    }
    
    public void deleteRow(int row) {
    	data.remove(row);
    	fireTableDataChanged();
    }
}
