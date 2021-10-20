package connector.sqlite;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import connector.sqlite.doctor.mapper.DoctorBomMapper;


public class SqliteDataSourceTest {

	@Test
	public void testSqliteDataSource() {
		
		SqlSession session = null;
		
		try {
		
			SqliteDataSource sds = new SqliteDataSource("jdbc:sqlite:D:/TEST.db", "tester");
			session = sds.openSession();
			
			DoctorBomMapper mapper = session.getMapper(DoctorBomMapper.class);
			
			//mapper.dropProductTable();
			
			//mapper.createProductTable();
			
			//mapper.createProductIndex();
			
			mapper.createTestTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		
	}

	
	
	
}
