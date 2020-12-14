package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.ConnectionProvider;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao(); 
	private int size = 10;
	
	public ArticlePage getArticlePage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.selectCount(conn);
			List<Article> content = articleDao.select(conn, (pageNum), size); // 페이지에 해당하는 게시들 List에 담아서 
			return new ArticlePage(total, pageNum, size, content); // ArticlePage를 만들어서 Handler로 리턴, 
		} catch (SQLException e ) {
			throw new RuntimeException(e);
		} 
	}
}
