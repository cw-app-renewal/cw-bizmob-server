package batch.doctor;

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
public class DoctorBatchZipTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorBatchZip doctorBatZip;

	@Before
	public void setUp() throws Exception {
		doctorBatZip = appContext.getBean(DoctorBatchZip.class);
	}
	@Test
	public void testBatchZip() {

		try {
			doctorBatZip.doctorBatchDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
