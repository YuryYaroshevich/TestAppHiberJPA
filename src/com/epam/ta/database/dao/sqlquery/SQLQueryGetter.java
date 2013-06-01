package com.epam.ta.database.dao.sqlquery;

import java.util.ResourceBundle;

public final class SQLQueryGetter {
	private static final String SQL_PROPERTY_FILE = "com."
			+ "epam.ta.database.dao.sqlquery.sqlquery";

	private static final ResourceBundle bundle = ResourceBundle
			.getBundle(SQL_PROPERTY_FILE);

	/* Keys for reading SQL queries from property file. */
	public static final String FETCH_BY_ID_QUERY = "FETCH_BY_ID_QUERY";
	public static final String GET_LIST_QUERY = "GET_LIST_QUERY";
	public static final String DELETE_NEWS_QUERY = "DELETE_NEWS_QUERY";
	public static final String UPDATE_NEWS_QUERY = "UPDATE_NEWS_QUERY";
	public static final String ADD_NEWS_QUERY = "ADD_NEWS_QUERY";
	public static final String GET_ID_OF_NEW_NEWS_QUERY = "GET_ID_OF_NEW"
			+ "_NEWS_QUERY";
	public static final String DELETE_NEWS_GROUP_QUERY = "DELETE_NEWS_"
			+ "GROUP_QUERY";

	private SQLQueryGetter() {
	}

	public static String getSQlQuery(String key) {
		return bundle.getString(key);
	}

	/* For group deleting on News list page */
	public static String deleteGroupQuery(String[] selectedNews) {
		StringBuilder query = new StringBuilder(
				getSQlQuery(DELETE_NEWS_GROUP_QUERY));
		int len = selectedNews.length;
		for (int i = 0; i < len; i++) {
			query.append(selectedNews[i]);
			if (i < len - 1) {
				query.append(",");
			}
		}
		query.append(")");
		return query.toString();
	}
}
