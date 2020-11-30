package adapter.common;

import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

public class SystemPropertyTest {

	
	
	@Test
	public void testByForeach() {
		
		Properties props = System.getProperties();
		for(Entry entry : props.entrySet()) {
			System.out.println(entry);
		}		
	}
	
	
	@Test
	public void testByEmumeration() {
		
		Properties props = System.getProperties();
		Enumeration keys = props.keys();
		while(keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + " = " + System.getProperty(key));
		}
		
	}
}
 