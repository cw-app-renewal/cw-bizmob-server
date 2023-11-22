package adapter.sap;

import org.junit.Test;

public class SapConnectionClientTest {

	
//	@BeforeClass
//	public static void initialize() {
//		System.setProperty("SMART_HOME", "D:/bizmob/workspace/bizmob.coway.SMART_HOME");
//	}

	
	@Test
	public void testStep() {
		
		try {
			SapConnectionClient.step1Connect();
			
			SapConnectionClient.step2ConnectUsingPool();
			
			SapConnectionClient.step3SimpleCall();
			
			SapConnectionClient.step3WorkWithStructure();
			
			SapConnectionClient.step4WorkWithTable();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
