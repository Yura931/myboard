package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {

	private MemberDao memberDao = new MemberDao();
	
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Member member = memberDao.selectById(conn, userId);
			if (member == null) { // member userId가 없으면 MemberNotFoundException 발생 , Exception 처리가 되지 않으면 프로그램이 종료되므로 유의해서 사용
				throw new MemberNotFoundException();
			}
			if (!member.matchPassword(curPwd)) { // 현재비밀번호가 일치하지 않으면 InvalidPasswordException 발생
				throw new InvalidPasswordException();
			}
			member.changePassword(newPwd); // 매개로 받은 newPwd를 멤버객체에 저장
			memberDao.update(conn, member); // 멤버다오객체의 update메소드 실행 newPwd가 저장된 member파라미터 전달
			conn.commit(); // 커넥션 커밋
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
