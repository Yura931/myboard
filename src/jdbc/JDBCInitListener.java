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
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         System.out.println("게시판 앱 최초 실행~~~");
         
         ServletContext application = sce.getServletContext();
         
         String url = application.getInitParameter("jdbcUrl");
         String user = application.getInitParameter("jdbcUser");
         String pw = application.getInitParameter("jdbcPassword");
         
         // context root 경로
         String contextPath = application.getContextPath();
         application.setAttribute("root", contextPath);
         
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
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         ConnectionProvider.setUrl(url);
         ConnectionProvider.setUser(user);
         ConnectionProvider.setPassword(pw);
         
         // 3. close();
         
    }
	
}























