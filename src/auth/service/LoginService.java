package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class LoginService {
	private MemberDao memberDao = new MemberDao();
	
	public User login(String id, String password) { // Dao에 id로 멤버를 select해옴
		try (Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, id);
			if (member == null) { // 해당하는 id가 없으면 익셉션 발생
				throw new LoginFailException();
			}
			if (!member.matchPassword(password)) { // id는 있고, pw가 일치하지 않으면 익셉션 발생
				throw new LoginFailException();  
			}
			return new User(member.getId(), member.getName()); // 위 if문 둘 다 아니면 member클래스에서 id와 Name을 가져와 user객체를 만들어 리턴
		} catch (SQLException e) { 
			throw new RuntimeException(e);
		}
	}
}
