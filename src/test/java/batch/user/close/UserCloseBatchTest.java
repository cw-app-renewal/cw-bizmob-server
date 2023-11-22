package batch.user.close;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import batch.cody.TestCommon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class UserCloseBatchTest extends TestCommon {

	
	@Autowired
	ApplicationContext appContext;
	
	UserCloseBatch userCloseBatch;
	
	@Before
	public void setUp() throws Exception {
		userCloseBatch = appContext.getBean(UserCloseBatch.class);
	}
		
	@Test
	public void testExecuteUserCloseBatch() {

		try {
			
			userCloseBatch.executeUserCloseBatch();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}  
