package connect.http.coway;

import static org.junit.Assert.*;

import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import connect.http.coway.data.DeleteDeviceListRequestDO;
import connect.http.coway.data.DeleteDeviceListResponseDO;
import connect.http.coway.data.DeleteUserListRequestDO;
import connect.http.coway.data.DeleteUserListResponseDO;
import connect.http.coway.data.InitDeviceAuthRequestDO;
import connect.http.coway.data.InitDeviceAuthResponseDO;
import connect.http.coway.data.InsertDeviceRequestDO;
import connect.http.coway.data.InsertUserRequestDO;
import connect.http.coway.data.InsertUserResponseDO;
import connect.http.coway.data.MdmUserResignRequestDO;
import connect.http.coway.data.MdmUserSyncRequestDO;
import connect.http.coway.data.UpdateUserRequestDO;
import connect.http.coway.data.UpdateUserResponseDO;

import test.common.TestCommon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-ftp-applicationContext.xml")
public class CowayCommonHttpClientTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;

	CowayCommonHttpClient cowayCommonHttpClient;

	@Before
	public void setUp() throws Exception {
		cowayCommonHttpClient = appContext.getBean(CowayCommonHttpClient.class);
	}
	
	@Test
	public void testDeleteDeviceList() {
		try {
			DeleteDeviceListRequestDO req = new DeleteDeviceListRequestDO();
			req.setUserId("20209570");
			req.setWithPublicFlag("N");
			req.setDeviceAuthNoArray01("11");
			
			JsonNode resultJson = cowayCommonHttpClient.deleteDeviceList("http://10.101.1.50:9080", req);
			System.out.println(resultJson.toString());
			
			DeleteDeviceListResponseDO res = new DeleteDeviceListResponseDO(resultJson);
			System.out.println(res.getResultCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteUserList() {
		try {
			
			DeleteUserListRequestDO req = new DeleteUserListRequestDO();
			req.setRowIdNoArray01("20209570");
			
			JsonNode resultJson = cowayCommonHttpClient.deleteUserList("http://10.101.1.50:9080", req);
			System.out.println(resultJson.toString());
			
			DeleteUserListResponseDO res = new DeleteUserListResponseDO(resultJson);
			System.out.println(res.getResultCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testInitDeviceAuth() {
		try {
			
			InitDeviceAuthRequestDO req = new InitDeviceAuthRequestDO();
			req.setUserId("20209570");
			req.setWithPublicFlag("Y");
			req.setDeviceAuthNoArray01("11");
			
			JsonNode resultJson = cowayCommonHttpClient.initDeviceAuth("http://10.101.1.50:9080", req);
			System.out.println(resultJson.toString());
			
			InitDeviceAuthResponseDO res = new InitDeviceAuthResponseDO(resultJson);			
			System.out.println(res.getResultCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertDevice() {
		try {
			InsertDeviceRequestDO req = new InsertDeviceRequestDO();
			req.setDeviceType("PUBLIC");
			req.setCompanyId("COMP0000000001");
			req.setUserId("");
			req.setDeviceAuthNo("mcnc001");
	
			JsonNode resultJson = cowayCommonHttpClient.insertDevice("http://10.101.1.50:9080", req);
			System.out.println(resultJson.toString());
			
			InitDeviceAuthResponseDO res = new InitDeviceAuthResponseDO(resultJson);			
			System.out.println(res.getResultCode());			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertUser() {
		try {

			InsertUserRequestDO userReq = new InsertUserRequestDO();
			userReq.setUserId("20209505");
			userReq.setUserGroupId("USGR0000000001");
			userReq.setRoleId("ROLE0000000003");
			userReq.setCompanyId("COMP0000000001");
			userReq.setUserType("USER");
			userReq.setUserName("20209505");
			userReq.setLossFlag("N");
			userReq.setRemoteDeleteFlag("N");
			userReq.setDownloadFlag("Y");
			userReq.setPublicUseFlag("Y");
			System.out.println(userReq.toString());
			
			JsonNode resultJson = cowayCommonHttpClient.insertUser("http://10.101.1.50:9080", userReq);
			System.out.println(resultJson.toString());
			
			InsertUserResponseDO userRes = new InsertUserResponseDO(resultJson);
			System.out.println(userRes.getResultCode());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateUser() {
		try {
			UpdateUserRequestDO req = new UpdateUserRequestDO();
			req.setUserId("20209570");
			req.setUserGroupId("USGR0000000001");
			req.setRoleId("ROLE0000000003");
			req.setCompanyId("COMP0000000001");
			req.setUserType("USER");
			req.setUserName("test");
			req.setLossFlag("N");
			req.setRemoteDeleteFlag("N");
			req.setDownloadFlag("Y");
			req.setPublicUseFlag("Y");
			System.out.println(req.toString());
	
			JsonNode resultJson = cowayCommonHttpClient.updateUser("http://10.101.1.50:9080", req);
			System.out.println(resultJson.toString());
			
			UpdateUserResponseDO res = new UpdateUserResponseDO(resultJson);			
			System.out.println(res.getResultCode());			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void testPostSmartAdmin() {
		try {
			
			MdmUserSyncRequestDO req = new MdmUserSyncRequestDO();
			req.setDeptId("CODY");
			req.setHphone("01099999999");
			req.setUserId("20120518");
			req.setUserNm("테스터");
			
			JsonNode resultJson = cowayCommonHttpClient.mdmUserSyncCheck("http://210.92.63.155:8080", req);
			System.out.println(resultJson.toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMdmUserSync() {
		try {
			// 사용자 등록
			String userId = "12341234";
			String userNm = "유경열";
			String hphone = "01044444444";
			String deptId = "DEV";
			
			
			MdmUserSyncRequestDO userInfo = new MdmUserSyncRequestDO();
			userInfo.setUserId(userId);
			userInfo.setUserNm(userNm);
			userInfo.setHphone(hphone);
			userInfo.setDeptId(deptId);
			System.out.println("MdmUserSyncRequestDO info :: " + userInfo.toString());
			
			JsonNode resultJson = cowayCommonHttpClient.mdmUserSyncCheck("http://ssm.coway.com:52444", userInfo);
			System.out.println("MDM Server UserSync result :: " + resultJson.toString());
			String resultString = resultJson.findPath("result").getTextValue();
			if(resultString.equals("ok") == true) {
				System.out.println("resultJson ok");
			}else{
				System.out.println("resultJson fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Test
	public void testMdmUserResign() {
		try {
			// 사용자 등록
			String userId = "12341234";
			String userNm = "유경열";
			String hphone = "01056036010";
			//String deptId = "DEV";
			
			MdmUserResignRequestDO user = new MdmUserResignRequestDO();
			user.setUserId(userId);
			user.setUserNm(userNm);
			user.setHphone(hphone);
			System.out.println("MDM Server User Resign info = " + user.toString());
			
			JsonNode resultJson = cowayCommonHttpClient.mdmUserResignCheck("http://ssm.coway.com:52444", user);
			System.out.println("MDM Server User Resign response :: " + resultJson.toString());
			
			System.out.println("MDM Server User Resign result :: " + resultJson.toString());
			String resultString = resultJson.findPath("result").getTextValue();
			
			if(resultString.equals("ok") == true) {
				System.out.println("resultJson ok");
			}else{
				System.out.println("resultJson fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testUserCloseCheck() {
		try {
			String CR301 = 	 "{ "
					+ "\"header\" : { "
							+ "\"error_code\" : \"\", "
							+ "\"error_text\" : \"\", "
							+ "\"info_text\" : \"\", "
							+ "\"login_session_id\" : \"\", "
							+ "\"message_version\" : \"\", "
							+ "\"result\" : true, "
							+ "\"trcode\" : \"COMMON\", "
							+ "\"rfc_name\" : \"ZSMT_IF_SP_CR301\" "
						+ "}, "
					+ "\"body\" : { "
						+ "} "
					+ "} ";
			
			System.out.println("UserCloseCheck info = " + CR301.toString());
			
//			JsonNode resultJson = cowayCommonHttpClient.userCloseCheck("http://127.0.0.1:9080", CR301);
//			JsonNode resultJson = cowayCommonHttpClient.userCloseCheck("http://msfadev.coway.co.kr:9080", CR301);
//			JsonNode resultJson = cowayCommonHttpClient.userCloseCheck("http://183.111.155.156:9080", CR301);
			JsonNode resultJson = cowayCommonHttpClient.userCloseCheck("http://127.0.0.1:9080", CR301);
			System.out.println("UserCloseCheck response :: " + resultJson.toString());
			
			System.out.println("UserCloseCheck result :: " + resultJson.toString());
			String resultString = resultJson.findPath("TYPE").getTextValue();
			
			if(resultString.equals("ok") == true) {
				System.out.println("resultJson ok");
			}else{
				System.out.println("resultJson fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
