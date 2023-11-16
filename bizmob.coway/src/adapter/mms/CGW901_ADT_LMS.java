package adapter.mms;

import adapter.common.SapCommonResponse;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import common.ResponseUtil;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
/**
 * @class CGW901_ADT_LMS
 * @since 2013-07-16
 * @description 80byte 가 넘는 문자는 LMS 로 전송하기 위한 전문 
 */
@Adapter(trcode = { "CGW901" })
public class CGW901_ADT_LMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW901_ADT_LMS.class);

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 		reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 		reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 		reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String	 		trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		
		try {
			String 		phnId 			= reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String 		invnr 			= reqBodyNode.findPath("I_INVNR").getValueAsText();
			String 		deptCode 		= reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String 		title 			= reqBodyNode.findPath("I_TITLE").getValueAsText();
			String 		content 		= reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String 		flag 			= reqBodyNode.findPath("I_FLAG").getValueAsText();

			boolean 			isTest 	= Boolean.parseBoolean(SmartConfig.getString("coway.mms.upload.test.mode", "true"));

			if (isTest) {
				// 테스트서버일 시 허용된 전화번호인지 체크
			}

			long 				start 	= System.currentTimeMillis();

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

			CowayUMSClient cowayUMSClient = new CowayUMSClient();
			Map<String, Object> resMap = cowayUMSClient.callUMSApi(cowayUMSRequestDO);

			long end = System.currentTimeMillis();

			// /result/mms?keyType={apikey}&value={전송응답key}
			// 문자전송결과 상세조회 가능

			if("13".equals(resMap.get("code"))) {
				//업로드 성공
				logger.debug("CGW901 :: image upload end!!");
			} else {
				//오류 처리
				logger.debug("CGW901 :: call sp result - " + resMap.toString());
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
}







