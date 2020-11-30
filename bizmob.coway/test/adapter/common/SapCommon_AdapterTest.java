package adapter.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class SapCommon_AdapterTest extends TestAdapter {

	@Autowired
	private SapCommonAdapter adapter;
	private JsonAdaptorObject obj = new JsonAdaptorObject();
	
	@Before
	public void setUp() throws IOException {
		//TODO 요청데이터 입력. JsonAdaptorObject.TYPE.REQUEST
		InputStream in = new FileInputStream(new File("./test/data/homecare/ZSMT_IF_SP_HOME_WR088.json"));
		
		ObjectMapper om = new ObjectMapper();
		JsonNode root = om.readTree(in);
		obj.put(JsonAdaptorObject.TYPE.REQUEST, root);

	}
	
	@Before
	public void setSession() {
		// TODO 세션 데이터 입력. JsonAdaptorObject.TYPE.META

	}
	
	
	@Test
	public void testOnProcess() {
		// test adapter process.
		JsonAdaptorObject res = adapter.onProcess(obj);
		
		System.out.println(res.toString());
	}


}
