package sap.step;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class StepByStepClientTest {

	
	@BeforeClass
	public static void initialize() {
		System.setProperty("SMART_HOME", "D:/bizmob/workspace/bizmob.coway.SMART_HOME");
	}

	
	@Test
	public void testStep() {
		
		try {
			StepByStepClient.step1Connect();
			
			StepByStepClient.step2ConnectUsingPool();
			
			StepByStepClient.step3SimpleCall();
			
			StepByStepClient.step3WorkWithStructure();
			
			StepByStepClient.step4WorkWithTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
