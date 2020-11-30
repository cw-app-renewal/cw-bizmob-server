package adapter.login;

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
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import connect.exception.ConnectClientException;
import connect.http.coway.CowayCommonHttpClient;
import connect.http.coway.data.MdmUserSyncRequestDO;


@Adapter(trcode = {"CGR000"})
public class CGR000_ADT_Login extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGR000_ADT_Login.class);

	@Autowired
	private SAPAdapter sapAdapter;

	@Autowired
	private DBAdapter dbAdapter;
	
	@Autowired
	private CowayCommonHttpClient cowayCommonHttpClient;

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		String errCode = "ADAP0000";
		
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
			logger.info("*************** Login RFC NAME = " + rfcName);
			logger.debug("*************** RFC IMPORT DATA = " + reqBodyNode.toString());
			
			//execute
			Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute(rfcName, reqBodyNode, new SapCommonMapperException(trCode, dbAdapter));
			//logger.trace("*************** RFC Return = " + resMap.toString());
			logger.debug("*************** RFC " + rfcName + " Return success");
		
			//MDM 연동
			String userId = reqBodyNode.findPath("I_PERNR").getTextValue();
			String userNm = resMap.get("O_SNAME").toString();
			String hphone = reqBodyNode.findPath("I_PHN_NO").getTextValue();
			String deptId = reqBodyNode.findPath("I_DEPT_ID").getTextValue();
			
			// 2014-06-11 kyryu : MDM 연동 제거
//			if(connectMDMUserSyncCheck(userId, userNm, hphone, deptId) == true) {
//				logger.debug("MDM Server User Sync Success !!");
//			}
			
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			ObjectNode resRootNode = new ObjectNode(factory);
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, AdapterUtil.ConvertJsonNode(resMap));
			logger.debug("*************** Response  = " + resRootNode.toString());
			
			return makeResponse(resObj, resRootNode);
		
		} catch (AdapterException e) {
			logger.error("AdapterException = " + e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return makeFailReesponse(e.getErrorCode(), e.getErrorMessage());

		} catch (Exception e) {
			logger.error("CGR000 Adapter Exception : ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
		
	}
	
//	private boolean connectMDMUserSyncCheck(String userId, String userNm, String hphone, String deptId) {
//		
//		try {
//			boolean syncEnable = Boolean.parseBoolean(SmartConfig.getString("mdm.user.sync.enable", "true"));
//			if(syncEnable == false) {
//				//mdm 사용자 sync 사용 여부
//				logger.debug("MDM User Sync disable !!");
//				return true;
//			}
//			
//			String serverUrl = SmartConfig.getString("mdm.server.url", "http://10.101.1.77:8080");
//			logger.debug("MDM Server URL = " + serverUrl);
//			
//			
//			MdmUserSyncRequestDO userInfo = new MdmUserSyncRequestDO();
//			userInfo.setUserId(userId);
//			userInfo.setUserNm(userNm);
//			userInfo.setHphone(hphone);
//			userInfo.setDeptId(deptId);
//			logger.debug("MdmUserSyncRequestDO info :: " + userInfo.toString());
//			
//			JsonNode resultJson = cowayCommonHttpClient.mdmUserSyncCheck(serverUrl, userInfo);
//			logger.debug("MDM Server UserSync result :: " + resultJson.toString());
//			String resultString = resultJson.findPath("result").getTextValue();
//			if(resultString.equals("ok") == true) {
//				return true;
//			}
//			
//		} catch (ConnectClientException e) {
//			logger.error("MDM Server Connect http error :: ", e);
//			return false;
//		} catch (Exception e) {
//			logger.error("MDM Server Connect normal error :: ", e);
//			return false;
//		}
//		
//		return false;
//	}


}
