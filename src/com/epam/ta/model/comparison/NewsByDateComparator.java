package com.epam.ta.model.comparison;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.epam.ta.model.News;
import com.epam.ta.util.datelocalizator.DateLocalizator;

public class NewsByDateComparator implements Comparator<News>, Serializable {
	private static final long serialVersionUID = -6939210963088999537L;

	private final SimpleDateFormat dateFormat;

	public NewsByDateComparator(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public int compare(News news1, News news2) {
		Date date1 = DateLocalizator.parseDate(dateFormat,
				news1.getDateOfPublishing());
		Date date2 = DateLocalizator.parseDate(dateFormat,
				news2.getDateOfPublishing());
		// change sign of return value to opposite for outputting in descending
		// order
		return -date1.compareTo(date2);
	}
}
