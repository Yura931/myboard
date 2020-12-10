package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */

public class LoginCheckFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException { // filter에서 사용하는 request와 response는 Http가 빠져있는 ServletRequest, ServletResponse 이다. 
		// ServletRequest에는 session을 얻어오는 메소드나, getContextPath메소드를 얻어오는 객체가 없기 때문에 하위 인터페이스인 HttpServletRequest로 강제 형변환 시켜주어야 한다.
		// 열심히 어떤 일을 한 다음 다음 필터, 서블릿이 일을 하도록 chain.doFilter메소드를 잘 실행시켜주면 됨
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 생성하지 않고 null을 반환
		
		if (session == null || session.getAttribute("authUser") == null) {
		// 로그인 되어있지 않으면 login.do로 redirect
			response.sendRedirect(request.getContextPath() + "/login.do");
		} else {		
		// 로그인 되어 있으면 다음 서블릿, 필터가 일을하도록
		chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
