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
	
	public int delete(Connection conn, int no) throws SQLException {
		String sql = "DELETE article "
				   + "WHERE article_no=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			int cnt = pstmt.executeUpdate();
					
			return cnt;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public int update(Connection conn, int no, String title) throws SQLException {
		String sql = "UPDATE article "
				   + "SET title=?, moddate=SYSDATE "
				   + "WHERE articl_no=?";
		PreparedStatement pstmt = null;
		
		try { 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			
			int cnt = pstmt.executeUpdate();
				
			return cnt;
			
			} finally {
				JdbcUtil.close(pstmt);
			}				
	}
	
	public Article selectById(Connection conn, int no) throws SQLException { // 하나의 Article을 담아 Article객체로 반환 시켜주는 역할
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
			String sql = "SELECT * FROM article WHERE article_no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			Article article = null;
		
			if (rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		String sql = "UPDATE article set read_cnt = read_cnt + 1 "
				   + "WHERE article_no = ?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
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
				+ "FROM ("
				+ "	SELECT article_no, "
				+ " 		   writer_id, "
				+ "        writer_name, "
				+ "        title, "
				+ "        regdate, "
				+ "        moddate, "
				+ "        read_cnt, "
				+ "        ROW_NUMBER() "
				+ "          OVER ( "
				+ "            ORDER BY "
				+ "            article_no "
				+ "            DESC) "
				+ "        rn "
				+ "  FROM article "
				+ ") WHERE rn "
				+ "    BETWEEN ? AND ?";
				   
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum-1) * size + 1);
			pstmt.setInt(2, pageNum * size);
			
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			
			return result;
		} finally {
			JdbcUtil.close(rs, pstmt);
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
							rs.getString("writer_name")
							),
					rs.getString("title"),
					rs.getTimestamp("regdate"),
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
		pstmt = conn.prepareStatement(sql, new String[] {"article_no", "regdate", "moddate"}); 	// generated로 정해놓은 article_no칼럼은 자동증가 값이기 때문에 insert시 바로 값을 알 수 없음. 
		// ex) 마지막 insert한 article_no가 6이여도 6이라는 숫자를 직접 넣어준 것이 아닌 자동생성된 값이므로 SELECT 하기전에는 알 수 없는 값.

		//INSERT 후 SELECT 할 때 알 수 있는 정보들
		// 지정된 배열에 의해 자동생성된 배열 인덱스번호를 돌려주는 기능?
		
		pstmt.setString(1, article.getWriter().getId()); // writer의 id
		pstmt.setString(2, article.getWriter().getName()); // writer의 name
		pstmt.setString(3, article.getTitle()); // title 저장
		
		int cnt = pstmt.executeUpdate(); // update 후 변동 로우가 1이되면 , 생성된 행의 키를 받아오는 메소드 실행
		
		if (cnt == 1) {			
			rs = pstmt.getGeneratedKeys(); // new String[] 값 들을 insert와 동시에 값을 얻어오기 위해 실행시키는 메소드
			int key = 0;
			Date regDate = null;
			Date modDate = null;
			
			if (rs.next()) { // getGeneratedKeys()메소드를 실행중인 rs의 next()메소드 실행 ,regdate와 moddate값이 insert와 동시에 가져와짐
				// String[] 배열 안의 값을 실행 시킨 듯? ..
				key = rs.getInt(1);  // article_no
				regDate = rs.getTimestamp(2); // regdate
				modDate = rs.getTimestamp(3); // moddate
			}
			
			return new Article(key, // if문을 다 실행하고 나면 rs.getInt로 받아온 번호를 가지고 있는 key(article_no, getWriter,getTitle,regDate,modDate,0을 파라미터로 받는 새 Article객체 생성 후 리턴
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
	




