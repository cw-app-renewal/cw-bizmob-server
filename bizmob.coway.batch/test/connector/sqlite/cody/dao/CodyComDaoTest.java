package connector.sqlite.cody.dao;

import static org.junit.Assert.fail;

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
public class CodyComDaoTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	CodyComDao codyComDao;
	
	@Before
	public void setUp() throws Exception {
		codyComDao = appContext.getBean(CodyComDao.class);
	}
	
	@Test
	public void testCreateCommonCodeTable() {
		try {
			codyComDao.createCommonCodeTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertCommonCodeData() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCommonCodeData() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDropCommonCodeTable() {
		try {
			
			codyComDao.dropCommonCodeTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
