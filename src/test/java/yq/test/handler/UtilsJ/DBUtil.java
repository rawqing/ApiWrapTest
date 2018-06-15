package yq.test.handler.UtilsJ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	public Connection getCon(){
		Connection con=null;
		String url="jdbc:oracle:thin:@192.168.130.17:1521:ebank";
		String user="ebank";
		String password="ebank";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		
	}
	/*public Connection getCon1(){
		Connection con=null;
		String url="jdbc:oracle:thin:@192.168.130.4:1521:jlcda";
		String user="zh_ebank";
		String password="zh_ebank";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		
	}*/
}
