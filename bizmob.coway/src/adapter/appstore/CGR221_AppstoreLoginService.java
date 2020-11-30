package adapter.appstore;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonMapperException;

import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;

import connect.exception.ConnectClientException;
import connect.http.coway.CowayCommonHttpClient;
import connect.http.coway.data.InitDeviceAuthRequestDO;
import connect.http.coway.data.InitDeviceAuthResponseDO;
import connect.http.coway.data.InsertDeviceRequestDO;
import connect.http.coway.data.InsertDeviceResponseDO;
import connect.http.coway.data.InsertUserRequestDO;
import connect.http.coway.data.InsertUserResponseDO;
import connect.http.coway.data.MdmUserSyncRequestDO;
import connect.http.coway.data.UpdateUserRequestDO;
import connect.http.coway.data.UpdateUserResponseDO;

public class CGR221_AppstoreLoginService {

	private ILogger logger = LoggerService.getLogger(CGR221_AppstoreLoginService.class);

	@Autowired
	private SAPAdapter sapAdapter;
	
	@Autowired
	private DBAdapter dbAdapter;
	
	@Autowired
	private CowayCommonHttpClient cowayCommonHttpClient;

	public String appstoreLogin(String I_PERNR, String I_PWD, String I_PHN_NO) {
		
		try {
			logger.debug("*** appstore Login start !! :: userId=[" + I_PERNR + "], phoneNo=[" + I_PHN_NO + "]" );
			
			String appstoreLoginEnable = SmartConfig.getString("appstore.login.enable", "true");
			if(Boolean.parseBoolean(appstoreLoginEnable) == false) {
				if(I_PERNR.equals("1") == true && I_PWD.equals("1") == true && I_PHN_NO.equals("1") == true) {
					//테스트를 위해 무조건 넘어감
					logger.debug("appstore Login disabled !! (by-pass)");
					return "true";	
				}
			}
			
			Map<String, String> reqMap = new HashMap<String, String>();
			Map<String, Object> resMap = null;
			
			reqMap.put("I_PERNR", I_PERNR);
			reqMap.put("I_PWD", I_PWD);
			reqMap.put("I_PHN_NO", I_PHN_NO);
			logger.debug("*** appstroe login info = " + reqMap.toString()); 
			
			//sap 인증
			try {
				//execute
				logger.info("*************** Appstore Login RFC NAME = " + "ZSMT_IF_SP_CR221");
				resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CR221", AdapterUtil.ConvertJsonNode(reqMap), new SapCommonMapperException("AppLog", dbAdapter));
				logger.debug("***  RFC Return = " + resMap.toString());
			} catch (AdapterException e) {
				logger.error("ZSMT_IF_SP_CR221 error :: ", e);
				return e.getErrorMessage();
			}
			
			String pernr = resMap.get("O_PERNR").toString();		//사용자번호
			String sname = resMap.get("O_SNAME").toString();		//사용자이름
			String pwd = resMap.get("O_PWD").toString();			//패스워드
			String phn_no = resMap.get("O_PHN_NO").toString();		//전화번호
			String pmnum = resMap.get("O_PMNUM").toString();		//인증번호
			String persg = resMap.get("O_PERSG").toString();		//사용자 그룹구분(CODY/CSDR/HOME)
			String wcode = resMap.get("O_WCODE").toString();		//직위(직책)코드
			String werks = resMap.get("O_WERKS").toString();		//회사코드
			String deptId = resMap.get("O_DEPT_ID").toString();		//부서코드
			
			
			//smart-admin server ip
			String serverIp = SmartConfig.getString("smart.admin.server.ip", "http://127.0.0.1:9080");
			logger.debug("smart-admin server ip = " + serverIp);

			//smart-admin 사용자 등록
			String userResult = insertUserInfoAdminServer(serverIp, pernr, sname, pernr, persg, phn_no);
			if(userResult.equals("SUCCESS") != true) {
				return userResult;
			}
			
			//smart-admin 디바이스 등록
			String deviceResult = insertDeviceInfoAdminServer(serverIp, pernr, pmnum);
			if(deviceResult.equals("SUCCESS") != true) {
				return deviceResult;
			}
			
			//MDM 연동
			String userId = pernr;
			String userNm = sname;
			String hphone = phn_no;
			String positionId = wcode;
			String companyId = werks;
			
			if(connectMDMUserSyncCheck(userId, userNm, hphone, deptId, positionId, companyId) == true) {
				logger.debug("appstore :: MDM Server User Sync Success !!");
			}
			
			return "true";
			
		} catch (Exception e) {
			logger.debug("appstore login error :: ", e);
			return e.getMessage();
		}
		
	}
	
