package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;

public class ArticleContentDao {
	public int delete(Connection conn, int no) throws SQLException {
		String sql = "DELETE article_content "
				   + "WHERE article_no=?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1,no);
			
			int cnt = pstmt.executeUpdate();
			
			return cnt;
		}
		
	}
	
	
	public int update(Connection conn, int no, String content) throws SQLException {
		String sql = "UPDATE article_content "
				   + "SET content=?	"
				   + "WHERE article_no=?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1,content);
			pstmt.setInt(2,no);
			
			int cnt = pstmt.executeUpdate();
			
			return cnt;
		}
	}
	public ArticleContent selectById(Connection conn, int no) throws SQLException {		
	
		String sql = "SELECT * FROM article_content "
				   + "WHERE article_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ArticleContent content = null;
			
			if (rs.next()) {
				content = new ArticleContent(rs.getInt("article_no"),
						rs.getString("content"));
			}
			return content;
			
		} finally {
			JdbcUtil.close(rs,pstmt);
		}
		
	}
	
	
	public ArticleContent insert(Connection conn, ArticleContent content) 
			throws SQLException {	
			
			String sql = "INSERT INTO article_content "
					+ "(article_no, content) " // article_no, content values 채워넣는 쿼리
					+ "VALUES (?, ?)";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, content.getNumber()); 
				pstmt.setString(2, content.getContent());
				
				int cnt = pstmt.executeUpdate(); // 레코드 변경여부를 판단하는 메소드 
				
				if (cnt == 1) { // 추가된 레코드가 있다면
					return content; // 쿼리 실행에 성공하면 파라미터로 전달받은 content객체를 그대로 리턴
				} else {
					return null; // 아니면 null을 리턴
				}
			}
		}
}
