package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class WriteArticleService {

	private ArticleDao articleDao = new ArticleDao(); // 쿼리작업 수행하는 ArticlDao, ArticleContentDao객체 생성
	private ArticleContentDao contentDao = new ArticleContentDao();

	public Integer write(WriteRequest req) { // WriteReuest(writer, title, content)객체를 파라미터로 받음
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false); 

			Article article = toArticle(req); // new Article(null, req.getWriter(), req.getTitle(), null, null, 0) 새로 생성된 객체를 리턴해주는 toArticle메소드 article변수에 할당
			Article savedArticle = articleDao.insert(conn, article); // article테이블에 insert쿼리작업을 수행하는 insert메소드 savedArticle 변수에 할당

			if (savedArticle == null) { // insert가 null이면
				throw new RuntimeException("fail to insert article"); // 익셉션 발생
			}

			ArticleContent content = new ArticleContent(savedArticle.getNumber(), req.getContent()); // savedArticle변수는 Article타입으로 선언, 
																									//Article객체의 getNumber(),와 WriteRequest의 getContent()를 파라미터로 받음
			ArticleContent savedContent = contentDao.insert(conn, content); // 쿼리수행
			if (savedContent == null) { // insert가 null이면
				throw new RuntimeException("fail to insert article_content"); // 익셉션 발생
			}

			conn.commit();

			return savedArticle.getNumber();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}

	}

	private Article toArticle(WriteRequest req) {
		return new Article(null, req.getWriter(), req.getTitle(), null, null, 0);
	}

}
