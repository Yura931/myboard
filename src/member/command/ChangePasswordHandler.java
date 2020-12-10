package member.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.ChangePasswordService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;

public class ChangePasswordHandler implements CommandHandler {
	private static final String FORM_VIEW = "changePwdForm";
	private ChangePasswordService changePwdSvc = new ChangePasswordService();
	
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
		 return FORM_VIEW; // changePwdForm으로 포워드하는 일만 실행
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// jsp에 현재비밀번호와 변경한비밀번호를 건네줌
		User user = (User)req.getSession().getAttribute("authUser");
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		String curPwd = req.getParameter("curPwd"); // 파라미터로 받은 현재패스워드와, 변경한패스워드를 검사
		String newPwd = req.getParameter("newPwd");
		
		if (curPwd == null || curPwd.isEmpty()) { // 문제가 생기면 
			errors.put("newPwd", true); // errors맵에 에러 저장
		}
		
		if (newPwd == null || newPwd.isEmpty()) {
			errors.put("newPwd",true);
		}
		
		if (!errors.isEmpty()) { // errors가 비워져있지 않으면, 
			return FORM_VIEW; // 암호를 입력받는 화면으로 포워드
		}
		
		try {
			changePwdSvc.changePassword(user.getId(), curPwd, newPwd); // 문제가 없으면 changePwdSvc에게 일을 시킴, 
			return "changePwdSuccess";
		} catch (InvalidPasswordException e) { // changePassword에서 throw했던 익셉션들
			errors.put("badCurPwd", true);
			return FORM_VIEW; // 익셉션이 발생하면 FORM_VIEW로 포워드시키거나
		} catch (MemberNotFoundException e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST); // 에러를 발생시키거나 함
			return null;
		}
	}

}
















