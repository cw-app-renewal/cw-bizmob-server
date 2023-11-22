package connector.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteCreateDatabase {
	
	
	public SqliteCreateDatabase(String _dbName) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {		
		
			Class.forName("org.sqlite.JDBC");
			String smartHome = System.getProperty("SMART_HOME");
			String url = "jdbc:sqlite:" + smartHome + "/sync/" + _dbName;
			
			conn = DriverManager.getConnection(url);
			
			stmt = conn.createStatement();
			
			int result = stmt.executeUpdate("create table if not exists dummy (dummy text);");
			
			System.out.println(result);
			
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(stmt != null)
				stmt.close();
			
			if(conn != null)
				conn.close();			
		}
	}
	
	
	
	
}
