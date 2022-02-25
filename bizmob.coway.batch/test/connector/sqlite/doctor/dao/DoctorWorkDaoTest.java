package connector.sqlite.doctor.dao;

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
public class DoctorWorkDaoTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorWorkDao doctorWorkDao = null;
	
	@Before
	public void setUp() throws Exception {
		doctorWorkDao = appContext.getBean(DoctorWorkDao.class);
	}
	
	@Test
	public void testSelectWorkTableList() {
		try {
			
//			List<String> tableList = doctorWorkDao.selectWorkTableList();
//			
//			for(String table : tableList) {
//				System.out.println(table);
//			}
//			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDropWorkTable() {
//		try {
//			
//			int count = doctorWorkDao.dropWorkTable("RD001_EXPORT");
//			
//			System.out.println(count);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
	}
	
	@Test
	public void testDropWorkTableList() {
//		try {
//			
//			List<String> tableList = doctorWorkDao.selectWorkTableList();
//			
//			int count = doctorWorkDao.dorpWorkTableList(tableList);
//			
//			System.out.println(count);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
	}
	
	@Test
	public void testCreateWorkTableList() {
//		try {
//			
//			doctorWorkDao.createWorkTableList();
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}

}
