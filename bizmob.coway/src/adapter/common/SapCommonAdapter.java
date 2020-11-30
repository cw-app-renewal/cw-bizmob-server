package adapter.common;

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

@Adapter(trcode = { "COMMON" })
public class SapCommonAdapter extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(SapCommonAdapter.class);

	@Autowired
	private SAPAdapter sapAdapter;
	
	@Autowired
	private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {			
			//request
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode sessionNode = obj.get(JsonAdaptorObject.TYPE.META);
			JsonNode reqHeaderNode = reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode reqBodyNode = reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
			
			//trCode
			String trCode = reqHeaderNode.findPath("trcode").getValueAsText();			
			//rfc name
			String rfcName = reqHeaderNode.findPath("rfc_name").getValueAsText();
			if(rfcName == null || rfcName.equals("") == true) {
				return makeFailReesponse("IMPL0001", " RFC Function name을 확인해 주세요.");
			}
			logger.info("*************** RFC NAME = " + rfcName);
			logger.debug("*************** RFC IMPORT DATA = " + reqBodyNode.toString());
			
			//execute
			Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute(rfcName, reqBodyNode, new SapCommonMapper(trCode, dbAdapter));
			//logger.trace("*************** RFC Return = " + resMap.toString());
			logger.debug("*************** RFC " + rfcName + " Return success");
		
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			ObjectNode resRootNode = new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, AdapterUtil.ConvertJsonNode(resMap));
			//logger.trace("*************** Response  = " + resRootNode.toString());
			
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
