package com.emmanuelmess.simpleaccounting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Formatter;

public class Utils {
	
	public static char decimalSeparator() {
		DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
		return symbols.getDecimalSeparator();
	}
	
	public static Double convert(Object d) {
		if(d == null)
			return new Double(0);
		else return (Double) d;
	}
	
	public static String format(Object s) {
		Formatter f = new Formatter();
		
		if(s instanceof String)
			s = unformat((String) s);
		
		String t = f.format("%,.2f", s).toString();
		f.close();
		
		return t;
	}
	
	public static Double unformat(String data) {
		if(data.contains(String.valueOf(decimalSeparator())) && data.length() - data.lastIndexOf(decimalSeparator()) > 2)
			data = data.substring(0, data.lastIndexOf(decimalSeparator()));
		
		NumberFormat format = NumberFormat.getInstance();
	    Number number = null;
		try {
			number = format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return number.doubleValue();
	}
}
