package com.epam.ta.database.dao;

import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.ADD_NEWS_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.DELETE_NEWS_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.FETCH_BY_ID_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.GET_ID_OF_NEW_NEWS_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.GET_LIST_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.UPDATE_NEWS_QUERY;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.deleteGroupQuery;
import static com.epam.ta.database.dao.sqlquery.SQLQueryGetter.getSQlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.epam.ta.database.dao.exception.NewsDAOException;
import com.epam.ta.database.dao.sqlquery.SQLQueryGetter;
import com.epam.ta.database.pool.ConnectionPool;
import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsDAOJDBC extends AbstractDAOJDBC implements INewsDAO {
	private static final INewsDAO dao = new NewsDAOJDBC();

	private static ConnectionPool pool;

	private static final int NEWS_ID_COLUMN_INDEX = 1;
	private static final int TITLE_COLUMN_INDEX = 2;
	private static final int BRIEF_COLUMN_INDEX = 3;
	private static final int CONTENT_COLUMN_INDEX = 4;
	private static final int DATE_OF_PUBLISHING_COLUMN_INDEX = 5;
	
	// set to true for fair access
	private static final Lock lock = new ReentrantLock(true);

	private static final Logger logger = Logger.getLogger(NewsDAOJDBC.class);

	private NewsDAOJDBC() {
	}

	public static INewsDAO getInstance() {
		return dao;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		pool = connectionPool;
	}

	public News fetchNewsById(long newsId) throws TATechnicalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(SQLQueryGetter
					.getSQlQuery(FETCH_BY_ID_QUERY));
			preparedStatement.setLong(1, newsId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return buildNews(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(preparedStatement);
			closeResultSet(resultSet);
			pool.makeConnectionFree(connection);
		}
	}

	public List<News> getNewsList() throws TATechnicalException {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(getSQlQuery(GET_LIST_QUERY));
			return buildNewsList(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
			pool.makeConnectionFree(connection);
		}
	}

	public void deleteNews(long newsId) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection
					.prepareStatement(getSQlQuery(DELETE_NEWS_QUERY));
			statement.setLong(1, newsId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	public void deleteNewsGroup(String[] selectedNews)
			throws TATechnicalException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(deleteGroupQuery(selectedNews));
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	public void updateNews(News editedNews) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection
					.prepareStatement(getSQlQuery(UPDATE_NEWS_QUERY));
			statement.setString(1, editedNews.getTitle());
			statement.setString(2, editedNews.getBrief());
			statement.setString(3, editedNews.getContent());
			statement.setString(4, editedNews.getDateOfPublishing());
			statement.setLong(5, editedNews.getNewsId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	public long addNews(News news) throws TATechnicalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection
					.prepareStatement(getSQlQuery(ADD_NEWS_QUERY));
			statement.setString(1, news.getTitle());
			statement.setString(2, news.getBrief());
			statement.setString(3, news.getContent());
			statement.setString(4, news.getDateOfPublishing());
			lock.lock();
			statement.executeUpdate();
			return getLastCreatedNewsId(connection);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			lock.unlock();
			closeStatement(statement);
			pool.makeConnectionFree(connection);
		}
	}

	private static long getLastCreatedNewsId(Connection connection)
			throws NewsDAOException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement
					.executeQuery(getSQlQuery(GET_ID_OF_NEW_NEWS_QUERY));
			resultSet.next();
			return resultSet.getLong(1);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
	}

	private static News buildNews(ResultSet newsResultSet)
			throws NewsDAOException {
		try {
			long newsId = newsResultSet.getLong(NEWS_ID_COLUMN_INDEX);
			String title = newsResultSet.getString(TITLE_COLUMN_INDEX);
			String brief = newsResultSet.getString(BRIEF_COLUMN_INDEX);
			String content = newsResultSet.getString(CONTENT_COLUMN_INDEX);
			String date = newsResultSet
					.getString(DATE_OF_PUBLISHING_COLUMN_INDEX);
			return new News(newsId, title, brief, content, date);
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}

	private static List<News> buildNewsList(ResultSet newsListResSet)
			throws NewsDAOException {
		try {
			List<News> newsList = new ArrayList<News>();
			while (newsListResSet.next()) {
				newsList.add(buildNews(newsListResSet));
			}
			return newsList;
		} catch (SQLException e) {
			logger.error(e);
			throw new NewsDAOException(e);
		}
	}
}
