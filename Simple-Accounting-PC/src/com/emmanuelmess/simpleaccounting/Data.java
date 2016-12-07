package com.emmanuelmess.simpleaccounting;

import java.util.ArrayList;

public class Data<T> {
	
	String[] columnNames;
	ArrayList<T[]> data;
	
	public void setColumNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
	
	public void setData(ArrayList<T[]> data) {
		this.data = data;
	}
	
	public ArrayList<T[]> getData() {
		return data;
	}
}
