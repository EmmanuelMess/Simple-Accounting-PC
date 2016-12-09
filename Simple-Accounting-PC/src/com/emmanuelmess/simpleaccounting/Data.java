package com.emmanuelmess.simpleaccounting;

public class Data<T> {
	
	String[] columnNames;
	T[][] data;
	
	public void setColumNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
	
	public void setData(T[][] data) {
		this.data = data;
	}
	
	public T[][] getData() {
		return data;
	}
}
