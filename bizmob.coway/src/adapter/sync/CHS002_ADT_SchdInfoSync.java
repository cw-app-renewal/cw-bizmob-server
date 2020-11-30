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
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@Adapter(trcode = { "CHS002" })
public class CHS002_ADT_SchdInfoSync extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CHS002_ADT_SchdInfoSync.class);
	
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
			//
			String trCode = reqHeaderNode.findPath("trcode").getValueAsText();
			
			SapCommonMapperException commonMapper = new SapCommonMapperException(trCode, dbAdapter);

			logger.info("CHS002 Sync : " + reqBodyNode.toString());

			/*
			 * ZSMT_IF_SP_HOME_RD051 - 하트예정 목록 조회(싱크)
			 */
			Map<String, Object> resRD051Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD051", reqBodyNode, commonMapper);
			logger.debug("CHS002 Sync : ZSMT_IF_SP_HOME_RD051 execute !!");
			/*
			 * ZSMT_IF_SP_HOME_RD019 - 조직정보 조회
			 */
			Map<String, Object> resRD019Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD019", reqBodyNode, commonMapper);
			logger.debug("CHS002 Sync : ZSMT_IF_SP_HOME_RD019 execute !!");	
			
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			//response body node
			ObjectNode resBodyNode = new ObjectNode(factory);		
			//response root node
			ObjectNode resRootNode = new ObjectNode(factory);

			//각 sync 항목을 추가
			resBodyNode.put("CHR051", AdapterUtil.ConvertJsonNode(resRD051Map));
			resBodyNode.put("CHR019", AdapterUtil.ConvertJsonNode(resRD019Map));
			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
			//
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