	/**
	 * smart-admin 서버에 사용자 정보를 등록 (insert - update)
	 */
	private String insertUserInfoAdminServer(String serverIp, String userId, String userName, String employeeId, String department, String telNo) {
		
		String rtnCode = "SUCCESS";
		
		try {
			InsertUserRequestDO userInfo = new InsertUserRequestDO();
			userInfo.setUserId(userId);
			userInfo.setUserGroupId("USGR0000000001");
			userInfo.setRoleId("ROLE0000000003");
			userInfo.setCompanyId("COMP0000000001");
			userInfo.setUserType("USER");
			userInfo.setUserName(userName);
			userInfo.setLossFlag("N");
			userInfo.setRemoteDeleteFlag("N");
			userInfo.setDownloadFlag("Y");
			userInfo.setPublicUseFlag("Y");
			userInfo.setEmployeeNo(employeeId);
			userInfo.setDepartment(department);
			userInfo.setTel(telNo);
			userInfo.setPrivateCreateFlag("N");
					
			JsonNode resultJson = cowayCommonHttpClient.insertUser(serverIp, userInfo);
			logger.debug("*** smart-admin isnert user result = " + resultJson.toString());
			
			InsertUserResponseDO userResult = new InsertUserResponseDO(resultJson);
			//logger.debug("*** 사용자 정보 등록 결과 : " + userResult.toString());
			
			if(userResult.getResultCode().equals("FAIL") == true) {
				
				String errorCode = userResult.getMessageCode();
				logger.debug("*** smart-admin insert user fail message = " + userResult.getMessageCode());
				if(errorCode.equals("USER1020") == true) {
					//기존 등록된 사용자일 경우는 update 처리함
					rtnCode = updateUserInfoAdmiinServer(serverIp, userId, userName, employeeId, department, telNo);
				} else {
					rtnCode = errorCode;
				}
			}
			
		} catch (ConnectClientException e) {
			logger.debug("insert user info error :: ", e);
			rtnCode = e.getMessage();
		}
			
		return rtnCode;
	}
	
	private String updateUserInfoAdmiinServer(String serverIp, String userId, String userName, String employeeId, String department, String telNo) {
		
		String rtnCode = "SUCCESS";
		try {
			UpdateUserRequestDO userInfo = new UpdateUserRequestDO();
			userInfo.setUserId(userId);
			userInfo.setUserGroupId("USGR0000000001");
			userInfo.setRoleId("ROLE0000000003");
			userInfo.setCompanyId("COMP0000000001");
			userInfo.setUserType("USER");
			userInfo.setUserName(userName);
			userInfo.setLossFlag("N");
			userInfo.setRemoteDeleteFlag("N");
			userInfo.setDownloadFlag("Y");
			userInfo.setPublicUseFlag("Y");
			userInfo.setEmployeeNo(employeeId);
			userInfo.setDepartment(department);
			userInfo.setTel(telNo);
			
			JsonNode resultJson = cowayCommonHttpClient.updateUser(serverIp, userInfo);
			logger.debug("*** smart-admin update user result = " + resultJson.toString());
			
			UpdateUserResponseDO userResult = new UpdateUserResponseDO(resultJson);
			//logger.debug("*** 사용자 수정 결과 결과 : " + userResult.toString());
			
			if(userResult.getResultCode().equals("FAIL") == true) {
				logger.debug("*** smart-admin update user fail message = " + userResult.getMessageCode());
				rtnCode = userResult.getMessageCode();
			}
			
		} catch (ConnectClientException e) {
			logger.debug("insert user info error :: ", e);
			rtnCode = e.getMessage();
		}
		
		return rtnCode;
	}
	
