package com.emmanuelmess.simpleaccounting.databases;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseGeneral extends Database {
	public static final String[] COLUMNS = new String[] { "DATE", "REFERENCE", "CREDIT", "DEBT", "MONTH", "YEAR"};
	public static final String NUMBER_COLUMN = "NUMBER";

	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "ACCOUNTING";
	private static final String TABLE_CREATE = String.format("CREATE TABLE %1$s" +
			" (%2$s INT, %3$s INT, %4$s TEXT, %5$s REAL, %6$s REAL, %7$s INT, %8$s INT);",
			TABLE_NAME, NUMBER_COLUMN, COLUMNS[0], COLUMNS[1], COLUMNS[2], COLUMNS[3], COLUMNS[4], COLUMNS[5]);
	
	public Connection getConnection() throws SQLException {
		return super.getConnection(TABLE_NAME);
	}
	
	public int add(int date, String ref, int credit, int debit) throws SQLException {
	    String query = String.format("INSERT %1$d, %2$s, %3$d, %4$d INTO %5$s",
	    		date, ref, credit, debit, TABLE_NAME);

	    try (java.sql.Statement stmt = getConnection().createStatement()) {
	        return stmt.executeUpdate(query);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return -1;
	}
	
}
