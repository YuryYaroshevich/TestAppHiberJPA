package com.epam.ta.database.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsDAOJPA implements INewsDAO {
	private static final INewsDAO dao = new NewsDAOJPA();

	@PersistenceUnit
	private static EntityManagerFactory entityManagerFactory;

	// key for named query
	private static final String NEWS_LIST = "newsList";
	private static final String DELETE_NEWS_GROUP = "deleteNewsGroup";
	// parameter name for deleting news group query
	private static final String NEWS_GROUP = "newsGroup";

	private NewsDAOJPA() {
	}

	public static INewsDAO getInstance() {
		return dao;
	}

	public void close() {
		entityManagerFactory.close();
	}

	public void setEntityManagerFactory(EntityManagerFactory entManFactory) {
		entityManagerFactory = entManFactory;
	}

	public List<News> getNewsList() throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction(); 
		tx.begin();
		TypedQuery<News> query = entManager.createNamedQuery(NEWS_LIST,
				News.class); 
		List<News> newsList = query.getResultList();
		tx.commit();
		entManager.close();
		return newsList;
	}

	public News fetchNewsById(long newsId) throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction();
		tx.begin();
		News news = (News) entManager.find(News.class, newsId);
		tx.commit();
		entManager.close();
		return news;
	}

	public void deleteNews(long newsId) throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction();
		tx.begin();
		News news = (News) entManager.find(News.class, newsId);
		entManager.remove(news);
		tx.commit();
		entManager.close();
	}

	public void deleteNewsGroup(String[] selectedNews)
			throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction();
		tx.begin();
		Query query = entManager.createNamedQuery(DELETE_NEWS_GROUP);
		query.setParameter(NEWS_GROUP, Arrays.asList(selectedNews));//entManager.createQuery(deleteGroupQuery(selectedNews));
		query.executeUpdate();
		tx.commit();
		entManager.close();
	}

	public long addNews(News news) throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction();
		tx.begin();
		entManager.persist(news);
		tx.commit();
		entManager.close();
		return news.getNewsId();
	}

	public void updateNews(News editedNews) throws TATechnicalException {
		EntityManager entManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entManager.getTransaction();
		tx.begin();
		News oldNews = entManager.find(News.class, editedNews.getNewsId());
		oldNews.setTitle(editedNews.getTitle());
		oldNews.setBrief(editedNews.getBrief());
		oldNews.setContent(editedNews.getContent());
		oldNews.setDateOfPublishing(editedNews.getDateOfPublishing());
		tx.commit();
		entManager.close();
	}
}