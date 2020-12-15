package member.command;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

public class JoinHandler implements CommandHandler { 
	private static final String FORM_VIEW = "join"; // 컨트롤러는 join.do라는 url패턴으로 요청이 들어오면 JoinHandler 객체의 process() 메소드를 호출
	private JoinService joinService = new JoinService(); // joinservice 객체 생성
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception { // CommandHandler 메소드 구현
		if (req.getMethod().equalsIgnoreCase("GET")) { // get방식으로 요청이 오면
			return processForm(req, res); // processForm 메소드 실행
		} else if (req.getMethod().equalsIgnoreCase("POST")) { // post방식으로 요청이 오면
			return processSubmit(req, res); // processSubmit 메소드 실행
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		JoinRequest joinReq = new JoinRequest(); // 사용자가 join.jsp에서 입력할 값들이 유효한지 확인하는 객체, joinReq객체의 validate를통해 errors를 확인
		
		joinReq.setId(req.getParameter("id"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		
		
		Map<String, Boolean> errors = new HashMap<>();  // 네 개의 값이 잘 들어왔는지 확인하는 코드 39행 ~ 46행
		req.setAttribute("errors", errors);
		
		joinReq.validate(errors); // joinReq안에 값이 잘 있는지 validate메소드로 확인, 문제가 있으면 errors가 채워짐
		
		if (!errors.isEmpty()) { // errors가 비어있지 않으면, 채워져있으면 문제가 있다는 뜻으로 더 진행하지 않고 FORM화면으로 다시 포워드 시키는 것
			return FORM_VIEW;
		}
		
		try {
			joinService.join(joinReq);
			return "joinSuccess";
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE); // error가 발생했으니 FORM_VIEW로 return시키겠다는 것
			return FORM_VIEW;
		}
	}
}
