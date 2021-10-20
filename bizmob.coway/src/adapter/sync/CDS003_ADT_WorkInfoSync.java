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
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;
import common.ResponseUtil;
@Adapter(trcode = { "CDS003" })
public class CDS003_ADT_WorkInfoSync extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CDS003_ADT_WorkInfoSync.class);
	
	@Autowired private SAPAdapter sapAdapter;
	@Autowired private DBAdapter dbAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		JsonNode 		reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 		reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 		reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 			trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		String[]		rfcNames			= {"ZSMT_IF_SP_CSDR_RD006", "ZSMT_IF_SP_CSDR_RD007", "ZSMT_IF_SP_CSDR_RD008", 
											   "ZSMT_IF_SP_CSDR_RD017", "ZSMT_IF_SP_CSDR_RD018", "ZSMT_IF_SP_CSDR_RD026",
											   "ZSMT_IF_SP_CSDR_RD027", "ZSMT_IF_SP_CSDR_RD036", "ZSMT_IF_SP_CSDR_RD042"};
		try {		
			
			SapCommonMapperException 	commonMapper 	= new SapCommonMapperException(trCode, dbAdapter);
			long 						start 			= System.currentTimeMillis();
			Map<String, Object> 		resRD006Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD006", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD006 - 작업목록
			Map<String, Object> 		resRD007Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD007", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD007 - 작업 상품 목록
			Map<String, Object> 		resRD008Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD008", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD008	- 차용재고(바코드/비바코드)
			Map<String, Object> 		resRD017Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD017", reqBodyNode, commonMapper); //ZSMT_IP_SP_CSDR_RD017 - 설치 작업 상세
			Map<String, Object> 		resRD018Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD018", reqBodyNode, commonMapper); //ZSMT_IP_SP_CSDR_RD108 - AS 작업 상세
			Map<String, Object> 		resRD026Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD026", reqBodyNode, commonMapper); //ZSMT_IP_SP_CSDR_RD026 - 매변 작업 상세
			Map<String, Object> 		resRD027Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD027", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD027 - 반환 작업 상세
			Map<String, Object> 		resRD036Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD036", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD036 - 하트서비스 작업 상세
			Map<String, Object> 		resRD042Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_RD042", reqBodyNode, commonMapper); //ZSMT_IF_SP_CSDR_RD042 - AS 이력 
			long 						end 			= System.currentTimeMillis();
			
			JsonNodeFactory 			factory 		= JsonNodeFactory.instance;
			ObjectNode 					resBodyNode 	= new ObjectNode(factory);
			resBodyNode.put("CDR006", AdapterUtil.ConvertJsonNode(resRD006Map));
			resBodyNode.put("CDR007", AdapterUtil.ConvertJsonNode(resRD007Map));
			resBodyNode.put("CDR008", AdapterUtil.ConvertJsonNode(resRD008Map));
			resBodyNode.put("CDR017", AdapterUtil.ConvertJsonNode(resRD017Map));
			resBodyNode.put("CDR018", AdapterUtil.ConvertJsonNode(resRD018Map));
			resBodyNode.put("CDR026", AdapterUtil.ConvertJsonNode(resRD026Map));
			resBodyNode.put("CDR027", AdapterUtil.ConvertJsonNode(resRD027Map));
			resBodyNode.put("CDR036", AdapterUtil.ConvertJsonNode(resRD036Map));
			resBodyNode.put("CDR042", AdapterUtil.ConvertJsonNode(resRD042Map));
			
			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> 		returnMap 		= new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
			
			ObjectNode 					resRootNode 	= new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);
			
			return ResponseUtil.makeResponse(obj, resRootNode, trCode, (end - start), rfcNames.toString(), reqBodyNode, this.getClass().getName());		
			
		} catch (AdapterException e) {
			logger.error(e.getErrorMessage(), e);
			return ResponseUtil.makeFailResponse(obj, e.getErrorCode(), e.getErrorMessage(), trCode, reqBodyNode, e, this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0002", "요청처리에 실패하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		}
	}

}