	private String insertDeviceInfoAdminServer(String serverIp, String userId, String deviceAuthNo) {

		String rtnCode = "SUCCESS";
		
		try {
			InsertDeviceRequestDO deviceInfo = new InsertDeviceRequestDO();
			deviceInfo.setDeviceType("PRIVATE");
			deviceInfo.setCompanyId("COMP0000000001");
			deviceInfo.setUserId(userId);
			deviceInfo.setDeviceAuthNo(deviceAuthNo);
	
			JsonNode resultJson = cowayCommonHttpClient.insertDevice(serverIp, deviceInfo);
			logger.debug("*** smart-admin insert device result = " + resultJson.toString());
			
			InsertDeviceResponseDO deviceResult = new InsertDeviceResponseDO(resultJson);			
			//logger.debug("*** 디바이스 등록 결과 " + deviceResult.toString());	
			
			if(deviceResult.getResultCode().equals("FAIL") == true) {
				String errorCode = deviceResult.getMessageCode();
				logger.debug("*** smart-admin insert device fail message : " + deviceResult.getMessageCode());
				if(errorCode.equals("DEVI1010") == true) {
					//이미 등록된 인증번호 라면 인증번호 초기화
					rtnCode = initDeviceInfoAdminServer(serverIp, userId, deviceAuthNo);
				} else {
					rtnCode = errorCode;
				}
			}
			
		} catch (ConnectClientException e) {
			logger.debug("insert device info error :: ", e);
			rtnCode = e.getMessage();
		}
			
		return rtnCode;
	}
	
	private String initDeviceInfoAdminServer(String serverIp, String userId, String deviceAuthNo) {

		String rtnCode = "SUCCESS";
		
		try {
			InitDeviceAuthRequestDO deviceInfo = new InitDeviceAuthRequestDO();
			deviceInfo.setUserId(userId);
			deviceInfo.setDeviceAuthNoArray01(deviceAuthNo);
	
			JsonNode resultJson = cowayCommonHttpClient.initDeviceAuth(serverIp, deviceInfo);
			logger.debug("*** smart-admin init device result = " + resultJson.toString());
			
			InitDeviceAuthResponseDO deviceResult = new InitDeviceAuthResponseDO(resultJson);			
			//logger.debug("*** 디바이스 초기화 결과 " + deviceResult.toString());	
			
			if(deviceResult.getResultCode().equals("FAIL") == true) {
				logger.debug("*** smart-admin init device fail message : " + deviceResult.getMessageCode());
				rtnCode = deviceResult.getMessageCode();
			}
			
		} catch (ConnectClientException e) {
			logger.debug("insert device info error :: ", e);
			rtnCode = e.getMessage();
		}
			
		return rtnCode;
	}
	
	private boolean connectMDMUserSyncCheck(String userId, String userNm, String hphone, String deptId, String positionId, String companyId) {
		
		try {
			boolean syncEnable = Boolean.parseBoolean(SmartConfig.getString("mdm.user.sync.enable", "true"));
			if(syncEnable == false) {
				//mdm 사용자 sync 사용 여부
				logger.debug("appstore :: MDM User Sync disable !!");
				return true;
			}
			
			String serverUrl = SmartConfig.getString("mdm.server.url", "http://10.101.1.77:8080");
			logger.debug("appstore :: MDM Server URL = " + serverUrl);
			
			
			MdmUserSyncRequestDO userInfo = new MdmUserSyncRequestDO();
			userInfo.setUserId(userId);
			userInfo.setUserNm(userNm);
			userInfo.setHphone(hphone);
			userInfo.setDeptId(deptId);
			userInfo.setCompanyId(companyId);
			userInfo.setPositionId(positionId);
			
			logger.debug("appstore :: MdmUserSyncRequestDO info :: " + userInfo.toString());
			
			JsonNode resultJson = cowayCommonHttpClient.mdmUserSyncCheck(serverUrl, userInfo);
			logger.debug("appstore :: MDM Server UserSync result :: " + resultJson.toString());
			String resultString = resultJson.findPath("result").getTextValue();
			if(resultString.equals("ok") == true) {
				return true;
			}
			
		} catch (ConnectClientException e) {
			logger.error("appstore :: MDM Server Connect http error :: ", e);
			return false;
		} catch (Exception e) {
			logger.error("appstore :: MDM Server Connect normal error :: ", e);
			return false;
		}
		
		return false;
	}
}
