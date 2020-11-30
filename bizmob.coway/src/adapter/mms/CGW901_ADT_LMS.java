package adapter.mms;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
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

import connect.db.mms.CowayMmsDao;

/**
 * @class CGW901_ADT_LMS
 * @since 2013-07-16
 * @description 80byte 가 넘는 문자는 LMS 로 전송하기 위한 전문 
 */
@Adapter(trcode = { "CGW901" })
public class CGW901_ADT_LMS extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGW901_ADT_LMS.class);
	
	private static final String ERROR_CODE = "ADAP0000";
	
	@Autowired
	private CowayMmsDao cowayMmsDao;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {
			
			logger.debug(">>>> CGW901_ADT_LMS Start");
			
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode reqHeaderNode = reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode reqBodyNode = reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			logger.debug(">>>> reqRootNode = " + reqRootNode.toString());
			
			//trcode
			String trCode = reqHeaderNode.findPath("trcode").getValueAsText();
			
			//body field
			String phnId = reqBodyNode.findPath("I_PHN_ID").getValueAsText();
			String invnr = reqBodyNode.findPath("I_INVNR").getValueAsText();
			String deptCode = reqBodyNode.findPath("I_DEPT_CD").getValueAsText();
			String title = reqBodyNode.findPath("I_TITLE").getValueAsText();
			String content = reqBodyNode.findPath("I_CONTENT").getValueAsText();
			String flag = reqBodyNode.findPath("I_FLAG").getValueAsText();

			//String rsrvdId = getRsvdId(flag, deptCode);
			String rsrvdId = CowayMMSInfo.getMMSUploadId(flag, deptCode);
			//String uid = reqBodyNode.findPath("I_UID").getValueAsText();
			//boolean testMode = reqBodyNode.findPath("I_TEST_MODE").getBooleanValue();
			
			Map<String, Object> resMap = null;
			
			boolean isTest = Boolean.parseBoolean(SmartConfig.getString("coway.mms.upload.test.mode", "true"));
			
			if ( isTest ) {
			
				//테스트 모드
				logger.debug(">>>> CGW901 lms test mode !! sp_lms_test execute!!");
				resMap = cowayMmsDao.callSpLmsTest(phnId, invnr, title, content, rsrvdId);
			} else {
				
				//운영모드
				logger.debug(">>>> CGW901 lms running mode !! sp_lms execute !!");
				resMap = cowayMmsDao.callSpLms(phnId, invnr, title, content, rsrvdId);
			}
			
			//등록 결과 확인
			String rtnCode = resMap.get("rtn_code").toString();
			String rtnMsg = resMap.get("rtn_msg").toString();
			
			if ( rtnCode.equalsIgnoreCase("0") ) {
				
				//업로드 성공
				logger.debug(">>>> CGW901 LMS upload SUCCESS!!");
			} else {
				
				//오류 처리
				logger.debug(">>>> CGW901 LMS call sp result = " + resMap.toString());
				
				SapCommonResponse errResponse = new SapCommonResponse();
				errResponse.setSapCommonHeader(reqHeaderNode);
				errResponse.setSapErrorMessage(rtnMsg);
				
				return makeResponse(resObj, errResponse.getSapCommonResponse());
			}	
			
			//response			
			SapCommonResponse response = new SapCommonResponse(reqHeaderNode);
			
			return makeResponse(resObj, response.getSapCommonResponse());

		} catch (Exception e) {
			
			logger.error(">>>> LMS Exception ", e);
			
			return makeFailReesponse(ERROR_CODE, e.getLocalizedMessage());
		}
	}

	/**
	 * @method getRsvdId
	 * @param {String }flag 닥터, 코드 그외에 코드
	 * @param {String} deptCode
	 * @return {String} result 문자 아이디
	 * @throws Exception
	 * @description 문자 아이디를 가져오는 메소드
	 */
//	private String getRsvdId(String flag, String deptCode) throws Exception {
//		
//		final String RFC_02_MATHER_2 = "RFC02엄마2";
//		final String RFC_02_MATHER = "RFC02엄마";
//		final String RFC_02_SUN = "RFC02아들";
//		
//		String result = "";
//		
//		try {
//			
//			if ( flag.equalsIgnoreCase("doctor") ) {
//				
//				result = SmartConfig.getString("coway.mms.upload.doctor.id", RFC_02_MATHER_2);
//			} else if ( flag.equalsIgnoreCase("cody") ) {
//				
//				result = SmartConfig.getString("coway.mms.upload.cody.id", RFC_02_MATHER);
//			} else if ( flag.equalsIgnoreCase("sun") ) {
//				
//				result = SmartConfig.getString("coway.mms.upload.sun.id", RFC_02_SUN);
//			} else {
//				
//				result = deptCode;
//			}
//		} catch ( Exception e ) {
//
//			logger.debug(">>>> 문자 아이디를 가져오는 중 오류가 발생하였습니다.");
//			
//			throw e;
//		}
//		
//		logger.debug(">>>> result =  " + result);
//		
//		return result;
//	}
}







