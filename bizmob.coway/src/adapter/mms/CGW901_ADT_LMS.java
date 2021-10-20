package adapter.mms;

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
import connect.db.mms.CowayMmsDao;
/**
 * @class CGW901_ADT_LMS
 * @since 2013-07-16
 * @description 80byte 가 넘는 문자는 LMS 로 전송하기 위한 전문 
 */
@Adapter(trcode = { "CGW901" })
public class CGW901_ADT_LMS extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGW901_ADT_LMS.class);
	@Autowired private CowayMmsDao cowayMmsDao;
	
	private static final String ERROR_CODE = "ADAP0000";
	
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
			String 		rsrvdId 		= CowayMMSInfo.getMMSUploadId(flag, deptCode);
			
			Map<String, Object> resMap 	= null;
			boolean 			isTest 	= Boolean.parseBoolean(SmartConfig.getString("coway.mms.upload.test.mode", "true"));
			long 				start 	= System.currentTimeMillis();
			if ( isTest ) {
				//테스트 모드
				logger.debug(">>>> CGW901 lms test mode !! sp_lms_test execute!!");
				resMap = cowayMmsDao.callSpLmsTest(phnId, invnr, title, content, rsrvdId);
			} else {
				//운영모드
				logger.debug(">>>> CGW901 lms running mode !! sp_lms execute !!");
				resMap = cowayMmsDao.callSpLms(phnId, invnr, title, content, rsrvdId);
			}
			long 				end 	= System.currentTimeMillis();
			String 				rtnCode = resMap.get("rtn_code").toString();
			String 				rtnMsg 	= resMap.get("rtn_msg").toString();
			
			if ( rtnCode.equalsIgnoreCase("0") ) {
				logger.debug(">>>> CGW901 LMS upload SUCCESS!!");
			} else {
				logger.debug(">>>> CGW901 LMS call sp result = " + resMap.toString());
				
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(rtnMsg);
				
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







