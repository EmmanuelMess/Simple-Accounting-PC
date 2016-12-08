package com.emmanuelmess.simpleaccounting.databases;

public class SQLStatementCreator {

	public static String insert(String table, String[] columns, Object[] data) {
		if(columns.length != data.length)
			throw new IllegalArgumentException();
		
		StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO ");
		statement.append(table);
		statement.append(" (");
		for(String s : columns)
			statement.append(s + ", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(") VALUES (");
		for (Object elem : data) 
			statement.append(elem + ", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(");");
		return statement.toString();
	}
	
	public static String select(String table, String[] columns) {
		StringBuilder statement = new StringBuilder();
		statement.append("SELECT ");
		for(String s : columns)
			statement.append(s + ", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(" FROM ");
		statement.append(table);
		statement.append(";");
		return statement.toString();
	}
}
