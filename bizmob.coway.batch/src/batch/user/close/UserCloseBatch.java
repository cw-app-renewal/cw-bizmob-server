package batch.user.close;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import batch.user.close.data.UserCloseInfoDO;

import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import adapter.common.SapCommonMapper;
import connect.exception.ConnectClientException;
import connect.http.coway.CowayCommonHttpClient;
import connect.http.coway.data.DeleteDeviceListRequestDO;
import connect.http.coway.data.DeleteDeviceListResponseDO;
import connect.http.coway.data.DeleteUserListRequestDO;
import connect.http.coway.data.DeleteUserListResponseDO;
import connect.http.coway.data.MdmUserResignRequestDO;

public class UserCloseBatch {

	private ILogger logger = LoggerService.getLogger(UserCloseBatch.class);
	
	@Autowired
	SAPAdapter sapAdapter;
	
	@Autowired
	private CowayCommonHttpClient cowayCommonHttpClient;
	
	private List<UserCloseInfoDO> userCloseInfoList = null;
	
	
	public boolean executeUserCloseBatch() {
		
		
		try {
			
			//
			String batchEnable = SmartConfig.getString("batch.user.close.enable", "true");
			logger.debug("batch.user.close.enable :: " + batchEnable);
			if(Boolean.parseBoolean(batchEnable) == false) {
				logger.debug("user close batch disabled !! (only server #1 possible)");
				return false;
			}
			
			//SAP 실행 : URL Call로 변경하면서 주석처리함.
			//sapAdapter.execute("ZSMT_IF_SP_CR301", null, new SapMapper());
			
			//smart-admin server ip
			String serverIp = SmartConfig.getString("smart.admin.server.ip", "http://127.0.0.1:9080");
			logger.debug("smart-admin server ip = " + serverIp);
			
			String mdmServerIp = SmartConfig.getString("mdm.server.url", "http://10.101.1.77:8080");
			logger.debug("MDM Server URL = " + mdmServerIp);
			
			// ZSMT_IF_SP_CR301 펑션 호출(URL Call)
			if( !getUserCloseList(serverIp) ){
				logger.debug("getUserCloseList failed!!!!!!!");
				return false;
			}
			//
			
			logger.debug("userCloseInfoList length = " + userCloseInfoList.size());
			
			for(UserCloseInfoDO userInfo : userCloseInfoList) {
				
				logger.debug("delete Batch UserInfo :: " + userInfo.toString());
				
				
				if(userInfo.getGBN().equals("A") == true || userInfo.getGBN().equals("1") == true) {
					//디바이스 삭제
					//smart-admin 디바이스 삭제
					deleteDeviceInfoAdminServer(serverIp, userInfo.getPERNR(), userInfo.getPMNUM());			
				}
				
				if(userInfo.getGBN().equals("A") == true) {
					//smart-admin 사용자 삭제
					if(deleteUserInfoAdminServer(serverIp, userInfo.getPERNR()) == false) {
						//사용자 삭제에 실패하면, 디바이스, MDM 연동도 하지 않는다. 
						continue;
					}
				
					// 2014-06-19 Coway 요청으로 퇴사자 배치 연동 정지
					//mdm 사용자 삭제
					// 2018-01-26, 이재성, 퇴직자 배치삭제 다시 살려놓은
					deleteUserInfoMdmServer(mdmServerIp,  userInfo.getPERNR(), userInfo.getSNAME(), userInfo.getPHN_NO());
					
				}

			}			
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}

		return true;
	}
	
	private boolean deleteUserInfoAdminServer(String serverIp, String userId) {
		
		try {
		
			DeleteUserListRequestDO user = new DeleteUserListRequestDO();
			user.setRowIdNoArray01(userId);
			
			JsonNode resultJson = cowayCommonHttpClient.deleteUserList(serverIp, user);
			logger.debug("delete user response :: " + resultJson.toString());
			
			DeleteUserListResponseDO result = new DeleteUserListResponseDO(resultJson);
			
			if(result.getResultCode().equals("SUCCESS") == true) {
				return true;
			} else {
				logger.debug("delete user fail result :: " + result.getMessageCode());
				return false;
			}
					
		} catch (ConnectClientException e) {
			logger.debug("delete user info error :: ", e);
			return false;
		}
	}
	
	
	private boolean deleteDeviceInfoAdminServer(String serverIp, String userId, String deviceAuthNo) {
		
		try {
			DeleteDeviceListRequestDO device = new DeleteDeviceListRequestDO();
			device.setUserId(userId);
			device.setWithPublicFlag("Y");
			device.setDeviceAuthNoArray01(deviceAuthNo);
		
			JsonNode resultJson = cowayCommonHttpClient.deleteDeviceList(serverIp, device);
			logger.debug("delete device response :: " + resultJson.toString());
			
			DeleteDeviceListResponseDO result = new DeleteDeviceListResponseDO(resultJson);
			
			if(result.getResultCode().equals("SUCCESS") == true) {
				return true;
			} else {
				logger.debug("delete device fail result :: " + result.getMessageCode());
				return false;
			}
		
		} catch (ConnectClientException e) {
			logger.debug("delete user info error :: ", e);
			return false;
		}
	}
	
