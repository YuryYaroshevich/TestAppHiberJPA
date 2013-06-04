package com.epam.ta.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.epam.ta.util.datelocalizator.DateLocalizator;

@Entity
@Table(name = "News")
@NamedQueries({
		@NamedQuery(name = "newsList", query = "from News"),
		@NamedQuery(name = "deleteNewsGroup",
		     query = "delete from News where news_id in(:newsGroup)")
})
public class News implements Serializable {
	private static final long serialVersionUID = 7768144198842298346L;

	private long newsId;
	private String title;
	private String brief;
	private String content;
	private String dateOfPublishing;

	public News() {
		Date date = Calendar.getInstance().getTime();
		dateOfPublishing = DateLocalizator.getDateOfDefaultFormat(date);
	}

	public News(long newsId) {
		setNewsId(newsId);
	}

	public News(long newsId, String title, String brief, String content,
			String dateOfPublishing) {
		setNewsId(newsId);
		setTitle(title);
		setBrief(brief);
		setContent(content);
		setDateOfPublishing(dateOfPublishing);
	}

	public News(News news) {
		setNewsId(news.newsId);
		setTitle(news.title);
		setBrief(news.brief);
		setContent(news.content);
		setDateOfPublishing(news.dateOfPublishing);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEWS_SEQ")
	@Column(name = "NEWS_ID")
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	@Column(name = "TITLE")
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "BRIEF")
	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "CONTENT")
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "DATE_OF_PUBLISHING")
	public void setDateOfPublishing(String date) {
		this.dateOfPublishing = date;
	}

	public String getTitle() {
		return title;
	}

	public String getBrief() {
		return brief;
	}

	public String getContent() {
		return content;
	}

	public String getDateOfPublishing() {
		return dateOfPublishing;
	}

	public long getNewsId() {
		return newsId;
	}

	public String toString() {
		return "News [newsId=" + newsId + "," + " title=" + title + ", brief="
				+ brief + ", content=" + content + ", date=" + dateOfPublishing
				+ "]";
	}

	public int hashCode() {
		// the values of all those primes were chosen without the reason
		final int prime1 = 19;
		final int prime2 = 43;
		final int prime3 = 23;
		final int prime4 = 67;
		final int prime5 = 79;
		int hash = 1;
		hash = prime1 * hash + ((brief == null) ? 0 : brief.hashCode());
		hash = prime2 * hash + ((content == null) ? 0 : content.hashCode());
		hash = prime3
				* hash
				+ ((dateOfPublishing == null) ? 0 : dateOfPublishing.hashCode());
		hash = prime4 * hash + (int) (newsId ^ (newsId >>> 32));
		hash = prime5 * hash + ((title == null) ? 0 : title.hashCode());
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		News other = (News) obj;
		if (brief == null) {
			if (other.brief != null) {
				return false;
			}
		} else if (!brief.equals(other.brief)) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (dateOfPublishing == null) {
			if (other.dateOfPublishing != null) {
				return false;
			}
		} else if (!dateOfPublishing.equals(other.dateOfPublishing)) {
			return false;
		}
		if (newsId != other.newsId) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
}
