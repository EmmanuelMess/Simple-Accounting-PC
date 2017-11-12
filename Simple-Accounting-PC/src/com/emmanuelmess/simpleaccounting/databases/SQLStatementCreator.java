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
			statement.append(s).append(", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(") VALUES (");
		for (Object elem : data) 
			statement.append(c(elem)).append(", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(");");
		System.out.println(statement.toString());
		return statement.toString();
	}
	
	public static String select(String table, String[] columns) {
		StringBuilder statement = new StringBuilder();
		statement.append("SELECT ");
		for(String s : columns)
			statement.append(s).append(", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(" FROM ");
		statement.append(table);
		statement.append(";");
		System.out.println(statement.toString());

		return statement.toString();
	}
	
	public static String delete(String table, String check) {
		StringBuilder statement = new StringBuilder();
		statement.append("DELETE FROM ");
		statement.append(table);
		statement.append(" WHERE ");
		statement.append(check);
		statement.append(";");
		System.out.println(statement.toString());

		return statement.toString();
	}
	
	public static String update(String table, String[] columns, Object[] data, String check) {
		if(columns.length != data.length)
			throw new IllegalArgumentException();
		
		StringBuilder statement = new StringBuilder();
		statement.append("UPDATE ");
		statement.append(table);
		statement.append(" SET ");
		for(int i = 0; i < columns.length; i++)
			statement.append(columns[i]).append("=").append(c(data[i])).append(", ");
		statement.delete(statement.lastIndexOf(", "), statement.length());
		statement.append(" WHERE ");
		statement.append(check);
		statement.append(";");
		System.out.println(statement.toString());

		return statement.toString();
	}
	
	private static Object c(Object s) {
		if(s instanceof String) {
			return "'" + s + "'";
		}
		
		return s;
	}
}
