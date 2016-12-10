package com.emmanuelmess.simpleaccounting.databases;

import static com.emmanuelmess.simpleaccounting.Utils.convert;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.emmanuelmess.simpleaccounting.Main;
import com.emmanuelmess.simpleaccounting.Utils;

public class ProcessData {
	public static Object[][] digest(Object[][] month) {
		ArrayList<Object[]> data = new ArrayList<>();
    	BigDecimal balance = BigDecimal.ZERO;
		
		for(int i = 0; i < month.length; i++) {
			Object[] elem = month[i];
	    	
	    	balance = balance.add(new BigDecimal(convert(elem[2])))
	    					.subtract(new BigDecimal(convert(elem[3])));
			
			data.add(new Object[] {String.format("%02d", elem[0]), elem[1], 
					Utils.format(convert(elem[2])), 
					Utils.format(convert(elem[3])), 
					"$ " + Utils.format(balance)});
		}
		
		return data.toArray(new Object[data.size()][Main.columnNames.length]);
	}
}
