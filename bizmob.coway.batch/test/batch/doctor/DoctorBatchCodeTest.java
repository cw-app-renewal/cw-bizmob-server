package batch.doctor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.common.TestCommon;

import connector.sqlite.doctor.dao.DoctorWorkDao;


import batch.doctor.DoctorBatchCode;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class DoctorBatchCodeTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorBatchCode doctorBatchCode =  null;
	
	@Before
	public void setUp() throws Exception {
		doctorBatchCode = appContext.getBean(DoctorBatchCode.class);
	}
	
	@Test
	public void testCreateCodeDatabase() {
		
		try {
			
			doctorBatchCode.createCodeDatabase();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
