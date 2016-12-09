package com.emmanuelmess.simpleaccounting.databases;

import static com.emmanuelmess.simpleaccounting.Utils.convert;

import java.util.ArrayList;

import com.emmanuelmess.simpleaccounting.Main;

public class ProcessData {
	public static Object[][] digest(Object[][] month) {
		ArrayList<Object[]> data = new ArrayList<>();

		for(int i = 0; i < month.length; i++) {
			Object[] elem = month[i];
			Double prevBalance = i > 0? Double.parseDouble(((String) data.get(i-1)[4]).substring(2)) : 0;
			
			data.add(new Object[] {elem[0], elem[1], convert(elem[2]), convert(elem[3]), 
					"$ " + (prevBalance + convert(elem[2]) - convert(elem[3]))});
		}
		
		return data.toArray(new Object[data.size()][Main.columnNames.length]);
	}
}
