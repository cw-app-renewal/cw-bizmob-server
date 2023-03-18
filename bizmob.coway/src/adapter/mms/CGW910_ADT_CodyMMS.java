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

@Adapter(trcode = {"CGW910"})
public class CGW910_ADT_CodyMMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW910_ADT_CodyMMS.class);

	//웹서버 그림파일저장 경로(mms메시지 발송)
	private final String CHECK_IMAGE_PATH = "/oradata/WJSMSEXCEL/img/";

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 			reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 			reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 			reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 				trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		
		try {
			//body field
			String 			phnId 				= reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String 			invnr 				= reqBodyNode.findPath("I_INVNR").getValueAsText();
			String 			deptCode 			= reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String 			title 				= reqBodyNode.findPath("I_TITLE").getValueAsText();
			String 			content 			= reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String 			downloadFileName 	= reqBodyNode.findPath("I_MMS_SEQ").getValueAsText();
			boolean 		testMode 			= reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			String 			flag 				= reqBodyNode.findPath("I_FLAG").getValueAsText();

			if (testMode) {
				// 테스트서버일 시 허용된 전화번호인지 체크
			}

			//발송 이미지 다운로드
			String filePath = CowayFtpFilePath.getCowayMMSFolder();
			String fileName = downloadFileName; // 확장자를 포함한 이미지 명칭
			String imgFullPath = "";
			
			filePath = filePath.replace("..", "");
			fileName = fileName.replace("..", "");

			long	start			= System.currentTimeMillis();

		    try {
				//ftp
				FileAttachmentService service = new FileAttachmentService();
				byte[] imgData = service.download(filePath, fileName, true);

				// 다운로드 이미지가 있는 경우 upload 처리
				if ( imgData == null ){
					logger.error("CGW910 :: image ftp download fail!!");
					SapCommonResponse errResponse = new SapCommonResponse();
					errResponse.setSapCommonHeader(reqHeaderNode);
					errResponse.setSapErrorMessage("전송 이미지 없거나, 수신에 실패했습니다.");
					return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, 0, reqBodyNode, this.getClass().getName());
					
				}

				imgFullPath = service.getDownloadUrl(filePath, fileName);

			} catch (Exception e) {
				logger.error("CGW910 down/upload Exception :: ", e);
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(e.getLocalizedMessage());
				return ResponseUtil.makeResponse(obj, errResponse.getSapCommonResponse(), trCode, 0, reqBodyNode, this.getClass().getName());
	        	
			}  
		    
		    long end = System.currentTimeMillis();

			CowayUMSRequestDO cowayUMSRequestDO = new CowayUMSRequestDO();
			cowayUMSRequestDO.setTRAN_PHONE(phnId);
			cowayUMSRequestDO.setTRAN_CALLBACK("1588-5200");
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

			// /result/mms?keyType={apikey}&value={전송응답key}
			// 문자전송결과 상세조회 가능

			if("13".equals(resMap.get("code"))) {
				//업로드 성공
				logger.debug("CGW910 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW910 :: call sp result - " + resMap.toString());
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
	
	
	
}
