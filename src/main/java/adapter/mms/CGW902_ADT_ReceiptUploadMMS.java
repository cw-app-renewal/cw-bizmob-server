package adapter.mms;

import adapter.common.SapCommonResponse;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import common.ResponseUtil;
import common.ftp.CowayFtpFilePath;
import common.util.FileAttachmentService;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
/**
 * @class CGW902_ADT_ReceiptUploadMMS
 * @since 2013-12-0
 * @description 요청된 영수증 정보를 이미지로 변환해서 MMS를 전송함 
 */
@Adapter(trcode = {"CGW902"})
public class CGW902_ADT_ReceiptUploadMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW902_ADT_ReceiptUploadMMS.class);

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
			//String 			uid 			= reqBodyNode.findPath("I_UID").getValueAsText();
			boolean 		testMode 		= reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String 			flag 			= reqBodyNode.findPath("I_FLAG").getValueAsText();
			String 			mmsSeq 			= reqBodyNode.findPath("I_MMS_SEQ").getValueAsText(); // mms 발송코드
			JsonNode 		req_I_ITAB 		= reqRootNode.findValue("I_ITAB"); // Receipt data array

			if (testMode) {
				// 테스트서버일 시 허용된 전화번호인지 체크
			}

			String 				imgFileName = mmsSeq + ".jpg";
			String 				imgFullPath 		= "";

			// Receipt 이미지 생성
			Text2Image 			t2i 		= new Text2Image();
			byte[] 				imgData 	= t2i.text2Image(testMode, imgFileName, req_I_ITAB);
			long 				start 		= 0;
			long 				end			= 0;
			try {

				start 	= System.currentTimeMillis();
				// ImageServer upload
				String imageServerFilePath = CowayFtpFilePath.getReceiptMMSFolder(imgFileName);
				logger.debug("CGW902 :: upload img ftp path = " + imageServerFilePath + CowayFtpFilePath._FOLDER_SEPARATOR + imgFileName);
				//ftp
				imgFullPath = this.uploadImage(imgData, imageServerFilePath, imgFileName);

			} catch (Exception e) {
				logger.error("CGW902 :: ", e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				end = System.currentTimeMillis();
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}

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
			cowayUMSRequestDO.setATTACH(imgFullPath);

			CowayUMSClient cowayUMSClient = new CowayUMSClient();
			Map<String, Object> resMap = cowayUMSClient.callUMSApi(cowayUMSRequestDO);

			end = System.currentTimeMillis();

			// /result/mms?keyType={apikey}&value={전송응답key}
			// 문자전송결과 상세조회 가능

			if("13".equals(resMap.get("code"))) {
				//업로드 성공
				logger.debug("CGW902 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW902 :: call sp result - " + resMap.toString());
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage((String) resMap.get("msg"));

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

	private String uploadImage(byte[] imgData, String imgPath, String imgFileName) throws Exception{

		if (imgData == null || imgData.length == 0) {
			//오류 처리 - 업로드 파일 실패
			logger.error("CGW902 :: uploadImage() :: multipart upload temp file not found!! :: imgFileName = " + imgFileName);
			throw new Exception("업로드 파일을 찾을 수 없습니다.");
		}

		FileAttachmentService service = new FileAttachmentService();
		//ftp upload
		try {
			service.upload(imgPath, imgFileName, imgData, true);
		} catch (Exception e) {
			logger.error("CGW902 :: uploadImage() :: ", e);
			throw new Exception(e.getLocalizedMessage());
		}

		return service.getDownloadUrl(imgPath, imgFileName);
	}
	
}
