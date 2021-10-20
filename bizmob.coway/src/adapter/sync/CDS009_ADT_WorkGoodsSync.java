package adapter.sync;

import java.util.HashMap;
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
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;
//@Adapter(trcode = { "CDS009" })
@Deprecated
public class CDS009_ADT_WorkGoodsSync extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CDS009_ADT_WorkGoodsSync.class);
	
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
			
			logger.debug("CDS009 Sync request data :: " + reqBodyNode.toString());
			/*
			 * ZSMT_IF_SP_CSDR_RD007 - 작업 상품 목록
			 */
			Map<String, Object> resRD007Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD007", reqBodyNode, commonMapper);
			logger.debug("CDS009 Sync : ZSMT_IF_SP_CSDR_RD007 execute !!");
			/*
			 * ZSMT_IF_SP_CSDR_RD008	- 차용재고(바코드/비바코드)
			 */
			Map<String, Object> resRD008Map = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD008", reqBodyNode, commonMapper);
			logger.debug("CDS009 Sync : ZSMT_IF_SP_CSDR_RD008 execute !!");

			
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			//
			ObjectNode resBodyNode = new ObjectNode(factory);
			resBodyNode.put("CDR007", AdapterUtil.ConvertJsonNode(resRD007Map));
			resBodyNode.put("CDR008", AdapterUtil.ConvertJsonNode(resRD008Map));
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
			return makeFailResponse(e.getErrorCode(), e.getErrorMessage());
		
		} catch (Exception e) {
			logger.error("Exception = ", e);
			return makeFailResponse("IMPL0002", e.getLocalizedMessage());
		}
	}
}
