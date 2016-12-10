package com.emmanuelmess.simpleaccounting;

import java.sql.SQLException;

import com.emmanuelmess.simpleaccounting.databases.ProcessData;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
import com.emmanuelmess.simpleaccounting.gui.MainWindow;

public class Main {

	public static final String VERSION = "1";
	public static final String VERSION_NAME = "0.0.1 alpha";

	public final static String[] columnNames = {"Date", "Reference", "Credit", "Debit", "Balance"};
		
	public static void main(String[] args) {
		TableGeneral db = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			db = new TableGeneral();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		Object[][] month = ProcessData.digest(db.getMonth(-1,  -1));
		
		Data<Object> d = new Data<>();
		d.setData(month);
		d.setColumNames(columnNames);
		
		new MainWindow(d, db);
	}
	
}