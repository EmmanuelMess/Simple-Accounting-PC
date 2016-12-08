package com.emmanuelmess.simpleaccounting;

public class Utils {
	public static Double convert(Object d) {
		if(d == null)
			return new Double(0);
		else return (Double) d;
	}
}
