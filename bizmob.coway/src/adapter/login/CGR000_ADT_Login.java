package adapter.login;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;
import common.BizmobUtil;
import common.ResponseUtil;


@Adapter(trcode = {"CGR000"})
public class CGR000_ADT_Login extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGR000_ADT_Login.class);

	@Autowired private SAPAdapter 	sapAdapter;
	@Autowired private DBAdapter 	dbAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		String 				errCode 			= "ADAP0000";
		JsonNode 			reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 			reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		
		JsonNode 			reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);

		String 				trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		String				appKey				= reqHeaderNode.findPath("info_text").getValueAsText();
		
		String 				rfcName 			= reqHeaderNode.findPath("rfc_name").getValueAsText();
		
		try {
			
			if("".equals(rfcName)) {
				return ResponseUtil.makeFailResponse(obj, "RFCE", " RFC Function name을 확인해 주세요.", trCode, reqBodyNode, rfcName, null, this.getClass().getName());
			}
			
			SapCommonMapperException 	mapper 			= new SapCommonMapperException(trCode, dbAdapter);
			long						start			= System.currentTimeMillis();
			Map<String, Object> 		resMap 			= (Map<String, Object>) sapAdapter.execute(rfcName, reqBodyNode, mapper);
			long						end				= System.currentTimeMillis();
			
			JsonNodeFactory 			factory 		= JsonNodeFactory.instance;
			ObjectNode 					resRootNode 	= new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, AdapterUtil.ConvertJsonNode(resMap));
			
			BizmobUtil.putSessionData(obj, "appKey", appKey);
			return ResponseUtil.makeResponse(obj, resRootNode, trCode, (end - start), rfcName, reqBodyNode, this.getClass().getName());
		
		} catch (AdapterException e) {
			logger.error(e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return ResponseUtil.makeFailResponse(obj, e.getErrorCode(), e.getErrorMessage(), trCode, reqBodyNode, rfcName, e, this.getClass().getName());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, errCode, "요청처리에 실패하였습니다.", trCode, reqBodyNode, rfcName, e, this.getClass().getName());
		}
	}
}
