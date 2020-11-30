package adapter.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;

public class TestAdapter {

	@BeforeClass
	public static void initialize() {
		System.setProperty("SMART_HOME", "D:/bizmob_coway/workspace/bizmob.coway.SMART_HOME");
		
//		System.setProperty( Context.INITIAL_CONTEXT_FACTORY,"org.apache.xbean.spring.jndi.SpringInitialContextFactory" );
//		try {
//			new InitialContext();
//		} catch( NamingException ex ) {
//			ex.printStackTrace();
//		}
	}
}
