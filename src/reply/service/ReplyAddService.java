package reply.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import reply.dao.ReplyDao;

public class ReplyAddService {
	private ReplyDao dao = new ReplyDao();

	public void add(String userId, int articleNo, String body) {
		Connection conn = ConnectionProvider.getConnection();
		
		try {
			dao.insert(conn, userId, articleNo, body);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
