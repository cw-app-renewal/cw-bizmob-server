package connect.db.mms;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations="/test-ftp-applicationContext.xml" )
public class CowayMmsDaoTest {

	@Autowired
	ApplicationContext	appContext;
	
	CowayMmsDao cowayMmsDao;
	
	@Before
	public void setUp() throws Exception {
		cowayMmsDao = appContext.getBean( CowayMmsDao.class );
	}
	
	@Test
	public void testGetSystemTime() {
		
		try {
			System.out.println("test start");
			
			String time = cowayMmsDao.getSystemTime();
			System.out.println(time);
			
			System.out.println("test end!!!");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testCallSpMms() {
		

	}

	@Test
	public void testCallSpMmsTest() {
	
		try { 
			
			System.out.println("test start!!");
			
			String phnId = "01011112222";
			String invnr = "01022221111";
			String title = "테스트";
			String msg = "테스트 ";
			String deptId = "RFC02엄마2";
			String imgPath = "";
			
			Map<String, Object> map = cowayMmsDao.callSpMmsTest(phnId, invnr, title, msg, deptId, imgPath);
		
			System.out.println(map.toString());
			
			
			String rtnCode = map.get("rtn_code").toString();
			String rtnMsg = map.get("rtn_msg").toString();
			
			System.out.println(rtnCode);
			System.out.println(rtnMsg);
			
			System.out.println("test end!!");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

}
