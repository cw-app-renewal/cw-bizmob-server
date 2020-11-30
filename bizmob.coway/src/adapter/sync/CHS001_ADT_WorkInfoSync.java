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

@Adapter(trcode = { "CHS001" })
public class CHS001_ADT_WorkInfoSync extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CHS001_ADT_WorkInfoSync.class);
	
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
			
			logger.info("CHS001 Sync : " + reqBodyNode.toString());
			/*
			 * ZSMT_IF_SP_HOME_RD006 - 서비스 목록 조회 (싱크)
			 */
			Map<String, Object> resRD006Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD006", reqBodyNode, commonMapper);
			logger.debug("CHS001 Sync : ZSMT_IF_SP_HOME_RD006 execute !!");
			/*
			 * ZSMT_IF_SP_HOME_RD010 - 작업 상세 정보 조회 (싱크)
			 */
			Map<String, Object> resRD010Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD010", reqBodyNode, commonMapper);
			logger.debug("CHS001 Sync : ZSMT_IF_SP_HOME_RD010 execute !!");
			/*
			 * ZSMT_IF_SP_HOME_RD012 - 하트서비스 고객별 스케줄 내역 조회 (싱크)
			 */
			Map<String, Object> resRD012Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD012", reqBodyNode, commonMapper);
			logger.debug("CHS001 Sync : ZSMT_IF_SP_HOME_RD012 execute !!");			
	
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			//response body node
			ObjectNode resBodyNode = new ObjectNode(factory);		
			//response root node
			ObjectNode resRootNode = new ObjectNode(factory);

			//각 sync 항목을 추가
			resBodyNode.put("CHR006", AdapterUtil.ConvertJsonNode(resRD006Map));
			resBodyNode.put("CHR010", AdapterUtil.ConvertJsonNode(resRD010Map));
			resBodyNode.put("CHR012", AdapterUtil.ConvertJsonNode(resRD012Map));
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
