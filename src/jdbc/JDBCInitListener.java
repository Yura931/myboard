package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class JDBCInitListener
 *
 */
@WebListener
public class JDBCInitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public JDBCInitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {  // 어플리케이션이 종료될 때 들어가야하는 코드
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {  // 어플리케이션이 시작 될 때 들어가는 코드, 어떤 서블릿보다도 먼저 실행된다.
         System.out.println("게시판 앱 최초 실행~~~");
         
         ServletContext application = sce.getServletContext(); // 어플리케이션의 메소드 사용을 위해 어플리케이션 꺼내옴
         
         String url = application.getInitParameter("jdbcUrl"); // getInitParameter로 web.xml에 저장해둔 param-value값을 꺼내옴
         String user = application.getInitParameter("jdbcUser");
         String pw = application.getInitParameter("jdbcPassword");
         
         // context root 경로
         String contextPath = application.getContextPath(); // 
         application.setAttribute("root", contextPath); // /myboard 경로 application에 root라는 이름으로 setAttribute에 저장
         System.out.println(contextPath);
         
         System.out.println(url);
         System.out.println(user);
         System.out.println(pw);
         

         // 1. 클래스 로딩
         try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         // 2. drivermanager에서 connection
         try (    
        		 Connection con = DriverManager.getConnection(url, user, pw);
        	) {
        	 System.out.println("연결 잘 됨");
        	 System.out.println(con); // oracle.jdbc.driver.T4CConnection@4b54af3d
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         // CommectionProvider Bean에 url, user, pw 저장
         ConnectionProvider.setUrl(url);
         ConnectionProvider.setUser(user);
         ConnectionProvider.setPassword(pw);
         
         // 3. close();
         
    }
	
}























