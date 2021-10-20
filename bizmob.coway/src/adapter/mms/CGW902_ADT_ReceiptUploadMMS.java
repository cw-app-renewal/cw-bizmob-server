package adapter.mms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonResponse;
import common.ResponseUtil;
import common.ftp.CowayFtpFilePath;
import connect.db.mms.CowayMmsDao;
import connect.ftp.FtpClientService;
import connect.ftp.mms.CowayMmsFtpUtils;
/**
 * @class CGW902_ADT_ReceiptUploadMMS
 * @since 2013-12-0
 * @description 요청된 영수증 정보를 이미지로 변환해서 MMS를 전송함 
 */
@Adapter(trcode = {"CGW902"})
public class CGW902_ADT_ReceiptUploadMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW902_ADT_ReceiptUploadMMS.class);

	@Autowired private CowayMmsDao cowayMmsDao;
	@Autowired private FtpClientService ftpClientService;

	private final String CHECK_IMAGE_PATH = "/oradata/WJSMSEXCEL/img/"; 	//웹서버 그림파일저장 경로(mms메시지 발송)

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		JsonNode 			reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 			reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 			reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 				trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		
		try {
			String 			phnId 			= reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String 			invnr 			= reqBodyNode.findPath("I_INVNR").getValueAsText();
			String 			deptCode 		= reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String 			title 			= reqBodyNode.findPath("I_TITLE").getValueAsText();
			String 			content 		= reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String 			uid 			= reqBodyNode.findPath("I_UID").getValueAsText();
			boolean 		testMode 		= reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String 			flag 			= reqBodyNode.findPath("I_FLAG").getValueAsText();
			String 			mmsSeq 			= reqBodyNode.findPath("I_MMS_SEQ").getValueAsText(); // mms 발송코드
			JsonNode 		req_I_ITAB 		= reqRootNode.findValue("I_ITAB"); // Receipt data array
			String 			rsrvdId 		= CowayMMSInfo.getMMSUploadId(flag, deptCode);
			String 			imgFullPathName = "";
			
			// 이미지 저장 경로 생성
			SimpleDateFormat 	date2 		= new SimpleDateFormat("yyyyMM");
			String 				yyyymm 		= date2.format(new Date());
			
			SimpleDateFormat 	date3 		= new SimpleDateFormat("yyyyMMdd");
			String 				yyyymmdd 	= date3.format(new Date());
			
			String 				imgFullPath = CHECK_IMAGE_PATH + yyyymm + "/" + yyyymmdd;
			String 				imgFileName = mmsSeq + ".jpg";
			
			imgFullPathName = imgFullPath + "/" + imgFileName;
			logger.debug("CGW902 :: image full path = " + imgFullPathName);
			
			// Receipt 이미지 생성
			Text2Image 			t2i 		= new Text2Image();
			byte[] 				imgData 	= t2i.text2Image(testMode, imgFileName, req_I_ITAB);
			long 				start 		= 0;
			long 				end			= 0;
			try {
				String 				ftpHost 		= SmartConfig.getString("coway.mms.ftp.host", "10.101.5.74");
				String 				ftpUserName 	= SmartConfig.getString("coway.mms.ftp.username", "wjsms");
				String 				ftpPassword 	= SmartConfig.getString("coway.mms.ftp.password", "!dnvwjsms1");
				
				CowayMmsFtpUtils 	ftpUtils 		= new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
				
				start 	= System.currentTimeMillis();
				boolean 			result 			= ftpUtils.ftpUpload(imgFullPath, imgFileName, imgData);
				end 	= System.currentTimeMillis();
				
				if(result == false) {
					
					//오류 처리 - 파일 업로드 실패
					logger.error("CGW902 :: image ftp upload fail!!");
					SapCommonResponse errResponse = new SapCommonResponse();
					errResponse.setSapCommonHeader(reqHeaderNode);
					errResponse.setSapErrorMessage("MMS 이미지 업로드에 실패했습니다.");
					
					return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
				}
				
				// ImageServer upload
				String imageServerFilePath = CowayFtpFilePath.getReceiptMMSFolder(imgFileName);
				String imageServerFileName = imgFileName;
				logger.debug("CGW902 :: upload img ftp path = " + imageServerFilePath + CowayFtpFilePath._FOLDER_SEPARATOR + imageServerFileName);			
				
				//ftp
				ftpClientService.uploadFile(imageServerFilePath, imageServerFileName, imgData);
				end = System.currentTimeMillis();
				
			} catch (Exception e) {
				logger.error("CGW902 :: ", e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}
						
			//oracle procedure call 처리
			Map<String, Object> resMap = null;
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
			String rtnCode 	= resMap.get("rtn_code").toString();
			String rtnMsg	= resMap.get("rtn_msg").toString();
			
			if("0".equals(rtnCode) == true) {
				//업로드 성공
				logger.debug("CGW902 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW902 :: call sp result - " + resMap.toString());
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(rtnMsg);
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}	
			//response			
			SapCommonResponse response = new SapCommonResponse(reqHeaderNode);
			return ResponseUtil.makeResponse(obj, response.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		}
	}
	
}
