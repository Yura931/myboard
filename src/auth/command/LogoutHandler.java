package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler { // logout.do 경로로 요청이 오면 LogoutHandler 실행

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(false); // parameter가 없는 getSession : 현재 session을 리턴, 세션이 없으면 세션을 새로 만듬,
														// 파라미터가 있는 getSession : 현재 세션이 없을때 true를 파라미터로 받으면 세션을 만들고,
														// false를 받으면 세션을 만들지 않음
		// 로그인 하지 않고 logout.do로 첫 요청 보내면 세션이 없음, session을 만들지 않고 index.jsp로 redirect 시킴,
		// 로그인 후 , logout.do 요청 시 세션을 없애고 index.jsp로 리다이렉트 됨
		if (session != null) { // login하지 않은 상태에서 logout.do 경로를 통해 들어가면 session이 없는 상태
			session.invalidate(); // session이 null이 아닐 경우에 invalidate() 실행
		}
		res.sendRedirect(req.getContextPath() + "/index.jsp");
		return null;
	}
}
