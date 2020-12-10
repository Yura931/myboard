package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDao { // 실제 member테이블에 멤버를 추가하고 기존에 멤버가 있는지 검색하는 MemeberDao객체 저의

	public void delete(Connection con, String id) throws SQLException {
		String sql = "DELETE member "
				   + "WHERE memberid=?";
		
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
		}
		
				
	}
	
	public void update(Connection conn, Member member) throws SQLException { // 커넥션과 멤버객체를 받아 새로운 update내용 받음
		String sql = "UPDATE member "
				   + "Set name=?, password=? "
				   + "WHERE memberid=? ";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getId());
			
			pstmt.executeUpdate();
		}
	}
	
	public Member selectById(Connection con, String id) throws SQLException{
		
		Member member = null;
		
		String sql = "SELECT memberid, name, password, regdate "
				   + "FROM member "
				   + "WHERE memberid=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member();
				member.setId(rs.getString(1));
				member.setName(rs.getString(2));
				member.setPassword(rs.getString(3));
				member.setRegDate(rs.getTimestamp(4)); // 날짜와 시간을 다 얻어오는 ResultSet의 메소드 Timestamp
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(rs,pstmt);
		}
				
		
		return member;
	}
	
	public void insert(Connection con, Member member) throws SQLException{
		String sql = "INSERT INTO member "
				   + "(memberid, name, password, regdate) "
				   + "VALUES (?, ?, ?, SYSDATE) ";
				
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	 
}































