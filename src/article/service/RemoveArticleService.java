package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import auth.service.User;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class RemoveArticleService {
	
	private ArticleContentDao articleContentDao = new ArticleContentDao();
	private ArticleDao articleDao = new ArticleDao();
	
	public void removeArticle(int no) {
	Connection conn = null;
	Article article = null;
	try {
		conn = ConnectionProvider.getConnection();
		conn.setAutoCommit(false);
		
		article = articleDao.selectById(conn, no);
		
		if (article == null) {
			throw new ArticleNotFoundException();
		}
		
		if (!article.equals(article.getNumber())) {
			throw new RuntimeException();
		}
		
		articleDao.delete(conn, no);
		articleContentDao.delete(conn, no);
		conn.commit();
	} catch (SQLException e) {
		JdbcUtil.rollback(conn);
		throw new RuntimeException(e);
	} catch (PermissionDeniedException e) {
		JdbcUtil.rollback(conn);
		throw e;
	} finally {
		JdbcUtil.close(conn);
	}
	
	}
}
