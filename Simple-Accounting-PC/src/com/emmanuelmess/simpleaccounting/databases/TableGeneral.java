package com.emmanuelmess.simpleaccounting.databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableGeneral extends Database {
	
	public static final String[] COLUMNS = new String[] { "DATE", "REFERENCE", "CREDIT", "DEBT", "MONTH", "YEAR"};
	public static final String NUMBER_COLUMN = "ID";

	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "ACCOUNTING";
	private static final String TABLE_CREATE = String.format("CREATE TABLE IF NOT EXISTS %1$s" +
			" (%2$s INTEGER PRIMARY KEY, %3$s INT, %4$s TEXT, %5$s REAL, %6$s REAL, %7$s INT, %8$s INT)",
			TABLE_NAME, NUMBER_COLUMN, COLUMNS[0], COLUMNS[1], COLUMNS[2], COLUMNS[3], COLUMNS[4], COLUMNS[5]);
	
	private String upd;

	public TableGeneral() throws SQLException {
		super();
		super.createTable(TABLE_CREATE, con);
        upd = "UPDATE " + TABLE_NAME + " SET %1$s = ? WHERE " + NUMBER_COLUMN + " = ?;";
	}
	
	public void addNew(int date, int month, int year) {
		try (Statement stat = con.createStatement()) {
			stat.executeUpdate(SQLStatementCreator.insert(TABLE_NAME,
					new String[] {COLUMNS[0], COLUMNS[4], COLUMNS[5]} , 
					new Object[]{new Integer(date), new Integer(month), new Integer(year)}));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(int row, String columns[], Object[] data) {
		if(columns.length != data.length)
			throw new IllegalArgumentException();
		
		try (Statement stat = con.createStatement()){	    
			for(int i = 0; i < columns.length; i++) {
				PreparedStatement preparedUpd = con.prepareStatement(String.format(upd, columns[i]));
				preparedUpd.setObject(1, data[i]);
				preparedUpd.setInt(2, row);
				preparedUpd.addBatch();
				
		        con.setAutoCommit(false);
		        preparedUpd.executeBatch();
		        con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int row) {
		try (Statement stat = con.createStatement()){	        
			stat.executeUpdate(SQLStatementCreator.delete(TABLE_NAME, NUMBER_COLUMN + "=" + row));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * month and year are NOT used
	 */
	public Object[][] getMonth(int month, int year) {
		ArrayList<Object[]> data = new ArrayList<>();
		
		try (Statement stat = con.createStatement(); 
				ResultSet rs = stat.executeQuery(
						SQLStatementCreator.select(TABLE_NAME, 
								new String[] {COLUMNS[0], COLUMNS[1], COLUMNS[2], COLUMNS[3]}))) {
			while(rs.next()) { 
				data.add(new Object[] {rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4)}); //COLUMNS START IN 1
			}
			
			System.out.println("Feched month: " + year + "-" + month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data.toArray(new Object[data.size()][3]);
	}
	
}
