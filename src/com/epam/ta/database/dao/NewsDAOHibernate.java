package com.epam.ta.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsDAOHibernate implements INewsDAO {
	private static final INewsDAO dao = new NewsDAOHibernate();

	private static SessionFactory sessionFactory;

	// key for named query
	private static final String NEWS_LIST = "newsList";
	private static final String DELETE_NEWS_GROUP = "deleteNewsGroup";
	// parameter for deleting news group query
	private static final String NEWS_GROUP = "newsGroup";

	public static INewsDAO getInstance() {
		return dao;
	}

	private NewsDAOHibernate() {
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		NewsDAOHibernate.sessionFactory = sessionFactory;
	}

	public void close() {
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	public List<News> getNewsList() throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<News> newsList = session.getNamedQuery(NEWS_LIST).list();
		tx.commit();
		return newsList;
	}

	public News fetchNewsById(long newsId) throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		News news = (News) session.get(News.class, newsId);
		tx.commit();
		return news;
	}

	public void deleteNews(long newsId) throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(new News(newsId));
		tx.commit();
	}

	public void deleteNewsGroup(String[] selectedNews)
			throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query deletingQuery = session.getNamedQuery(DELETE_NEWS_GROUP);
		deletingQuery.setParameterList(NEWS_GROUP, selectedNews);
		/*session
				.createQuery(deleteGroupQuery(selectedNews));*/
		deletingQuery.executeUpdate();
		tx.commit();
	}

	public long addNews(News news) throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		long newsId = (Long) session.save(news);
		tx.commit();
		return newsId;
	}

	public void updateNews(News editedNews) throws TATechnicalException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(editedNews);
		tx.commit();
	}
}
