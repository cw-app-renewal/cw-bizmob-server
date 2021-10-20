package connector.sqlite;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.common.TestCommon;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class SqliteCreateDatabaseTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	
	@Test
	public void testSqliteCreateDatabase() {
		try {
			
			SqliteCreateDatabase db = new SqliteCreateDatabase("WD_COM");
		
			System.out.println(db.toString());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
