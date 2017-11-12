package com.emmanuelmess.simpleaccounting;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is a singleton for getting translated strings
 */
public class Localization {

	private static Localization instance;

	public static void createInstance(String language, String country) {
		if(instance != null) throw new IllegalStateException("Instance already exists!");
		instance = new Localization(language, country);
	}

	public static Localization getInstance() {
		if(instance == null) throw new NullPointerException("Call createInstance() first!");
		return instance;
	}

	public final String[] COLUMNS;
	public final String[] MONTHS = new String[12];

	private final ResourceBundle translations;

	private Localization(String language, String country) {
		Locale currentLocale = new Locale(language, country);
		translations = ResourceBundle.getBundle("SimpleAccountingPC", currentLocale);

		COLUMNS = new String[] {translations.getString("date"),
				translations.getString("reference"), translations.getString("credit"),
				translations.getString("debit"), translations.getString("balance")};

		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; months[i].isEmpty(); i++) {
			MONTHS[i] = Utils.capitalize(months[i]);
		}

	}

	public static String getString(String key) {
		return getInstance().translations.getString(key);
	}

}
