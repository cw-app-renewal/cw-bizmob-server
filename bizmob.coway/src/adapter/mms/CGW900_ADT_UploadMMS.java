package adapter.mms;

import adapter.common.SapCommonResponse;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.server.web.dao.LocalFileStorageAccessor;
import common.ResponseUtil;
import common.util.FileAttachmentService;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Adapter(trcode = {"CGW900"})
public class CGW900_ADT_UploadMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW900_ADT_UploadMMS.class);

	@Autowired private LocalFileStorageAccessor uploadStorageAccessor;

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		//request
		JsonNode 		reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 		reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 		reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 			trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		
		try {	
			//body field
			String 		phnId 				= reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String 		invnr 				= reqBodyNode.findPath("I_INVNR").getValueAsText();
			String 		deptCode 			= reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String 		title 				= reqBodyNode.findPath("I_TITLE").getValueAsText();
			String 		content 			= reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String 		uid 				= reqBodyNode.findPath("I_UID").getValueAsText();
			String 		uid2 				= reqBodyNode.findPath("I_UID2").getValueAsText();
			String 		uid3 				= reqBodyNode.findPath("I_UID3").getValueAsText();
			boolean 	testMode 			= reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String 		flag 				= reqBodyNode.findPath("I_FLAG").getValueAsText();

			if (testMode) {
				// 테스트서버일 시 허용된 전화번호인지 체크
			}

			String 				imgFullPath1 		= "";
			String 				imgFullPath2 		= "";
			String 				imgFullPath3 		= "";

			//파일 경로 확인
			SimpleDateFormat 	date2 				= new SimpleDateFormat("yyyyMM");
			String 				yyyymm 				= date2.format(new Date());
			SimpleDateFormat 	date3 				= new SimpleDateFormat("yyyyMMdd");
			String 				yyyymmdd 			= date3.format(new Date());
			//웹서버 그림파일저장 경로(mms메시지 발송)
			String CHECK_IMAGE_PATH = "/oradata/WJSMSEXCEL/img/";
			String 				imgPath 			= CHECK_IMAGE_PATH + yyyymm + "/" + yyyymmdd;
			
			long start = System.currentTimeMillis();
			try{
				
				imgFullPath1 = this.uploadImage(uid, imgPath);
				// 2번 3번 이미지는 UMS 지원불가, 추후 2회 전송 등 고려
				//imgFullPath2 = this.uploadImage(uid2, imgPath, ftpUtils);
				//imgFullPath3 = this.uploadImage(uid3, imgPath, ftpUtils);
				
			} catch(Exception e){
				long end = System.currentTimeMillis();
				logger.error(e.getMessage(), e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}

			content = content + " (담당자 : " + invnr + ")";

			CowayUMSRequestDO cowayUMSRequestDO = new CowayUMSRequestDO();
			cowayUMSRequestDO.setTRAN_PHONE(phnId);
			cowayUMSRequestDO.setTRAN_CALLBACK("1588-5200");
			//cowayUMSRequestDO.setTRAN_CALLBACK(invnr);
			cowayUMSRequestDO.setTITLE(title);
			cowayUMSRequestDO.setMESSAGE(content);
			cowayUMSRequestDO.setAUTOTYPE(CowayUMSInfo.getAutotype());
			cowayUMSRequestDO.setAUTOTYPEDESC(CowayUMSInfo.getAutotypeDesc(flag, deptCode));
			cowayUMSRequestDO.setDEPTCODE_OP(CowayUMSInfo.getDeptCodeOp());
			cowayUMSRequestDO.setDEPTCODE(CowayUMSInfo.getDeptCode());
			cowayUMSRequestDO.setLEGACYID(invnr);
			cowayUMSRequestDO.setSENDTYPE("R");
			cowayUMSRequestDO.setATTACH(imgFullPath1);

			CowayUMSClient cowayUMSClient = new CowayUMSClient();
			Map<String, Object> resMap = cowayUMSClient.callUMSApi(cowayUMSRequestDO);

			long end = System.currentTimeMillis();

			// /result/mms?keyType={apikey}&value={전송응답key}
			// 문자전송결과 상세조회 가능

			if("13".equals(resMap.get("code"))) {
				//업로드 성공
				logger.debug("CGW900 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW900 :: call sp result - " + resMap.toString());
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage((String) resMap.get("msg"));
				
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}	
			
			SapCommonResponse response = new SapCommonResponse(reqHeaderNode);
			return ResponseUtil.makeResponse(obj, response.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		}
		
	}
	
	private String uploadImage(String uid, String imgPath) throws Exception{
		
		//upload file 처리
		if ( (uid == null) || ("".equalsIgnoreCase(uid)) ) {
			logger.debug(">>>> UID 없이 MMS 보내기");
			return "";
			
		} else {
			
			String imgFileName = uid + ".jpg";
			byte[] imgData = uploadStorageAccessor.load(uid);
			logger.debug("CGW900 :: image file name = " + imgFileName);
			
			if(imgData == null || imgData.length == 0) {
				//오류 처리 - 업로드 파일 실패
				logger.error("CGW900 :: uploadImage() :: multipart upload temp file not found!! :: multipart uid = " + uid);
				throw new Exception("업로드 파일을 찾을 수 없습니다.");
			}

			FileAttachmentService service = new FileAttachmentService();
			//ftp upload
			try {
				service.upload(imgPath, imgFileName, imgData, true);
			} catch (Exception e) {
				logger.error("CGW900 :: uploadImage() :: ", e);
				throw new Exception(e.getLocalizedMessage());
			} finally {
				uploadStorageAccessor.remove(uid);
			}

			return service.getDownloadUrl(imgPath, imgFileName);
		}
	}
	
}
