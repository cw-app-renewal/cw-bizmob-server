package adapter.common;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.SapConnector;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import common.ResponseUtil;

@Adapter(trcode = { "COMMON" })
public class SapCommonAdapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(SapCommonAdapter.class);

	@Autowired
	private SAPAdapter sapAdapter;
	
	@Autowired
	private DBAdapter dbAdapter;
	
	@Autowired
	private SapConnector sapConnector;
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		//request
		JsonNode 			reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 			reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 			reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
		String				trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();			
		String				rfcName 			= reqHeaderNode.findPath("rfc_name").getValueAsText();

		try {			
			if("".equals(rfcName)) {
				return ResponseUtil.makeFailResponse(obj, "RFCE", " RFC Function name을 확인해 주세요.", trCode, reqBodyNode, rfcName, null, this.getClass().getName());
			}
			
			long 					start 		= System.currentTimeMillis();
			Map<String, Object> 	resMap 		= (Map<String, Object>) sapAdapter.execute(rfcName, reqBodyNode, new SapCommonMapper(trCode, dbAdapter));
			long 					end 		= System.currentTimeMillis();
			//response
			JsonNodeFactory 		factory 	= JsonNodeFactory.instance;
			
			ObjectNode 				resRootNode = new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, JsonUtil.toObjectNode(resMap));
			
			return ResponseUtil.makeResponse(obj, resRootNode, trCode, (end - start), rfcName, reqBodyNode, this.getClass().getName());
			
		} catch (AdapterException e) {
			logger.error("AdapterException = " + e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return ResponseUtil.makeFailResponse(obj, e.getErrorCode(), e.getErrorMessage(), trCode, reqBodyNode, rfcName, e, this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0002", "요청처리에 실패하였습니다.", trCode, reqBodyNode, rfcName, e, this.getClass().getName());
		}
	}
	
}
