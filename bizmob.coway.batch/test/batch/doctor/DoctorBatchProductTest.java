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

import batch.doctor.DoctorBatchProduct;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-batch-applicationContext.xml")
public class DoctorBatchProductTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	DoctorBatchProduct doctorBatchProduct =  null;

	@Before
	public void setUp() throws Exception {
		doctorBatchProduct = appContext.getBean(DoctorBatchProduct.class);
	}
	@Test
	public void testCreateProductDatabase() {
		try {
			
			doctorBatchProduct.createProductDatabase();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
