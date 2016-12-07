package com.emmanuelmess.simpleaccounting;

import java.util.ArrayList;

import com.emmanuelmess.simpleaccounting.gui.MainWindow;

public class Main {

	public static final String VERSION = "1";
	
	public final static String[] columnNames = {"Date", "Reference", "Credit", "Debit", "Balance"};
	
	private static MainWindow window = null;
	
	public static void main(String[] args) {		
		ArrayList<Object[]> data = new ArrayList<>();
		
		data.add(new Object []{new Integer(01), "Salary", new Integer(5), new Integer(0), new Integer(5)});
		data.add(new Object []{new Integer(03), "Thing", new Integer(0), new Integer(2), new Integer(3)});
		data.add(new Object []{new Integer(12), "Supermarket", new Integer(0), new Integer(1), new Integer(2)});
		data.add(new Object []{new Integer(24), "Services", new Integer(0), new Integer(1), new Integer(1)});
		data.add(new Object []{new Integer(30), "Other stuff", new Integer(0), new Integer(2), new Integer(-1)});

		Data<Object> d = new Data<>();
		d.setData(data);
		d.setColumNames(columnNames);
		
		window = new MainWindow(d);
	}

}