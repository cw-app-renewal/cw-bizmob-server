package batch.cody;

import org.junit.BeforeClass;

public class TestCommon {

	@BeforeClass
	public static void initialize() {
		System.setProperty("SMART_HOME", "D:/bizmob/workspace/bizmob.coway.SMART_HOME");
		
//		System.setProperty( Context.INITIAL_CONTEXT_FACTORY,"org.apache.xbean.spring.jndi.SpringInitialContextFactory" );
//		try {
//			new InitialContext();
//		} catch( NamingException ex ) {
//			ex.printStackTrace();
//		}
	}
}
