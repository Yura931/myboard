package article.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {
	public List<Article> select(Connection conn, int pageNum, int size) throws SQLException { // mysql은 limit함수 사용으로 ?에 받는 숫자를 startRow와 size 파라미터에 넣어주면 간편하지만, 오라클은 파라미터를 시작번호와 사이즈로 받기 불편
		
		String sql = "SELECT " // 오라클 페이징 방법, 다양한 방법이 있음
				   + "rn, "
				   + "article_no, "
				   + "writer_id, "
				   + "writer_name, "
				   + "title, "
				   + "regdate, "
				   + "moddate, "
				   + "read_cnt "
				   + "FROM ( "
				   + "SELECT article_no, "
				   + " 		 writer_id,"
				   + " 		 wirter_name, "
				   + "		 title, "
				   + "       regdate, "
				   + "		 moddate, "
				   + "		 read_cnt, "
				   + "		 ROW_NUMBER() "
				   + "		 OVER ( "
				   + "		 ORDER BY "
				   + "		 article_no "
				   + "		 DESC)"
				   + "			rn"
				   + " FROM article "
				   + ") WHERE rn "
				   + "	BETWEEN ? AND ?";
				   
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum-1) * size + 1);
			pstmt.setInt(2, (pageNum * size));
			
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs,pstmt);
		}
		
		/*
		 * String sql = "SELECT * FROM article ORDER BY article_no DESC " +
		 * "LIMIT ?, ?"; // 시작 row_num(zerobase), 갯수 / 0을 베이스로 페이지 보여줌 // 최신글부터 보이도록 정렬
		 */	
		}
	
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"), 
				new Writer(
						rs.getString("writer_id"),
						rs.getString("writer_name_")
						),
						rs.getString("title"),
						rs.getTimestamp("regdate"), //Timestamp가 곧 Date
						rs.getTimestamp("moddate"),
						rs.getInt("read_cnt")
						);
	}
	
	// 전체게시글 파악 쿼리
	
	public int selectCount(Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM article";
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs, stmt);
		} // SQLException으로 캐치가 넘어가게 됨
	}
	

public Article insert(Connection conn, Article article) throws SQLException { 
	// 
	String sql = "INSERT INTO article "
			+ "(writer_id, writer_name, title,"
			+ " regdate, moddate, read_cnt) "
			+ "VALUES (?, ?, ?, SYSDATE, SYSDATE, 0)";
	
	// 11g
	/*
	String sql = "INSERT INTO article "
			+ "(article_no, writer_id, writer_name, title,"
			+ " regdate, moddate, read_cnt) "
			+ "VALUES (article_seq.nextval, ?, ?, ?, SYSDATE, SYSDATE, 0)";
			*/
	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		pstmt = conn.prepareStatement(sql, new String[] {"article_no", "regdate", "moddate"}); // 지정된 배열에 의해 자동생성된 배열 인덱스번호를 돌려주는 기능?
		
		pstmt.setString(1, article.getWriter().getId()); // writer의 id
		pstmt.setString(2, article.getWriter().getName()); // writer의 name
		pstmt.setString(3, article.getTitle()); // title 저장
		
		int cnt = pstmt.executeUpdate();
		
		if (cnt == 1) {
			rs = pstmt.getGeneratedKeys(); // update 후 변동 로우가 1이되면 , 생성된 행의 키를 받아오는 메소드 실행
			int key = 0;
			Date regDate = null;
			Date modDate = null;
			if (rs.next()) { // getGeneratedKeys()메소드를 실행중인 rs의 next()메소드 실행 
				key = rs.getInt(1);  
				regDate = rs.getTimestamp(2);
				modDate = rs.getTimestamp(3);
			}
			return new Article(key, // if문을 다 실행하고 나면 rs.getInt로 받아온 번호를 가지고 있는 key(writer_id), getWriter,getTitle,regDate,modDate,0을 파라미터로 받는 새 Article객체 생성 후 리턴
					article.getWriter(),
					article.getTitle(),
					regDate,
					modDate,
					0);
		} else {
			return null; // 변경된 레코드가 없으면 (삽입된 레코드가 없으면) null을 리턴
		}
	} finally {
		JdbcUtil.close(rs, pstmt);
	}
}
 	
	// Article의 키 값을 얻어오는 쿼리가 필요, Article 객체 안의 사용자 정보 중 id값은 사용자 정보를 저장 전에 알 수 없기 때문
	// prepareStatement를 만들 때 파라미터 중 두번째 파라미터로 컬럼 인덱스를 가져올 수 있음, 이 파라미터로 자동 생성되는 키를 int[]로 받을 수 있음
	
}
	// 잘 완성된 Article을 리턴해주길 원함
	




