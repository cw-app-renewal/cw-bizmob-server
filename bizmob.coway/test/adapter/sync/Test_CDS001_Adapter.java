package adapter.sync;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import adapter.common.TestAdapter;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class Test_CDS001_Adapter extends TestAdapter {

	@Autowired
	private CDS001_ADT_CodeTableVerChecker adapter;
	private JsonAdaptorObject obj = new JsonAdaptorObject();

	@Before
	public void setUp() {
		//TODO 요청데이터 입력. JsonAdaptorObject.TYPE.REQUEST

	}

	@Before
	public void setSession() {
		// TODO 세션 데이터 입력. JsonAdaptorObject.TYPE.META

	}

	@Test
	public void testAdapterBeanInstance() {
		// test creating adapter bean
		assertNotNull(adapter);
	}

	@Test
	public void testOnProcess() {

		// test adapter process.
		JsonAdaptorObject res = adapter.onProcess(obj);
		assertNotNull("Must not return a null response object", res);

		System.out.println(res.toString());

		// TODO response 검즘 
		/* 검증 샘플
		 * HDP000Response response = new HDP000Response(res);
		 * assertTrue("Result of Response should be True", response.getHeader().getResult());
		 */
	}

}
