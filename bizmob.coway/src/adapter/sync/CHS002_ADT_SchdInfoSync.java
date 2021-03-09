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
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;
import common.ResponseUtil;

@Adapter(trcode = { "CHS002" })
public class CHS002_ADT_SchdInfoSync extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CHS002_ADT_SchdInfoSync.class);
	
	@Autowired private SAPAdapter sapAdapter;
	@Autowired private DBAdapter dbAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		JsonNode 		reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 		reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 		reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 			trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		String[]		rfcNames			= {"ZSMT_IF_SP_HOME_RD051", "ZSMT_IF_SP_HOME_RD019"};
		
		try {	
			
			SapCommonMapperException 	mapper 			= new SapCommonMapperException(trCode, dbAdapter);
			long 						start 			= System.currentTimeMillis();
			Map<String, Object> 		resRD051Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD051", reqBodyNode, mapper); //ZSMT_IF_SP_HOME_RD051 - 하트예정 목록 조회(싱크)
			Map<String, Object> 		resRD019Map 	= (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_HOME_RD019", reqBodyNode, mapper); //ZSMT_IF_SP_HOME_RD019 - 조직정보 조회
			long 						end 			= System.currentTimeMillis();

			JsonNodeFactory 			factory 		= JsonNodeFactory.instance;
			ObjectNode 					resBodyNode 	= new ObjectNode(factory);		
			ObjectNode 					resRootNode 	= new ObjectNode(factory);

			//각 sync 항목을 추가
			resBodyNode.put("CHR051", AdapterUtil.ConvertJsonNode(resRD051Map));
			resBodyNode.put("CHR019", AdapterUtil.ConvertJsonNode(resRD019Map));
			
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
