package adapter.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.TestAdapter;
import adapter.model.header.DefaultHeader;
import adapter.model.sample.TrCode.TrCodeRequest;
import adapter.model.sample.TrCode.TrCodeRequest_Body;
import adapter.model.sample.TrCode.TrCodeResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class TrCode_SampleTest extends TestAdapter {

	@Autowired
	private TrCode_Sample adapter;
	
	TrCodeRequest request;
	
	@Before
	public void setUp() {
		// 개발자 입력.
		request = new TrCodeRequest();
		DefaultHeader header = new DefaultHeader();
		header.setTrcode("TRCODE");
		header.setResult(true);
		request.setHeader(header);
		request.setBody(new TrCodeRequest_Body());
		request.getBody().setData("Hi~~");
	}

	@Test
	public void testOnProcess() {
		// test adapter process.
		JsonAdaptorObject obj = new JsonAdaptorObject();
		obj.put(JsonAdaptorObject.TYPE.REQUEST, request.toJsonNode());
		
		JsonAdaptorObject res = adapter.onProcess(obj);
		assertNotNull("Must not return a null response object", res);
		
		System.out.println(res.toString());
		TrCodeResponse response = new TrCodeResponse(res);
		assertTrue("Result of Response should be True", response.getHeader().getResult());
	}
	
}
