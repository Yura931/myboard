package article.model;

import java.util.Date;

public class Article {
	private Integer articleNo; // primarykey, 시퀀스 설정
	private Writer writer;
	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;
	
	public Article(Integer articleNo, Writer writer, String title, Date regDate, Date modifiedDate, int readCount) {
		super();
		this.articleNo = articleNo;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}
	
	public Integer getArticleNo() {
		return articleNo;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public int getReadCount() {
		return readCount;
	}
	
	
}
