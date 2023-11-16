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
@Adapter(trcode = { "CHS001" })
public class CHS001_ADT_WorkInfoSync extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CHS001_ADT_WorkInfoSync.class);
	
	@Autowired private SAPAdapter sapAdapter;
	@Autowired private DBAdapter dbAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		JsonNode 		reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 		reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 		reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 			trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		String[]		rfcNames			= {"ZSMT_IF_SP_HOME_RD006", "ZSMT_IF_SP_HOME_RD010", "ZSMT_IF_SP_HOME_RD012"};
		
		try {	
			
			SapCommonMapperException 	mapper 			= new SapCommonMapperException(trCode, dbAdapter);
			long 						start 			= System.currentTimeMillis();
			Map<String, Object> 		resRD006Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD006", reqBodyNode, mapper); //ZSMT_IF_SP_HOME_RD006 - 서비스 목록 조회 (싱크)
			Map<String, Object> 		resRD010Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD010", reqBodyNode, mapper); //ZSMT_IF_SP_HOME_RD010 - 작업 상세 정보 조회 (싱크)
			Map<String, Object> 		resRD012Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD012", reqBodyNode, mapper); //ZSMT_IF_SP_HOME_RD012 - 하트서비스 고객별 스케줄 내역 조회 (싱크)
			long 						end 			= System.currentTimeMillis();

			JsonNodeFactory 			factory 		= JsonNodeFactory.instance;
			ObjectNode 					resBodyNode 	= new ObjectNode(factory);		
			ObjectNode 					resRootNode 	= new ObjectNode(factory);

			//각 sync 항목을 추가
			resBodyNode.put("CHR006", AdapterUtil.ConvertJsonNode(resRD006Map));
			resBodyNode.put("CHR010", AdapterUtil.ConvertJsonNode(resRD010Map));
			resBodyNode.put("CHR012", AdapterUtil.ConvertJsonNode(resRD012Map));
			
			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
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
