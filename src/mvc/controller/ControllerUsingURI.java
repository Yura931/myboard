package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import mvc.command.NullHandler;

/**
 * Servlet implementation class ControllerUsingURI
 */

public class ControllerUsingURI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String prefix = "/WEB-INF/view/";
    private String suffix = ".jsp";
    private Map<String,CommandHandler> map = null;
    
    
    //
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerUsingURI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException { // init() 서블릿이 생성되자마자 실행되는 코드들 작성하면됨 ,어떤 요청이 오기전에 실행되어야 하는 코드 , init메소드에 들어가 있어야 함
    	ServletConfig config = getServletConfig(); // config를 꺼내오는 방법
        String configFilePath = config.getInitParameter("configFile").trim(); // configFile이름으로 저장해둔 param-value값을 읽어오게 됨
    	
    	map = new HashMap<>();
    	ServletContext application = getServletContext();// getRealPath메소드를 사용하기 위해 application을 먼저 불러옴
		String filePath = application.getRealPath(configFilePath); // 톰캣이 어떤폴더에 있는지 알 수 없음.. 그래서 getRealPath 사용 ...  configFile의 경로를 알고 싶은데 저 경로는 우리가 언제든 옮길 수 있는 경로이기 때문에 실제 경로를 알기위해 사용
		
		// Inpuststream 파일경로에 있는 파일을 읽어내는 stream 바이트 단위로 읽어냄
		try (FileReader fr = new FileReader(filePath);) { // FileReader는 문자단위 스트림
		
			
		Properties properties = new Properties();
		properties.load(fr); //properties파일 안에 있는 값을 키, 벨류로 읽어오는 메소드 load();
				
		Set<Object> keys = properties.keySet();
		
		for (Object key : keys) {
			Object value = properties.get(key);
			String className = (String) value;
			
			try {
				Class c = Class.forName(className);
				Object o = c.newInstance();
				CommandHandler handler = (CommandHandler) o;
				map.put((String) key, handler); // properties를 읽어 map을 만들어 놓은 상태
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String root = request.getContextPath();
		
		String command = "";
		if (uri.startsWith(root)) {
			command = uri.substring(root.length());
		} //경로에서 command를 뽑아냄
		CommandHandler handler = map.get(command);
		
		if (handler == null) {
			handler = new NullHandler();
		}
		
		/*
		 * ServletContext application = getServletContext(); String filePath =
		 * application.getRealPath("/WEB-INF/commandHandlerURI.properties"); // 톰캣이
		 * 어떤폴더에 있는지 알 수 없음.. 그래서 getRealPath 사용 ...
		 * 
		 * // Inpuststream 파일경로에 있는 파일을 읽어내는 stream 바이트 단위로 읽어냄 FileReader fr = new
		 * FileReader(filePath); // FileReader는 문자단위 스트림
		 * 
		 * Properties properties = new Properties(); properties.load(fr); // 어떤 요청이 오기전에
		 * 실행되어야 하는 코드
		 */		
		
		// 우리가 원하는 경로의 command가 잘 뜨면 if문을 사용할 필요가 없어짐
		
		//String className = properties.getProperty(command);
		
		//try {
		//Class c = Class.forName(className);
		//Object o = c.newInstance();
		//handler = (CommandHandler) o;
		//}catch (Exception e) {
			//e.printStackTrace();
		//} // init메소드에서 우선실행 시킴, 필요 없어진 코드
		
		/*
		 * int b = 0;
		 * 
		 * while ((b = fr.read()) != -1) { System.out.print((char) b); }
		 */
		// FileReader는 문자를 읽는 스트림
		// inputstream이 끝까지 다 읽었을 경우 -1을 리턴함
		// byte 단위로 읽어내기때문에 안전하게 영문으로 변환 가능
		
		/*
		 * if (command.contentEquals("/join.do")) { handler = new JoinHandler(); } else
		 * if (command.contentEquals("/login.do")) { handler = new LoginHandler(); }
		 * else if (command.contentEquals("/logout.do")) { handler = new
		 * LogoutHandler(); } else { handler = new NullHandler(); // ==
		 * CommandHandler인터페이스를 구현한 객체 }
		 */
		
		
		
		
		
		  // System.out.println(root); 
		  // System.out.println(uri); // 주소에 입력한 포트 다음의 경로를 얻어 수 있음,/myboard/~~~~ , /myboard/는 항상 같음 System.out.println(command);
		 
		
		
		
		String view = null;
		try {
			view = handler.process(request,  response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(prefix + view + suffix).forward(request, response);
		
		
	}

}
