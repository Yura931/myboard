package member.service;

import java.sql.Connection;

import java.sql.SQLException;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {
	private MemberDao memberDao = new MemberDao(); // 데이터베이스에서 조회해서 있으면 Exception 발생, 없으면 insert 실행
	
	public void join(JoinRequest joinReq) { // JoinRequest타입인 joinReq파라미터 받아서 일을 하는 메소드
		//값이 유효한지도 확인한 joinReq도 전달해줌
		
		Connection con = null;
		
		// connection open
		try {con = ConnectionProvider.getConnection();  /*선언과 동시에 값을 할당해야 try-with-resources 사용 할 수 있음*/
			 con.setAutoCommit(false); // 자동 커밋 방지
		
		Member m = memberDao.selectById(con, joinReq.getId()); // INSERT하기 전 이 메소드를 통해 ID가 존재하는지 체크하고 DuplicateIdException예외를 throw할지 말지 결정한다.
		
			if (m != null) { // 중복예외, 클라이어트가 넘긴 ID값이 DB에 이미 존재한다면 직접 정의한 예외 DuplicateIdException 객체가 throw 된다.
				JdbcUtil.rollback(con);
				throw new DuplicateIdException(); // 문제가 생겼을 때 익셉션을 발생시키기로 결정, 사용자정의 익셉션을 따로 만들기로 결정, 문제가 생기면 그다음 문장이 실행되지 않고 호출된 곳으로 돌아감
				// joinHandler의 53행 catch문으로 호출 되어 그 이후로 실행흐름이 넘어감 (저자의 의도 ..)
			}
		
			Member member = new Member();
			member.setId(joinReq.getId());
			member.setName(joinReq.getName());
			member.setPassword(joinReq.getPassword());
		
			memberDao.insert(con, member); // ID가 존재하지 않을 경우 memberDao.insert() 메소드로 입력받은 JoinRequest 객체로 Member DTO객체를 생성하여 insert하고 끝
			// memberDao insert메소드에 member객체를 담아 일을 시킴, joinReq에 담아져있던 정보 그대로 담아서 일을 시킴
			// 같은 커넥션을 받개 함, 같은 트랜잭션이기 때문에
			con.commit();
			
		} catch (SQLException e) { // SQL익셉션이 발생하게 되면
			JdbcUtil.rollback(con); // 롤백하고
			throw new RuntimeException(e); // runtimeException 발생
		} finally {
			JdbcUtil.close(con); // 정상실행 되면 close해줌
		}
		// connection close
		
		// 11행 ~ 26행 하나의 업무, 트랜잭션임, 하나의 커넥션을 가지고 실행되어야 한다.
		
		/*관련된 여러 sql문,
		하나의 트랜잭션에서 실행되는 에스큐엘은 
		하나의 커넥션을 가지고 실행되어야 한다.*/
	}
}
