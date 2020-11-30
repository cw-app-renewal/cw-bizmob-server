package adapter.sync;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonMapperException;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

//@Adapter(trcode = { "CDS008" })
@Deprecated
public class CDS008_ADT_WorkHistorySync extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CDS008_ADT_WorkHistorySync.class);
	
	@Autowired
	SAPAdapter sapAdapter;
	@Autowired
	private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		try {
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode sessionNode = obj.get(JsonAdaptorObject.TYPE.META);
			JsonNode reqHeaderNode = reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode reqBodyNode = reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
			//trCode
			String trCode = reqHeaderNode.findPath("trcode").getValueAsText();		
			
			SapCommonMapperException commonMapper = new SapCommonMapperException(trCode, dbAdapter);
			
			logger.debug("CDS008 Sync request data :: " + reqBodyNode.toString());
			/*
			 *  ZSMT_IF_SP_CSDR_RD042 - AS 이력 
			 */
			Map<String, Object> resRD042Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD042", reqBodyNode, commonMapper);
			logger.debug("CDS008 Sync : ZSMT_IF_SP_CSDR_RD042 execute !!");
			
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			//
			ObjectNode resBodyNode = new ObjectNode(factory);
			resBodyNode.put("CDR042", AdapterUtil.ConvertJsonNode(resRD042Map));
			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
			//
			ObjectNode resRootNode = new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);
			
			return makeResponse(resObj, resRootNode);			
			
		} catch (AdapterException e) {
			logger.error("AdapterException = " + e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return makeFailReesponse(e.getErrorCode(), e.getErrorMessage());
		
		} catch (Exception e) {
			logger.error("Exception = ", e);
			return makeFailReesponse("IMPL0002", e.getLocalizedMessage());
		}
	}
}
