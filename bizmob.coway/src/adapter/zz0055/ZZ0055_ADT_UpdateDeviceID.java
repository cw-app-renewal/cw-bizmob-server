package adapter.zz0055;

import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.portal.auth.model.DeviceInfo;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import common.ResponseUtil;

@Adapter(trcode = { "ZZ0055" })
public class ZZ0055_ADT_UpdateDeviceID extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(ZZ0055_ADT_UpdateDeviceID.class);
	@Autowired private DBAdapter dbAdapter;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 	reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 	reqBodyNode 		= reqRootNode.findPath(Codes._JSON_MESSAGE_BODY);
		JsonNode 	reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		String 		trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		
		try {		
			String 		oldDeviceId 	= reqBodyNode.get("oldDeviceId").getTextValue();
			String 		newDeviceId	 	= reqBodyNode.get("newDeviceId").getTextValue();
			
			HashMap<String,Object>	param 			= new HashMap<String, Object>();
			String 					partOfDeviceId 	= new StringBuilder(oldDeviceId.substring(4, 8)).append("-").append(oldDeviceId.substring(8, 22)).toString();	//64비트로 빌드 후 변경된 아이디 중 32비트 앱 디바이스 아이디와 동일한 부분 추출
			param.put("partOfDeviceId", partOfDeviceId);
			param.put("oldDeviceId", oldDeviceId);
			
			long 					start 			= System.currentTimeMillis();
			DeviceInfo 				deviceInfo 		= (DeviceInfo) dbAdapter.selectOne("BizMOBDB", "ZZ0055.ZZ0055GetDeviceId", param);
			if( deviceInfo != null ){
				param.put("oldDeviceId", deviceInfo.getDeviceId());
				param.put("newDeviceId", newDeviceId);
				int updateResult = dbAdapter.update("BizMOBDB", "ZZ0055.ZZ0055UpdateDeviceId", param);
				if(updateResult < 1){
					return ResponseUtil.makeFailResponse(obj, "ERR1001", "디바이스ID 갱신에 실패하였습니다.", trCode, reqBodyNode, null, this.getClass().getName());
				}
			}
			long 					end 			= System.currentTimeMillis();
			JsonNodeFactory 		factory 		= JsonNodeFactory.instance;
			ObjectNode 				resRootNode 	= new ObjectNode(factory);
            ObjectNode 				resBodyNode 	= resRootNode.objectNode();
			
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);
			
			JsonAdaptorObject resObj = new JsonAdaptorObject();
	        return ResponseUtil.makeResponse(resObj, resRootNode, trCode, (end - start), reqBodyNode,this.getClass().getName());			
		} catch (Exception e) {
	        logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
			
		}
	}

}
