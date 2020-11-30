package connector.sqlite.doctor.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.common.TestCommon;


import connector.sqlite.doctor.dao.DoctorBomDao;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class DoctorBomDaoTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorBomDao doctorBomDao = null;
	
	@Before
	public void setUp() throws Exception {
		doctorBomDao = appContext.getBean(DoctorBomDao.class);
	}
	
	@Test
	public void testCreateProductTable() {
		try {
			
			int i = doctorBomDao.createProductTable();
			
			doctorBomDao.createRD008_OITAB1Table();
			
			doctorBomDao.createRD008_OITAB2Table();
			
			System.out.println(i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testDropProductTable() {
		fail("Not yet implemented");
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
