package reply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reply.model.Reply;

public class ReplyDao {

	public void insert(Connection conn, String userId, int articleNo, String body) throws SQLException {
		// 11g
		/*
		String sql = "INSERT INTO reply "
				+ "(replyid, memberid, article_no, body, regdate) "
				+ "VALUES (reply_seq.nextval, ?, ?, ?, SYSDATE)";
		*/
		
		String sql = "INSERT INTO reply "
				+ "(memberid, article_no, body, regdate) "
				+ "VALUES (?, ?, ?, SYSDATE)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setInt(2, articleNo);
			pstmt.setString(3, body);
			
			pstmt.executeUpdate();
		}
	}

	public List<Reply> listReply(Connection con, int articleNum) throws SQLException {
		String sql = "SELECT replyid,"
				+ " memberid,"
				+ " article_no,"
				+ " body,"
				+ " regdate " + 
				"FROM reply " + 
				"WHERE article_no=? " + 
				"ORDER BY replyid DESC";

		List<Reply> list = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, articleNum);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Reply r = new Reply();
				r.setId(rs.getInt(1));
				r.setMemberId(rs.getString(2));
				r.setArticleNum(rs.getInt(3));
				r.setBody(rs.getString(4));
				r.setRegDate(rs.getTimestamp(5));
				
				list.add(r);
			}
		}
		return list;
	}
	
}
