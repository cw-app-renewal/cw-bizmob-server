package adapter.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.smart.common.Smart;

import common.ConfigurationManager;
public class SapCommonException {

	private static final Logger logger = LoggerFactory.getLogger(SapCommonException.class);
	
	private String errCode = "";
	private String errMsg = "";
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
		
	public void setError(String trCode, String errMsg, DBAdapter dbAdapter) {
		this.errCode = "SAPERR";//findErrCode(trCode, errMsg, dbAdapter);
		this.errMsg = errMsg;	
	}
	
	public SapCommonException(String trCode, String errMsg, DBAdapter dbAdapter) {
		this.errCode = "SAPERR";//findErrCode(trCode, errMsg, dbAdapter);
		this.errMsg = errMsg;	
	}
	
//	private String findErrCodeByDB(String trCode, String errMsg) {
//				
//		//디비에 등록되어 있는 에러 코드를 확인
//		String errCode = errorCodeDao.selectErrorCode(trCode, errMsg);
//		if(errCode == null || errCode.equals("") == true) {
//			//에러 코드가 없다면 신규 코드를 생성(max+1)
//			errCode = errorCodeDao.selectNextErrorCode(trCode);
//			//디비에 등록
//			errorCodeDao.insertNewErrorCode(trCode, errCode, errMsg);
//			//파일에 등록
//			addErrorProperties(trCode+"ADAP"+errCode, errMsg);
//		}
//	
//		return trCode + "ADAP" + errCode;
//	}
	
	/*private String findErrCode(String trCode, String errMsg, DBAdapter dbAdapter) {
		
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("trCode",trCode);
		inputMap.put("errMessage", errMsg);
		
		//디비에 등록되어 있는 에러 코드를 확인
		String errCode = (String) dbAdapter.selectOne("CowayErrorCode", "CowayErrorCode.selectErrorCode", inputMap);
		logger.debug("에러 메시지로 에러 코드를 확인 [" + errCode + "||" + errMsg + "]");
		if(errCode == null || errCode.equals("") == true) {
			//에러 코드가 없다면 신규 코드를 생성(max+1)
			errCode = (String) dbAdapter.selectOne("CowayErrorCode", "CowayErrorCode.selectNextErrorCode", inputMap);
			logger.debug("신규 에러 코드를 생성 (trCode단위) [" + errCode + "]");
			//디비에 등록
			inputMap.put("errCode", errCode);
			dbAdapter.insert("CowayErrorCode", "CowayErrorCode.insertNewErrorCode", inputMap);
			logger.debug("신규 에러 코드를 디비에 등록함");
		}

		//파일에 등록
		addErrorProperties(trCode+"ADAP"+errCode, errMsg);
	
		return trCode + "ADAP" + errCode;
	}*/
	
	/*
	 * table errCodeTalbe (errCode (auto-increment), errMsg)
	 * 
	 * function findErrCode (errMsg)
	 * 
	 * select count(*) as cnt from errCodeTable where errMsg like '%%'
	 * 
	 * if cnt = 0 then
	 *   insert into errCodeTable (errMsg) values ('')
	 * end
	 *  
	 * select errCode from errCodeTable where errMsg like '%%'
	 * 
	 * return errCode
	 */
	
	/**
	 * display_message에 error code 추가
	 * display_message는 applicationcontext-resources.xml에서 reloadable 되기 때문에 error code 추가하면 다시 로드한다. 
	 */
	/*private void addErrorProperties(String code, String message) {

		boolean isConfig = ConfigurationManager.containsConfiguration("display_message");
		if(isConfig == false) {
			String url = Smart.HOME_DIR + "/conf/server/display_message.properties";
			ConfigurationManager.addConfiguration("display_message", url);
		}
	        
        PropertiesConfiguration config 	= ConfigurationManager.getConfiguration("display_message");
        
        // 오더생성시에 같은 에러 type이 넘어오는 관계로 항상 property 파일에 에러문구를 저장하도록 수정됨. -이홍
        if(config.containsKey(code) == false) {
        	config.addProperty(code, message);
        	
        	String filePath = config.getBasePath();
        	try {
				config.save(filePath);
			} catch (ConfigurationException e) {
				logger.error("에러 메시지 업데이트 오류 !!", e);
			}
        } 
		
	}*/
	
}
