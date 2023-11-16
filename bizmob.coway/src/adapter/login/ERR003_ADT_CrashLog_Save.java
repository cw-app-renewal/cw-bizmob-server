package adapter.login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
/**
 * @class ERR001_ADT_File_Save
 * @since 2013-06-17
 * @description 에러를 파일로 저장하는 어댑터
 */
@Adapter(trcode = {"ERR003"})
public class ERR003_ADT_CrashLog_Save extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(ERR003_ADT_CrashLog_Save.class);
	
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		try {
			
			JsonNode reqRootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode sessionNode = obj.get(JsonAdaptorObject.TYPE.META);
			JsonNode reqHeaderNode = reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode reqBodyNode = reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
			
			logger.info(">>>> reqRootNode = " + reqRootNode.toString());
			
			String packageName = convertNull2Str(reqBodyNode.findPath("packageName").getValueAsText());
			String time = convertNull2Str(reqBodyNode.findPath("time").getValueAsText());
			String deviceId = convertNull2Str(reqBodyNode.findPath("deviceId").getValueAsText());
			String model = convertNull2Str(reqBodyNode.findPath("model").getValueAsText());
			String mobileNumber = convertNull2Str(reqBodyNode.findPath("mobileNumber").getValueAsText());
			String osVersion = convertNull2Str(reqBodyNode.findPath("osVersion").getValueAsText());
			String log = convertNull2Str(reqBodyNode.findPath("log").getValueAsText());
			String userId = convertNull2Str(reqBodyNode.findPath("userId").getValueAsText());
			String departmentCode = convertNull2Str(reqBodyNode.findPath("departmentCode").getValueAsText());
			String departmentName = convertNull2Str(reqBodyNode.findPath("departmentName").getValueAsText());
			
			StringBuffer params = new StringBuffer();
			params.append("packageName = " + packageName + "\n");
			params.append("time = " + time + "\n");
			params.append("deviceId = " + deviceId + "\n");
			params.append("model = " + model + "\n");
			params.append("mobileNumber = " + mobileNumber + "\n");
			params.append("osVersion = " + osVersion + "\n");
			params.append("userId = " + userId + "\n");
			params.append("departmentCode = " + departmentCode + "\n");
			params.append("departmentName = " + departmentName + "\n");
			
			params.append("log = " + log + "\n");
			
			
			boolean isSaveLogFile = saveLogFile(params, userId, packageName);
			
			logger.debug(">>>> isSaveLogFile = " + isSaveLogFile);
			
			if ( !isSaveLogFile ) {
				
				logger.debug("로그 파일 저장에 실패하였습니다.");
				
				return makeFailResponse("IMPL0002", "로그 파일 저장에 실패하였습니다.");
			}
			
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			//response body node
			ObjectNode resBodyNode = new ObjectNode(factory);
			
			//response root node
			ObjectNode resRootNode = new ObjectNode(factory);

			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
		
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);

			return makeResponse(resObj, resRootNode);
		} catch ( Exception e ) {
			
			logger.error(">>>> Exception = ", e);
			
			return makeFailResponse("IMPL0002", e.getLocalizedMessage());
		}
	}
	
	/**
	 * @method convertNull2Str
	 * @param {String} value String Value
	 * @return {String} result 공백, null 값인 경우 공백 리턴
	 */
	private String convertNull2Str(String value) {
		
		String result = "";
		
		if ( !StringUtils.isBlank(value) ) {
			
			result = value;
		} 
		
		return result;
	}
	
	/**
	 * @method saveLogFile 
	 * @param {StringBuffer} logParams 로그파라미터 내용들
	 * @param {String} userId 사용자ID
	 * @param {String} packageName 패키지 이름
	 * @return {boolean} result 성공여부 true?false?
	 * @throws Exception
	 * @description 로그 파일을 저장하는 메소드
	 */
	private boolean saveLogFile(StringBuffer logParams, String userId, String packageName) throws Exception {
		
		String 				dateFormat 			= "yyyyMMdd";
		boolean 			result 				= false;
		Date 				date 				= new Date();
		SimpleDateFormat 	simpleDateFormat 	= new SimpleDateFormat(dateFormat); 
		String 				SAVE_FILE_PATH 		= SmartConfig.getString("coway.log.file.path", "/app/webapp/SMART_HOME/client_logs/");
		BufferedWriter 		bw					= null;

		try {
			
			String			toDate			= simpleDateFormat.format(date);
			String			saveFilePath	= SAVE_FILE_PATH + File.separator + toDate; 
			
			//오늘날짜로된 디렉토리가 없다면 생성
			File			fileSavePath	= new File(saveFilePath);
			if( !fileSavePath.exists() ) {
				fileSavePath.mkdir();
			}
			
			String 			fileName 		= saveFilePath + File.separator + userId + "_" + packageName + ".txt";
			File			logFile			= new File(fileName);
			if(!logFile.exists()) {
				logFile.createNewFile();
			}
			
			logger.debug(">>>> saveLogFileFullPath = " + fileName);
			bw = new BufferedWriter(new FileWriter(logFile, true));
			bw.write(logParams.toString());
			logger.debug(">>>> 로그 파일 저장 성공!!!");
			result = true;
			
			logger.info(">>>> saveLogFile End");
			return result;
		} catch ( FileNotFoundException e ) {
			logger.error(">>>> 해당 경로를 찾을 수 없습니다.", e);
			throw e;
		} catch ( IOException e ) {
			logger.error(">>>> 파일 쓰기에 실패하였습니다.", e);
			throw e;
		} catch ( Exception e ) {
			logger.error(">>>> 알 수 없는 오류 입니다.", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}
}