	private boolean deleteUserInfoMdmServer(String serverIp, String userId, String userNm, String hphone) {
		
		try {
			
			boolean syncEnable = Boolean.parseBoolean(SmartConfig.getString("mdm.user.sync.enable", "true"));
			if(syncEnable == false) {
				//mdm 사용자 sync 사용 여부
				logger.debug("MDM User Sync disable !!");
				return false;
			}
			
			MdmUserResignRequestDO user = new MdmUserResignRequestDO();
			user.setUserId(userId);
			user.setUserNm(userNm);
			user.setHphone(hphone);
			logger.debug("MDM Server User Resign info = " + user.toString());
			
			JsonNode resultJson = cowayCommonHttpClient.mdmUserResignCheck(serverIp, user);
			logger.debug("MDM Server User Resign response :: " + resultJson.toString());
			
			logger.debug("MDM Server User Resign result :: " + resultJson.toString());
			String resultString = resultJson.findPath("result").getTextValue();
			if(resultString.equals("ok") == true) {
				return true;
			}
		
		} catch (ConnectClientException e) {
			logger.debug("MDM Server User Resign error :: ", e);
			return false;
		}
		
		return false;
	}
	
	private boolean getUserCloseList(String ServerIp) throws Exception {
		
		ObjectNode reqBodyNode = JsonUtil.objectNode();
		//execute 
		@SuppressWarnings("unchecked")
		Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CR301", reqBodyNode, new SapCommonMapper("", null));
		JsonNode resultJson = JsonUtil.toObjectNode(resMap);
		
		
//		String CR301 = 	 "{ "
//							+ "\"header\" : { "
//									+ "\"error_code\" : \"\", "
//									+ "\"error_text\" : \"\", "
//									+ "\"info_text\" : \"\", "
//									+ "\"login_session_id\" : \"\", "
//									+ "\"message_version\" : \"\", "
//									+ "\"result\" : true, "
//									+ "\"trcode\" : \"COMMON\", "
//									+ "\"rfc_name\" : \"ZSMT_IF_SP_CR301\" "
//								+ "}, "
//							+ "\"body\" : { "
//								+ "} "
//							+ "} ";
//			
//			JsonNode resultJson = cowayCommonHttpClient.userCloseCheck(ServerIp, CR301);
//			System.out.println("ZSMT_IF_SP_CR301 response :: " + resultJson.toString());
			
			//String CR301Result = "{\"body\":{\"E_RETURN\":{\"MESSAGE\":\"\",\"TYPE\":\"T\"},\"O_ITAB\":[{\"PMNUM\":\"26262248\",\"PHN_NO\":\"01098931106\",\"SNAME\":\"최기호\",\"PERNR\":\"20262248\",\"GBN\":\"A\"},{\"PMNUM\":\"21071122\",\"PHN_NO\":\"01031561689\",\"SNAME\":\"박동첨\",\"PERNR\":\"20210122\",\"GBN\":\"A\"}]},\"header\":{\"error_code\":\"\",\"error_text\":\"\",\"info_text\":\"\",\"login_session_id\":\"\",\"message_version\":\"\",\"result\":true,\"trcode\":\"COMMON\",\"rfc_name\":\"ZSMT_IF_SP_CR301\"}}";
			//ObjectMapper om = new ObjectMapper();
			//JsonNode resRoot = om.readTree(CR301Result);
			
			JsonNode resReturn = resultJson.findValue("E_RETURN");
			JsonNode closerList = resultJson.findValue("O_ITAB");
			
			if( !resReturn.path("TYPE").getTextValue().equals("T") ){
				// error
				logger.debug("ZSMT_IF_SP_CR301 is failed!!!");
				return false;
			}
			
			Iterator<JsonNode> iterator = closerList.iterator();
			userCloseInfoList = new ArrayList<UserCloseInfoDO>();
			
			while( iterator.hasNext() ){
				JsonNode array = iterator.next();
				UserCloseInfoDO _close = new UserCloseInfoDO();
				
				_close.setPMNUM(array.path("PMNUM").getTextValue());
				_close.setPHN_NO(array.path("PHN_NO").getTextValue());
				_close.setSNAME(array.path("SNAME").getTextValue());
				_close.setPERNR(array.path("PERNR").getTextValue());
				_close.setGBN(array.path("GBN").getTextValue());
				
				userCloseInfoList.add(_close);
			}
			
			return true;
	}
	
	class SapMapper extends AbstractSapMapper {

		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object param) throws AdapterException {
			
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
		
			JCoTable userInfoTable = function.getTableParameterList().getTable("O_ITAB");

			logger.debug(">>>> userInfoTable Data Start <<<<");
			logger.debug(userInfoTable.toString());
			logger.debug(">>>> userInfoTable Data End<<<<");
			
			userCloseInfoList = convertSapTableToObjectList(userInfoTable, UserCloseInfoDO.class);
			logger.debug("close user info sap record count = " + userCloseInfoList.size());
			
			return null;
		}

		@Override
		public void verifySapResult(JCoFunction function) throws AdapterException {
			
			JCoParameterList paramList = function.getExportParameterList();
			JCoStructure struct = paramList.getStructure("E_RETURN");
			String type = struct.getString("TYPE");
			if(type.equals("T") != true) {
				throw new AdapterException("", struct.getString("MESSAGE"));
			}
		}
	}
}
