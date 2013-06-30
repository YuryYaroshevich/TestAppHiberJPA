package com.epam.ta.util.datelocalizator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.epam.ta.util.appconstant.TAConstant;

public final class DateLocalizator {
	private static final SimpleDateFormat DEFAULT_DATE_FORMAT;

	static {
		String defaultFormat = TAConstant
				.getConstant(TAConstant.DEFAULT_DATE_FORMAT);
		DEFAULT_DATE_FORMAT = new SimpleDateFormat(defaultFormat);
	}

	private static final Logger logger = Logger
			.getLogger(DateLocalizator.class);

	private DateLocalizator() {
	}

	public static Date parseDate(SimpleDateFormat dateFormat, String dateStr) {
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}

	public static String getDateOfDefaultFormat(Date date) {
		return DEFAULT_DATE_FORMAT.format(date);
	}
}
