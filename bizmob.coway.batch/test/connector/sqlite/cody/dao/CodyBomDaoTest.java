package connector.sqlite.cody.dao;

import static org.junit.Assert.fail;

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
public class CodyBomDaoTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	CodyBomDao codyBomDao;

	@Before
	public void setUp() throws Exception {
		codyBomDao = appContext.getBean(CodyBomDao.class);
	}
	
	@Test
	public void testCreateProductTable() {
		try {
			
			codyBomDao.createProductTable();
			
			codyBomDao.createRD007_ExportTable();
			
			codyBomDao.createRD007_O_ITAB1Table();
			
			codyBomDao.createRD007_O_ITAB2Table();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Test
	public void testDropProductTable() {
		try {
			codyBomDao.dropProductTable();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteProductData() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertProductData() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProductData() {
		fail("Not yet implemented");
	}

}
