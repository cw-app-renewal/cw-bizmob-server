package adapter.appstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import adapter.common.TestAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class CGR221_AppstoreLoginServiceTest extends TestAdapter {

	@Autowired
	private CGR221_AppstoreLoginService adapter;
	
	@Test
	public void testAppstoreLogin() {
		
		try {
		
			String I_PERNR = "1";
			String I_PWD = "1";
			String I_PHN_NO = "1";
			
			String rtn = adapter.appstoreLogin(I_PERNR, I_PWD, I_PHN_NO);
			
			System.out.println(rtn);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
