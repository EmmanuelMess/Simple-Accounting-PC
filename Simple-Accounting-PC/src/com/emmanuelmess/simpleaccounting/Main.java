package com.emmanuelmess.simpleaccounting;

import java.sql.SQLException;

import com.emmanuelmess.simpleaccounting.databases.ProcessData;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;
import com.emmanuelmess.simpleaccounting.gui.MainWindow;

public class Main {

	public static final String VERSION = "1";
	public static final String VERSION_NAME = "0.1 alpha";
		
	public static void main(String[] args) {
		if (args.length != 2) {
			Localization.createInstance("en", "US");
		} else {
			Localization.createInstance(args[0], args[1]);
		}

		TableGeneral db;

		try {
			Class.forName("org.sqlite.JDBC");
			db = new TableGeneral();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			return;
		}
		
		Object[][] month = ProcessData.digest(db.getMonth(-1,  -1));
		
		Data<Object> d = new Data<>();
		d.setData(month);
		d.setColumNames(Localization.getInstance().COLUMNS);
		
		new MainWindow(d, db);
	}
	
}