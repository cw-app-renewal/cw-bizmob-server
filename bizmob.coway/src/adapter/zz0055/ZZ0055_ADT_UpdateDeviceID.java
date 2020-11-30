package adapter.zz0055;

import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.portal.auth.model.DeviceInfo;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.adapter.header.HWHeader;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@Adapter(trcode = { "ZZ0055" })
public class ZZ0055_ADT_UpdateDeviceID extends AbstractTemplateAdapter implements
		IAdapterJob {

	private static final ILogger logger = LoggerService.getLogger(ZZ0055_ADT_UpdateDeviceID.class);
	@Autowired
	private DBAdapter dbAdapter;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        JsonAdaptorObject resObj = new JsonAdaptorObject();
        
		try {
			JsonNode 	reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode 	reqBodyNode = reqRootNode.findPath(Codes._JSON_MESSAGE_BODY);
            HWHeader 	resHeader = new HWHeader(reqRootNode.findPath(Codes._JSON_MESSAGE_HEADER));
			
			String oldDeviceId 		= reqBodyNode.get("oldDeviceId").getTextValue();
			String newDeviceId	 	= reqBodyNode.get("newDeviceId").getTextValue();
			
			HashMap<String,Object>	param = new HashMap<String, Object>();
			String partOfDeviceId = new StringBuilder(oldDeviceId.substring(4, 8)).append("-").append(oldDeviceId.substring(8, 22)).toString();	//64비트로 빌드 후 변경된 아이디 중 32비트 앱 디바이스 아이디와 동일한 부분 추출
			param.put("partOfDeviceId", partOfDeviceId);
			param.put("oldDeviceId", oldDeviceId);
			
			DeviceInfo deviceInfo = (DeviceInfo) dbAdapter.selectOne("BizMOBDB", "ZZ0055.ZZ0055GetDeviceId", param);
			if( deviceInfo != null ){
				param.put("oldDeviceId", deviceInfo.getDeviceId());
				param.put("newDeviceId", newDeviceId);
				int updateResult = dbAdapter.update("BizMOBDB", "ZZ0055.ZZ0055UpdateDeviceId", param);
				if(updateResult<1){
					 resObj = new JsonAdaptorObject();
			         resObj.put(JsonAdaptorObject.TYPE.RESULT,
			                       new SimpleJsonResponse(false, "ZZ0055ERR1001", "fail to update deviceId", null).toJson());
			         return resObj;
				}
			}
			
			ObjectNode resRootNode = new ObjectNode(factory);
            ObjectNode resBodyNode = resRootNode.objectNode();
            resHeader.setResult(true);
			resRootNode.put( Codes._JSON_MESSAGE_BODY, resBodyNode);
			resObj = new JsonAdaptorObject();
			resHeader.buildJsonHeader(resRootNode);
	        resObj.put( JsonAdaptorObject.TYPE.RESPONSE, resRootNode );
	        resObj.put(JsonAdaptorObject.TYPE.RESULT, SimpleJsonResponse.getSimpleSuccessResponse().toJson());
	        
	        return resObj;
		} catch (Exception e) {
			logger.error("어댑터 처리 중 오류가 발생 했습니다.",e);
			resObj = new JsonAdaptorObject();
	         resObj.put(JsonAdaptorObject.TYPE.RESULT,
	                       new SimpleJsonResponse(false, "ZZ0055IMPL0001", "fail to update deviceId", null).toJson());
	         return resObj;
		}
	}

}
