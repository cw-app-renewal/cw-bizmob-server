package adapter.mms;

import com.mcnc.smart.common.config.SmartConfig;

public class CowayMMSInfo {

	/**
	 * @method getMMSUploadId
	 * @param {String }flag 닥터, 코드 그외에 코드
	 * @param {String} deptCode
	 * @return {String} result 문자 아이디
	 * @throws Exception
	 * @description 문자 아이디를 가져오는 메소드
	 */
	static String getMMSUploadId(String flag, String deptCode){
		String rsrvdId = null;
		
		if(flag.equalsIgnoreCase("doctor")) {
			rsrvdId = SmartConfig.getString("coway.mms.upload.doctor.id", "RFC02엄마2");

		} else if(flag.equalsIgnoreCase("cody")) {
			rsrvdId = SmartConfig.getString("coway.mms.upload.cody.id", "RFC02엄마");

		} else if ( flag.equalsIgnoreCase("sun") ) {
			rsrvdId = SmartConfig.getString("coway.mms.upload.sun.id", "RFC02아들");

		} else if ( flag.equalsIgnoreCase("home") ) {
			rsrvdId = SmartConfig.getString("coway.mms.upload.home.id", "RFC02HOME-MMS");

		} else if ( flag.equalsIgnoreCase("codyreceipt") ) {
			// cody receipt image mms
			rsrvdId = SmartConfig.getString("coway.mms.upload.codyreceipt.id", "Web02mob-MMS");
			
		} else if ( flag.equalsIgnoreCase("homereceipt") ) {
			// homecare receipt image mms
			rsrvdId = SmartConfig.getString("coway.mms.upload.codyreceipt.id", "RFC02스마트1");
			
		} else if ( flag.equalsIgnoreCase("CODYMMS") ) {
			// codysns image mms
			rsrvdId = SmartConfig.getString("coway.mms.upload.codysns.id", "RFC02전단");
			
		} else {
			rsrvdId = deptCode;
			
		}
		
		return rsrvdId;

	}		
}
