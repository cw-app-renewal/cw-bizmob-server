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

import connect.db.mms.CowayMmsDao;
import connect.exception.ConnectClientException;
import connect.ftp.mms.CowayMmsFtpUtils;
import connect.ftp.mms.CowayMmsFtpsService;

@Adapter(trcode = {"CGW900"})
public class CGW900_ADT_UploadMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGW900_ADT_UploadMMS.class);

	@Autowired
	private CowayMmsDao cowayMmsDao;
		
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

			logger.debug("CGW900 :: image upload start!!");

			//request
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			logger.debug("CGW900 :: " + reqRootNode.toString());
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
			String uid2 = reqBodyNode.findPath("I_UID2").getValueAsText();
			String uid3 = reqBodyNode.findPath("I_UID3").getValueAsText();
			boolean testMode = reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String flag = reqBodyNode.findPath("I_FLAG").getValueAsText();
			String rsrvdId = CowayMMSInfo.getMMSUploadId(flag, deptCode);
			
			logger.debug("CGW900 :: [" + flag + "] user - rsrvdid = " + rsrvdId);
			
			//response object 
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			String imgFullPath1 = "";
			String imgFullPath2 = "";
			String imgFullPath3 = "";
			String ftpHost = SmartConfig.getString("coway.mms.ftp.host", "10.101.5.74");
			String ftpUserName = SmartConfig.getString("coway.mms.ftp.username", "wjsms");
			String ftpPassword = SmartConfig.getString("coway.mms.ftp.password", "!dnvwjsms1");
			CowayMmsFtpUtils ftpUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
			
			//파일 경로 확인
			SimpleDateFormat date2 = new SimpleDateFormat("yyyyMM");
			String yyyymm = date2.format(new Date());
			SimpleDateFormat date3 = new SimpleDateFormat("yyyyMMdd");
			String yyyymmdd = date3.format(new Date());
			String imgPath = CHECK_IMAGE_PATH + yyyymm + "/" + yyyymmdd;
			
			try{
				
				imgFullPath1 = this.uploadImage(uid, imgPath, ftpUtils);
				imgFullPath2 = this.uploadImage(uid2, imgPath, ftpUtils);
				imgFullPath3 = this.uploadImage(uid3, imgPath, ftpUtils);
				
			}catch(Exception e){
				logger.error("CGW900 :: ", e);
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
				logger.debug("CGW900 :: mms upload test mode !! callSpMms3ImageTest execute!!");
				resMap = cowayMmsDao.callSpMms3ImageTest(phnId, invnr, title, content, rsrvdId, imgFullPath1, imgFullPath2, imgFullPath3);
			} else {
				//운영모드
				logger.debug("CGW900 :: mms upload running mode !! callSpMms3Image execute !!");
				resMap = cowayMmsDao.callSpMms3Image(phnId, invnr, title, content, rsrvdId, imgFullPath1, imgFullPath2, imgFullPath3);
			}
			//등록 결과 확인
			String rtnCode = resMap.get("rtn_code").toString();
			String rtnMsg = resMap.get("rtn_msg").toString();
			
			if(rtnCode.equals("0") == true) {
				//업로드 성공
				logger.debug("CGW900 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW900 :: call sp result - " + resMap.toString());
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
	
	private String uploadImage(String uid, String imgPath, CowayMmsFtpUtils ftpUtils) throws Exception{
		
		String imgFileName = "";
		
		//upload file 처리
		if ( (uid == null) || ("".equalsIgnoreCase(uid)) ) {
			logger.debug(">>>> UID 없이 MMS 보내기");
			return "";
			
		} else {
			
			imgFileName = uploadStorageAccessor.getFileName(uid);
			if(imgFileName == null || imgFileName.equals("") == true) {
				//오류 처리 - 업로드 파일 실패
				logger.error("CGW900 :: uploadImage() :: multipart upload temp file not found!! :: multipart uid = " + uid);
				throw new Exception("업로드 파일을 찾을 수 없습니다.");
			}

			byte[] imgData = uploadStorageAccessor.load(uid);
			uploadStorageAccessor.remove(uid);
			logger.debug("CGW900 :: image file name = " + imgFileName);
			
			//ftp upload
			try {
				boolean result = ftpUtils.ftpUpload(imgPath, imgFileName, imgData);
				if(result == false) {
					//오류 처리 - 파일 업로드 실패
					logger.error("CGW900 :: uploadImage() :: image ftp upload fail!!");
					throw new Exception("MMS 이미지 업로드에 실패했습니다.");
				}
			} catch (Exception e) {
				logger.error("CGW900 :: uploadImage() :: ", e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		
		return imgPath + "/" + imgFileName;
	}
	
}
