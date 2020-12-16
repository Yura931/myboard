package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import auth.service.User;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class RemoveMemberService {
	private MemberDao memberDao = new MemberDao();
	
	
	public void removeMember(User user, String password) { // User객체 get,set Id,Name 받아옴
		Connection conn = null;
		Member member = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			member = memberDao.selectById(conn, user.getId());
			
			if (member == null) {
				throw new MemberNotFoundException();
			}
			if (!member.matchPassword(password)) {
				throw new InvalidPasswordException();
			}
			
			
			
			memberDao.delete(conn, user.getId());
			conn.commit();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
		
		// 1. memberDao.selectById로 member 객체 얻기
		// 	1.2 member없으면 MemberNotFoundException 발생
		
		// 2. 얻어온 password와 member의 password가 다르면
		//	2.1 InvalidPasswordException 발생
		
		// 통과하면 delete메소드 실행
		
	}
}
