package com.emmanuelmess.simpleaccounting;

import java.sql.SQLException;
import java.text.DateFormatSymbols;

import com.emmanuelmess.simpleaccounting.databases.ProcessData;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
import com.emmanuelmess.simpleaccounting.gui.MainWindow;

public class Main {

	public static final String VERSION = "1";
	public static final String VERSION_NAME = "0.1 alpha";

	public final static String[] COLUMNS = {"Date", "Reference", "Credit", "Debit", "Balance"};
	public final static String[] MONTHS = new String[12];
		
	public static void main(String[] args) {
		TableGeneral db = null;
		
		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; months[i] != ""; i++)
			MONTHS[i] = Utils.capitalize(months[i]);
		
		try {
			Class.forName("org.sqlite.JDBC");
			db = new TableGeneral();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		Object[][] month = ProcessData.digest(db.getMonth(-1,  -1));
		
		Data<Object> d = new Data<>();
		d.setData(month);
		d.setColumNames(COLUMNS);
		
		new MainWindow(d, db);
	}
	
}