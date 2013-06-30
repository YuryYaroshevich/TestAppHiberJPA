package com.epam.ta.util.appconstant;

import java.util.ResourceBundle;

public final class TAConstant {
	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("com.epam.ta.util.appconstant.constant");

	public static final String DEFAULT_DATE_FORMAT = "default.date.format";
	
	private TAConstant() {
	}
	
	public static String getConstant(String key) {
		return bundle.getString(key);
	}
}
