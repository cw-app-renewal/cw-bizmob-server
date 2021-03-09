package adapter.sync;

import java.util.HashMap;
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
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;

//@Adapter(trcode = { "CDS007" })
@Deprecated
public class CDS007_ADT_WorkDetailSync extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CDS007_ADT_WorkDetailSync.class);
	
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
			
			logger.debug("CDS007 Sync request data :: " + reqBodyNode.toString());
			/*
			 * ZSMT_IP_SP_CSDR_RD017 - 설치 작업 상세
			 */
			Map<String, Object> resRD017Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD017", reqBodyNode, commonMapper);
			logger.debug("CDS007 Sync : ZSMT_IF_SP_CSDR_RD017 execute !!");
			/*
			 * ZSMT_IP_SP_CSDR_RD108 - AS 작업 상세
			 */
			Map<String, Object> resRD018Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD018", reqBodyNode, commonMapper);
			logger.debug("CDS007 Sync : ZSMT_IF_SP_CSDR_RD018 execute !!");
			/*
			 * ZSMT_IP_SP_CSDR_RD026 - 매변 작업 상세
			 */
			Map<String, Object> resRD026Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD026", reqBodyNode, commonMapper);
			logger.debug("CDS007 Sync : ZSMT_IF_SP_CSDR_RD026 execute !!");
			/*
			 * ZSMT_IF_SP_CSDR_RD027 - 반환 작업 상세
			 */
			Map<String, Object> resRD027Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD027", reqBodyNode, commonMapper);
			logger.debug("CDS007 Sync : ZSMT_IF_SP_CSDR_RD027 execute !!");
			/*
			 * ZSMT_IF_SP_CSDR_RD036 - 하트서비스 작업 상세
			 */
			Map<String, Object> resRD036Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD036", reqBodyNode, commonMapper);
			logger.debug("CDS007 Sync : ZSMT_IF_SP_CSDR_RD036 execute !!");

			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			//
			ObjectNode resBodyNode = new ObjectNode(factory);
			resBodyNode.put("CDR017", AdapterUtil.ConvertJsonNode(resRD017Map));
			resBodyNode.put("CDR018", AdapterUtil.ConvertJsonNode(resRD018Map));
			resBodyNode.put("CDR026", AdapterUtil.ConvertJsonNode(resRD026Map));
			resBodyNode.put("CDR027", AdapterUtil.ConvertJsonNode(resRD027Map));
			resBodyNode.put("CDR036", AdapterUtil.ConvertJsonNode(resRD036Map));
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
