package article.model;

public class ArticleContent {
	private Integer articleNo;
	private String content;
	
	public ArticleContent(Integer articleNo, String content) {
		super();
		this.articleNo = articleNo;
		this.content = content;
	}
	public Integer getNumber() {
		return articleNo;
	}
	public String getContent() {
		return content;
	}
	
	
}
