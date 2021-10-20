package batch.cody;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.common.TestCommon;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class CodyBatchTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	CodyBatch codyBatch;
	
	@Before
	public void setUp() throws Exception {
		codyBatch = appContext.getBean(CodyBatch.class);
	}	
	
	@Test
	public void testCreateDatabase() {
		try {
			
			codyBatch.createDatabase();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

}
