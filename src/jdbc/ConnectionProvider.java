package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	private static String url;
	private static String user;
	private static String password;
	
	
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	static void setUrl(String url) {
		ConnectionProvider.url = url;
	}

	static void setUser(String user) {
		ConnectionProvider.user = user;
	}

	static void setPassword(String password) {
		ConnectionProvider.password = password;
	}
}
