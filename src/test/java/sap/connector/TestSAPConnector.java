package sap.connector;


import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcnc.bizmob.adapter.sap.SapConnector;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "/test/test-applicationContext.xml")
public class TestSAPConnector {

	@Autowired 
	SapConnector sapConnector;
	
	@BeforeClass
	public static void initialize() {
		System.setProperty("SMART_HOME", "D:/bizmob/workspace/bizmob.coway.SMART_HOME");
	}

	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testSapConnectorInstance() {
		assertNotNull(sapConnector);
	}
	
//	@Test
//	public void testExcuteSapInterface() throws AdapterException {
//		
//		try {
//			Map<String, Object>	reqMap = new HashMap<String, Object>();
//			
//			String key1 = "P_MATNR";
//			String value1 = "11184";
//			String key2 = "P_KUNNR"; 
//			String value2 = "63296"; //10832
//			String key3 = "P_PRODH";
//			String value3 = ""; //111001000010000000
//			String key4 = "P_DATAB"; 
//			String value4 = "20030601";
//			String key5 = "P_DATBI"; 
//			String value5 = "20030602";
//			reqMap.put(key1, value1);
//			reqMap.put(key2, value2);
//			reqMap.put(key3, value3);
//			reqMap.put(key4, value4);
//			reqMap.put(key5, value5);
//			
//			JCoFunction result = sapConnector.executeSapInterface("Z_MOB_FBIF_PRICE_INFO_N", reqMap);
//			JCoTable returnTable = result.getTableParameterList().getTable("RETURN");
//			assertTrue(returnTable.getString("TYPE").equals("S"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
}
