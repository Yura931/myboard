package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler{
	
	private static final String FORM_VIEW = "loginForm";
	private LoginService loginService = new LoginService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) { // get방식으로 왔을 때에는 processFrom이 실행, 하는 일은 간단히 FORM_VIEW를 리턴, ControllerUsingURI에서 prefix와 suffix와 합쳐져 경로가 완성됨 
			return processForm(req, res); 
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password")); // 파라미터를 읽어옴, 읽어오는 중에 앞뒤 공백 제거
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors); // 같은 request영역에 있으면 꺼내서 어떠한 일을 할 수 있음
		
		if (id == null || id.isEmpty()) // service에 일을 시키기 전에 id와 password를 잘 받았는지 확인하는 절차
			errors.put("id", Boolean.TRUE); // id가 null이거나 비어있으면 errors맵에 true로 값을 넣어 error를 발생시킨다.
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);
		
		if (!errors.isEmpty()) { // map이 비어있지 않으면, 
			return FORM_VIEW; // 문제가 생기면 적절한 포워드로 form에 머무르게 함 , 오류가 생기면 여기서 리턴하고 끝
		}
		
		try {
			User user = loginService.login(id,  password); // 적절한 서비서에 적절한 일을 하게 하는 것, 오류가 없으면 User객체가 일을 하게 됨
			req.getSession().setAttribute("authUser", user); // request로부터 getSession메소드로 session을 꺼냄,
			// session에 넣어둬야 다음 요청이 왔을 때 얘가 로그인 된 유저라는 것을 알 수 있음, 같은 브라우저에 온 요청에 어스유저가 남아있다면 로그인되어있다는 것을 판단할 수 있음
			res.sendRedirect(req.getContextPath() + "/index.jsp"); // redirect후 포워드 불가능, null을 리턴 함
			return null;
		} catch (LoginFailException e) { // LoginFailException이 발생하게 되면 FORM에 머물러있게 함
			errors.put("idOrPwNotMatch",  Boolean.TRUE);
			return FORM_VIEW;
		}
	}
	
	private String trim(String str) {
		return str == null ? null : str.trim(); // 값이 있는 경우 앞뒤 공백을 잘라주는 메소드 만듦
	}
}



















