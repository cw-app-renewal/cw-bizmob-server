package adapter.mms;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonResponse;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.server.web.dao.LocalFileStorageAccessor;

import common.ftp.CowayFtpFilePath;
import connect.db.mms.CowayMmsDao;
import connect.exception.ConnectClientException;
import connect.ftp.FtpClientService;
import connect.ftp.mms.CowayMmsFtpUtils;


@Adapter(trcode = {"CGW910"})
public class CGW910_ADT_CodyMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGW910_ADT_CodyMMS.class);

	@Autowired
	private CowayMmsDao cowayMmsDao;
		
	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;

	@Autowired
	FtpClientService ftpClientService;
	
	//웹서버 그림파일저장 경로(mms메시지 발송)
	private final String CHECK_IMAGE_PATH = "/oradata/WJSMSEXCEL/img/";

	//공용
	private final String CHECK_IMAGE_DIRECTORY = "/img/";

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {

			logger.debug("CGW910 :: image upload start!!");

			//request
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			logger.debug("CGW910 :: " + reqRootNode.toString());
			JsonNode reqHeaderNode = reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode reqBodyNode = reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
			
			//trcode
			String trCode = reqHeaderNode.findPath("trcode").getValueAsText();
			
			//body field
			String phnId = reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String invnr = reqBodyNode.findPath("I_INVNR").getValueAsText();
			String deptCode = reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String title = reqBodyNode.findPath("I_TITLE").getValueAsText();
			String content = reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String downloadFileName = reqBodyNode.findPath("I_MMS_SEQ").getValueAsText();
			boolean testMode = reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String flag = reqBodyNode.findPath("I_FLAG").getValueAsText();
			String rsrvdId = CowayMMSInfo.getMMSUploadId(flag, deptCode);
			
			//flag 정보 및 발송 ID
			logger.debug("CGW910 :: [" + flag + "] user - rsrvdid = " + rsrvdId);
			
			//response object 
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			
			//발송 이미지 다운로드
			String filePath = CowayFtpFilePath.getCowayMMSFolder();
			String fileName = downloadFileName; // 확장자를 포함한 이미지 명칭
			
			filePath = filePath.replace("..", "");
			fileName = fileName.replace("..", "");
			
			logger.debug("download full file path = [" + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName + "]");
			
			String imgFullPathName = "";

		    try {
			
				//ftp
				String ftpDowloadHost = SmartConfig.getString("media.ftp.host", "10.101.1.57");
				String ftpDowloadUserName = SmartConfig.getString("media.ftp.username", "ftpuser_smt");
				String ftpDowloadPassword = SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#");
				
				CowayMmsFtpUtils ftpDowloadUtils = new CowayMmsFtpUtils(ftpDowloadHost, ftpDowloadUserName, ftpDowloadPassword);
				ftpDowloadUtils.setFtpCharset("euc-kr");
				byte[] imgData = ftpDowloadUtils.ftpDownload(filePath, fileName);
			  
				// 다운로드 이미지가 있는 경우 upload 처리
				if ( imgData == null ){
					logger.error("CGW910 :: image ftp download fail!!");
					SapCommonResponse errResponse = new SapCommonResponse();
					errResponse.setSapCommonHeader(reqHeaderNode);
					errResponse.setSapErrorMessage("전송 이미지 없거나, 수신에 실패했습니다.");
					return makeResponse(resObj, errResponse.getSapCommonResponse());
				} else {

					//파일 경로 확인
					SimpleDateFormat date2 = new SimpleDateFormat("yyyyMM");
					String yyyymm = date2.format(new Date());
					
					SimpleDateFormat date3 = new SimpleDateFormat("yyyyMMdd");
					String yyyymmdd = date3.format(new Date());
					
					String imgFullPath = CHECK_IMAGE_PATH + yyyymm + "/" + yyyymmdd;
					imgFullPathName = imgFullPath + "/" + fileName;
					logger.debug("CGW900 :: image full path = " + imgFullPathName);
					
					//ftp upload
					try {
						String ftpUploadHost = SmartConfig.getString("coway.mms.ftp.host", "10.101.5.74");
						String ftpUploadUserName = SmartConfig.getString("coway.mms.ftp.username", "wjsms");
						String ftpUploadPassword = SmartConfig.getString("coway.mms.ftp.password", "!dnvwjsms1");
						
						CowayMmsFtpUtils ftpUploadUtils = new CowayMmsFtpUtils(ftpUploadHost, ftpUploadUserName, ftpUploadPassword);
						ftpUploadUtils.setFtpCharset("euc-kr");
						boolean result = ftpUploadUtils.ftpUpload(imgFullPath, fileName, imgData);
						if(result == false) {
							//오류 처리 - 파일 업로드 실패
							logger.error("CGW910 :: image ftp upload fail!!");
							SapCommonResponse errResponse = new SapCommonResponse();
							errResponse.setSapCommonHeader(reqHeaderNode);
							errResponse.setSapErrorMessage("MMS 이미지 업로드에 실패했습니다.");
							return makeResponse(resObj, errResponse.getSapCommonResponse());
						}
					} catch (Exception e) {
						logger.error("CGW910 upload Eception :: ", e);
						SapCommonResponse errResponse = new SapCommonResponse();
						errResponse.setSapCommonHeader(reqHeaderNode);
						errResponse.setSapErrorMessage(e.getLocalizedMessage());
						return makeResponse(resObj, errResponse.getSapCommonResponse());
					}
				}
		    	
			} catch (Exception e) {
				logger.error("CGW910 down/upload Exception :: ", e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				return makeResponse(resObj, errResponse.getSapCommonResponse());
	        	
			}  finally {
				
	        }
		    
			//oracle procedure call 처리
			Map<String, Object> resMap = null;
//			boolean isTest = Boolean.parseBoolean(SmartConfig.getString("coway.mms.upload.test.mode", "true"));
			if(testMode == true) {
				//테스트 모드
				logger.debug("CGW910 :: mms upload test mode !! sp_mms_test execute!!");
				resMap = cowayMmsDao.callSpMmsTest(phnId, invnr, title, content, rsrvdId, imgFullPathName);
			} else {
				//운영모드
				logger.debug("CGW910 :: mms upload running mode !! sp_mms execute !!");
				resMap = cowayMmsDao.callSpMms(phnId, invnr, title, content, rsrvdId, imgFullPathName);
			}
			//등록 결과 확인
			String rtnCode = resMap.get("rtn_code").toString();
			String rtnMsg = resMap.get("rtn_msg").toString();
			
			if(rtnCode.equals("0") == true) {
				//업로드 성공
				logger.debug("CGW910 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW910 :: call sp result - " + resMap.toString());
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(rtnMsg);
				return makeResponse(resObj, errResponse.getSapCommonResponse());
			}	
			
			//response			
			SapCommonResponse response = new SapCommonResponse(reqHeaderNode);
			
			return makeResponse(resObj, response.getSapCommonResponse());

		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
		
	}
	
	
	
}
