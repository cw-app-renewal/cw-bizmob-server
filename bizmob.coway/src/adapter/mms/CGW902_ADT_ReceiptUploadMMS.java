package adapter.mms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonResponse;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.server.web.dao.LocalFileStorageAccessor;

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import connect.db.mms.CowayMmsDao;
import connect.exception.ConnectClientException;
import connect.ftp.FtpClientService;
import connect.ftp.mms.CowayMmsFtpUtils;
import connect.ftp.mms.CowayMmsFtpsService;

/**
 * @class CGW902_ADT_ReceiptUploadMMS
 * @since 2013-12-0
 * @description 요청된 영수증 정보를 이미지로 변환해서 MMS를 전송함 
 */
@Adapter(trcode = {"CGW902"})
public class CGW902_ADT_ReceiptUploadMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGW902_ADT_ReceiptUploadMMS.class);

	@Autowired
	private CowayMmsDao cowayMmsDao;
	
	@Autowired
	private FtpClientService ftpClientService;
	
	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;

	//웹서버 그림파일저장 경로(mms메시지 발송)
	private final String CHECK_IMAGE_PATH = "/oradata/WJSMSEXCEL/img/";

	//공용
	private final String CHECK_IMAGE_DIRECTORY = "/img/";

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {
			
			logger.debug("CGW902 :: image upload start!!");
			
			//request
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			logger.debug("CGW902 :: " + reqRootNode.toString());
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
			String uid = reqBodyNode.findPath("I_UID").getValueAsText();
			boolean testMode = reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String flag = reqBodyNode.findPath("I_FLAG").getValueAsText();
			// mms 발송코드
			String mmsSeq = reqBodyNode.findPath("I_MMS_SEQ").getValueAsText();
			// Receipt data array
			JsonNode req_I_ITAB = reqRootNode.findValue("I_ITAB");

			String rsrvdId = CowayMMSInfo.getMMSUploadId(flag, deptCode);

			logger.debug("CGW902 :: [" + flag + "] user - rsrvdid = " + rsrvdId);
			
			
			//response object 
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			String imgFullPathName = "";
			
			// 이미지 저장 경로 생성
			SimpleDateFormat date2 = new SimpleDateFormat("yyyyMM");
			String yyyymm = date2.format(new Date());
			
			SimpleDateFormat date3 = new SimpleDateFormat("yyyyMMdd");
			String yyyymmdd = date3.format(new Date());
			
			String imgFullPath = CHECK_IMAGE_PATH + yyyymm + "/" + yyyymmdd;
			String imgFileName = mmsSeq + ".jpg";
			imgFullPathName = imgFullPath + "/" + imgFileName;
			logger.debug("CGW902 :: image full path = " + imgFullPathName);
			
			// Receipt 이미지 생성
			Text2Image t2i = new Text2Image();
			byte[] imgData = t2i.text2Image(testMode, imgFileName, req_I_ITAB);
			
			//ftp upload : MMS and ImageServer
			try {
				String ftpHost = SmartConfig.getString("coway.mms.ftp.host", "10.101.5.74");
				String ftpUserName = SmartConfig.getString("coway.mms.ftp.username", "wjsms");
				String ftpPassword = SmartConfig.getString("coway.mms.ftp.password", "!dnvwjsms1");
				
				CowayMmsFtpUtils ftpUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
				boolean result = ftpUtils.ftpUpload(imgFullPath, imgFileName, imgData);
				if(result == false) {
					//오류 처리 - 파일 업로드 실패
					logger.error("CGW902 :: image ftp upload fail!!");
					SapCommonResponse errResponse = new SapCommonResponse();
					errResponse.setSapCommonHeader(reqHeaderNode);
					errResponse.setSapErrorMessage("MMS 이미지 업로드에 실패했습니다.");
					return makeResponse(resObj, errResponse.getSapCommonResponse());
				}
				
				// ImageServer upload
				String imageServerFilePath = CowayFtpFilePath.getReceiptMMSFolder(imgFileName);
				String imageServerFileName = imgFileName;
				logger.debug("CGW902 :: upload img ftp path = " + imageServerFilePath + CowayFtpFilePath._FOLDER_SEPARATOR + imageServerFileName);			
				//ftp
				ftpClientService.uploadFile(imageServerFilePath, imageServerFileName, imgData);
			} catch (Exception e) {
				logger.error("CGW902 :: ", e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				return makeResponse(resObj, errResponse.getSapCommonResponse());
			}
						
			//oracle procedure call 처리
			Map<String, Object> resMap = null;
//			boolean isTest = Boolean.parseBoolean(SmartConfig.getString("coway.mms.upload.test.mode", "true"));
			if(testMode == true) {
				//테스트 모드
				logger.debug("CGW902 :: mms upload test mode !! sp_mms_test execute!!");
				resMap = cowayMmsDao.callSpMmsTest(phnId, invnr, title, content, rsrvdId, imgFullPathName);
			} else {
				//운영모드
				logger.debug("CGW902 :: mms upload running mode !! sp_mms execute !!");
				resMap = cowayMmsDao.callSpMms(phnId, invnr, title, content, rsrvdId, imgFullPathName);
			}
			//등록 결과 확인
			String rtnCode = resMap.get("rtn_code").toString();
			String rtnMsg = resMap.get("rtn_msg").toString();
			
			if(rtnCode.equals("0") == true) {
				//업로드 성공
				logger.debug("CGW902 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW902 :: call sp result - " + resMap.toString());
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
