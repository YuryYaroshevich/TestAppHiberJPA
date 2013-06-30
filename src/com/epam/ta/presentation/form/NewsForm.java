package com.epam.ta.presentation.form;

import static com.epam.ta.util.appconstant.TAConstant.DEFAULT_DATE_FORMAT;
import static com.epam.ta.util.appconstant.TAConstant.getConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.epam.ta.model.News;
import com.epam.ta.model.comparison.NewsByDateComparator;

public final class NewsForm extends ValidatorForm {
	private static final long serialVersionUID = 4795803945923209098L;

	private List<News> newsList;

	private News newsMessage;

	private String[] selectedNews;

	private static final Comparator<News> newsByDateComparator;

	//  TODO: take it away in wrapper
	static {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
				getConstant(DEFAULT_DATE_FORMAT));
		newsByDateComparator = new NewsByDateComparator(defaultDateFormat);
	}

	public NewsForm() {
		newsList = new ArrayList<News>();
		newsMessage = new News();
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		selectedNews = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}

	public List<News> getNewsList() {
	//  TODO: take it away in wrapper
		Collections.sort(newsList, newsByDateComparator);
		return newsList;
	}

    //  TODO: take it away in wrapper
	public List<News> getNewsList(Comparator<News> newsComparator) {
		Collections.sort(newsList, newsComparator);
		return newsList;
	}

	// nList is all news from table
	public void setNewsList(List<News> list) {
		this.newsList.clear(); // to avoid repetitive elements
		this.newsList.addAll(list);
	}

	public News getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(News newsMessage) {
		this.newsMessage = newsMessage;
	}

	public void resetNewsMessage() {
		setNewsMessage(new News());
	}

	public String[] getSelectedNews() {
		return selectedNews;
	}

	public void setSelectedNews(String[] selectedNews) {
		this.selectedNews = selectedNews;
	}

	// getters and setters for newsMessage
	public String getTitle() {
		return newsMessage.getTitle();
	}

	public String getBrief() {
		return newsMessage.getBrief();
	}

	public String getContent() {
		return newsMessage.getContent();
	}

	public String getDateOfPublishing() {
		return newsMessage.getDateOfPublishing();
	}

	public long getNewsId() {
		return newsMessage.getNewsId();
	}

	public void setNewsId(long newsId) {
		newsMessage.setNewsId(newsId);
	}

	public void setTitle(String title) {
		newsMessage.setTitle(title);
	}

	public void setBrief(String brief) {
		newsMessage.setBrief(brief);
	}

	public void setContent(String content) {
		newsMessage.setContent(content);
	}

	public void setDateOfPublishing(String dateOfPublishing) {
		newsMessage.setDateOfPublishing(dateOfPublishing);
	}
}
