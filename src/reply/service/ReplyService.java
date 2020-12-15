package reply.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.ConnectionProvider;
import reply.dao.ReplyDao;
import reply.model.Reply;

public class ReplyService {
	private ReplyDao dao = new ReplyDao();
	
	public List<Reply> getReplyList(int articleNum) {
		Connection con = ConnectionProvider.getConnection();
		
		List<Reply> list; 
		try {
			list = dao.listReply(con, articleNum);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
		return list;
	}
}
