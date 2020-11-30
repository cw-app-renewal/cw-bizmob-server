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


import connector.sqlite.doctor.dao.DoctorComDao;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class DoctorComDaoTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorComDao doctorComDao = null;

	@Before
	public void setUp() throws Exception {
		doctorComDao = appContext.getBean(DoctorComDao.class);
	}	
	@Test
	public void testCreateCommonCodeTable() {
		try {
			
			doctorComDao.createCommonCodeTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateQtCodeTable() {
		try {
			
			doctorComDao.createQtCodeTable();
			
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
	public void testInsertQtCodeData() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCommonCodeData() {
		try {
			
			doctorComDao.deleteCommonCodeData();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteQtCodeData() {
		try {
			
			doctorComDao.deleteQtCodeData();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
