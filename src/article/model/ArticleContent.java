package article.model;

public class ArticleContent { // article_content테이블 정보

		private Integer number;
		private String content;
		
		public ArticleContent(Integer number, String content) {
			this.number = number;
			this.content = content;
		}
		
		public Integer getNumber() { // article_no
			return number;
		}
		
		public String getContent() { // content
			return content;
		}
}
