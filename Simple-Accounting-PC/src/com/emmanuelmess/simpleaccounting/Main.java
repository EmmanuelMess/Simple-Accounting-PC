package com.emmanuelmess.simpleaccounting;

import static com.emmanuelmess.simpleaccounting.Utils.convert;

import java.sql.SQLException;
import java.util.ArrayList;

import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
import com.emmanuelmess.simpleaccounting.gui.MainWindow;

public class Main {

	public static final String VERSION = "1";
	public static final String VERSION_NAME = "0.0.1 alpha";

	public final static String[] columnNames = {"Date", "Reference", "Credit", "Debit", "Balance"};
	
	private static MainWindow window = null;
	
	public static void main(String[] args) {
		ArrayList<Object[]> data = new ArrayList<>();
		TableGeneral db = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			db = new TableGeneral();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		Object[][] month = db.getMonth(-1,  -1);
		for(int i = 0; i < month.length; i++) {
			Object[] elem = month[i];
			Double prevBalance = i > 0? Double.parseDouble(((String) data.get(i-1)[4]).substring(2)) : 0;
			
			data.add(new Object[] {elem[0], elem[1], convert(elem[2]), convert(elem[3]), 
					"$ " + (prevBalance + convert(elem[2]) - convert(elem[3]))});
		}
		
		Data<Object> d = new Data<>();
		d.setData(data);
		d.setColumNames(columnNames);
		
		window = new MainWindow(d, db);
	}
	
}