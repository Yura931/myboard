package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import member.service.RemoveMemberService;
import mvc.command.CommandHandler;

public class RemoveMemberHandler implements CommandHandler {
		private static final String FORM_VIEW ="removeMemberForm";
		private RemoveMemberService removeMemberSvc = new RemoveMemberService();
		
		@Override
		public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
			if (req.getMethod().equalsIgnoreCase("GET")) {
				return processForm(req, res);
			} else if (req.getMethod().equalsIgnoreCase("POST")) {
				return processSubmit(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return null;
			}

		}
		
		
		
		private String processForm(HttpServletRequest req, HttpServletResponse res) {
			return FORM_VIEW;
		}



		private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
			// password 파라미터 얻기, 저장
			
			Map<String, Boolean> errors = new HashMap<>();
			req.setAttribute("errors", errors);
			
			String password = req.getParameter("password");
			
			if (password == null || password.isEmpty()) { // password가 null 이거나 비어 있으면(empty)
				errors.put("password", true); // FORM_VIEW로 리턴
			}
			
			if (!errors.isEmpty()) {
				return FORM_VIEW;
			}
				
			// removeMemberService의 일 실행
			HttpSession session = req.getSession(); // session에 저장해둔 User객체를 꺼내오기 위해 session 호출
			User user = (User) session.getAttribute("authUser"); // User객체 호출	
			try {
			removeMemberSvc.removeMember(user, password); // 세션에 저장해둔 user객체 꺼내오기, request로 받아온 password 매개변수로 전달			
			// 세션을 invalidate
			session.invalidate(); // 로그아웃 후 index.jsp파일 실행시 로그인 되어있는 상태를 방지하기위해 session을 닫아줌
			return "removeMemberSuccess";
			
			} catch (InvalidPasswordException e) {
				e.printStackTrace();
				errors.put("badPwd", true);
				return FORM_VIEW;
			} catch (MemberNotFoundException e) {
				e.printStackTrace();
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			
		}	
}
