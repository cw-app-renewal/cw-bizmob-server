package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject.TYPE;

public class ResponseUtil {

	private static ILogger logger = LoggerService.getLogger(BizmobUtil.class);
	
	public static JsonAdaptorObject makeResponse(JsonAdaptorObject obj, JsonNode resNode, String trCode, long legacyTime, String rfcName, JsonNode reqBodyNode, String adtName) {

		try {
			ObjectNode resultNode = JsonUtil.objectNode();
			resultNode.put("result", true);
			resultNode.put("error_code", "");
			resultNode.put("error_text", "");
			resultNode.put("info_text", "");
			
			obj = new JsonAdaptorObject();
			obj.put(TYPE.RESULT, resultNode);
			obj.put(TYPE.RESPONSE, resNode);
			
			JsonNode 	headerNode 		= (JsonNode) JsonUtil.getValue(resNode, "header");
			String 		clientName 	= (String) JsonUtil.getValue(headerNode, "message_version");
			String 		strResNode 		= JsonUtil.toJson(JsonUtil.getValue(resNode, "body"));
			int length = strResNode.length();
			if(length > 1024) {
				strResNode = strResNode.substring(0, 1024) + "...";
			}
			
			String 		appName 	= getAppName(obj);
    		ObjectNode 	logNode 	= JsonUtil.objectNode();
    		JsonUtil.putValue(logNode, "appName", 		appName);
    		JsonUtil.putValue(logNode, "client", 		clientName);
    		JsonUtil.putValue(logNode, "server", 		adtName);
			JsonUtil.putValue(logNode, "trCode", 		trCode);
			JsonUtil.putValue(logNode, "rfcName", 		rfcName);
			JsonUtil.putValue(logNode, "processedTime", legacyTime);
			JsonUtil.putValue(logNode, "request", 		reqBodyNode);
			JsonUtil.putValue(logNode, "response", 		strResNode);
			
			StringBuffer	logBuffer		= new StringBuffer();
			logBuffer.append("\n###################################################################\n")		
			.append(JsonUtil.toJson(logNode, true))
			.append("\n###################################################################\n");
			
			logger.info(logBuffer.toString());
			return obj;
		} finally {
			if(resNode != null) {
				resNode = null;
			}
		}
	}
	
	
	public static JsonAdaptorObject makeResponse(JsonAdaptorObject obj, JsonNode resNode, String trCode, long legacyTime, JsonNode reqBodyNode, String adtName) {

		try {
			ObjectNode resultNode = JsonUtil.objectNode();
			resultNode.put("result", true);
			resultNode.put("error_code", "");
			resultNode.put("error_text", "");
			resultNode.put("info_text", "");
			
			obj = new JsonAdaptorObject();
			obj.put(TYPE.RESULT, resultNode);
			obj.put(TYPE.RESPONSE, resNode);
			
			JsonNode 	headerNode 		= (JsonNode) JsonUtil.getValue(resNode, "header");
			String 		clientName 	= (String) JsonUtil.getValue(headerNode, "message_version");
			String 		strResNode 		= JsonUtil.toJson(JsonUtil.getValue(resNode, "body"));
			int length = strResNode.length();
			if(length > 1024) {
				strResNode = strResNode.substring(0, 1024) + "...";
			}
			
			String 		appName 	= getAppName(obj);
    		ObjectNode 	logNode 	= JsonUtil.objectNode();
    		JsonUtil.putValue(logNode, "appName", 			appName);
    		JsonUtil.putValue(logNode, "client", 			clientName);
    		JsonUtil.putValue(logNode, "server", 			adtName);
			JsonUtil.putValue(logNode, "trCode", 			trCode);
			JsonUtil.putValue(logNode, "processedTime", 	legacyTime);
			JsonUtil.putValue(logNode, "request", 			reqBodyNode);
			JsonUtil.putValue(logNode, "response", 			strResNode);
			
			StringBuffer	logBuffer		= new StringBuffer();
			logBuffer.append("\n###################################################################\n")		
			.append(JsonUtil.toJson(logNode, true))
			.append("\n###################################################################\n");
			
			logger.info(logBuffer.toString());
			return obj;
		} finally {
			if(resNode != null) {
				resNode = null;
			}
		}
	}
    
    public static JsonAdaptorObject makeFailResponse(JsonAdaptorObject resObj, String errorCode, String errorMessage, String trCode, Object reqBody, String rfcName, Exception exception, String adtName){

    	resObj.put(TYPE.RESULT, new SimpleJsonResponse(false, trCode + errorCode, errorMessage, rfcName).toJson());
    	
    	StringBuffer 	errorLogBuffer 		= new StringBuffer();
    	StringBuffer	logBuffer 			= null;
    	try {
			if( exception != null ) {
				errorLogBuffer.append(exception.fillInStackTrace().toString()).append("\n");
				
				StackTraceElement[] stacktrace = exception.getStackTrace();
				for (StackTraceElement element : stacktrace) {
					errorLogBuffer.append(element.toString()).append("\n");
				}
				
			}
    		
    		JsonNode 			rootNode 		= resObj.get(JsonAdaptorObject.TYPE.REQUEST);
    		JsonNode 			headerNode 		= rootNode.findValue(Codes._JSON_MESSAGE_HEADER);
    		String 				clientName 	= (String) JsonUtil.getValue(headerNode, "message_version");
    		
    		String 				appName 		= getAppName(resObj);
    		ObjectNode 			logNode 		= JsonUtil.objectNode();
    		JsonUtil.putValue(logNode, "appName", 		appName);
    		JsonUtil.putValue(logNode, "client", 		clientName);
    		JsonUtil.putValue(logNode, "server", 		adtName);
			JsonUtil.putValue(logNode, "trCode", 		trCode);
			JsonUtil.putValue(logNode, "time", 			getYYYYMMDDHHMISS());
			JsonUtil.putValue(logNode, "rfc", 			rfcName);
			JsonUtil.putValue(logNode, "request", 		reqBody);
			JsonUtil.putValue(logNode, "result", 		resObj.get(TYPE.RESULT));
			JsonUtil.putValue(logNode, "errorCode", 	errorCode);
			JsonUtil.putValue(logNode, "errorMessage", 	errorMessage);
			JsonUtil.putValue(logNode, "exception", 	errorLogBuffer.toString());
			
			logBuffer		= new StringBuffer();
			logBuffer.append("\n###################################################################\n")		
			.append(JsonUtil.toJson(logNode, true))
			.append("\n###################################################################\n");
			
			logger.error(logBuffer.toString());
	        
	        return resObj;
    	} finally {
			errorLogBuffer = null;
			logBuffer = null;
		}
	}
    
    public static JsonAdaptorObject makeFailResponse(JsonAdaptorObject resObj, String errorCode, String errorMessage, String trCode, Object reqBody, Exception exception, String adtName ){

    	resObj.put(TYPE.RESULT, new SimpleJsonResponse(false, trCode + errorCode, errorMessage, null ).toJson());
    	
    	StringBuffer 	errorLogBuffer 		= new StringBuffer();
    	StringBuffer	logBuffer 			= null;
    	try {
    		
    		if( exception != null ) {
				errorLogBuffer.append(exception.fillInStackTrace().toString()).append("\n");
				
				StackTraceElement[] stacktrace = exception.getStackTrace();
				for (StackTraceElement element : stacktrace) {
					errorLogBuffer.append(element.toString()).append("\n");
				}
				
			}
    		
    		JsonNode 			rootNode 		= resObj.get(JsonAdaptorObject.TYPE.REQUEST);
    		JsonNode 			headerNode 		= rootNode.findValue(Codes._JSON_MESSAGE_HEADER);
    		String 				clientName 	= (String) JsonUtil.getValue(headerNode, "message_version");
    		
    		String 				appName 		= getAppName(resObj);
    		ObjectNode 			logNode 		= JsonUtil.objectNode();
    		JsonUtil.putValue(logNode, "appName", 		appName);
    		JsonUtil.putValue(logNode, "client", 		clientName);
    		JsonUtil.putValue(logNode, "server", 		adtName);
    		JsonUtil.putValue(logNode, "trCode", 		trCode);
    		JsonUtil.putValue(logNode, "time", 			getYYYYMMDDHHMISS());
    		JsonUtil.putValue(logNode, "request", 		reqBody);
    		JsonUtil.putValue(logNode, "result", 		resObj.get(TYPE.RESULT));
    		JsonUtil.putValue(logNode, "errorCode", 	errorCode);
    		JsonUtil.putValue(logNode, "errorMessage", 	errorMessage);
    		JsonUtil.putValue(logNode, "exception", 	errorLogBuffer.toString());
    		
    		logBuffer		= new StringBuffer();
    		logBuffer.append("\n###################################################################\n")		
    		.append(JsonUtil.toJson(logNode, true))
    		.append("\n###################################################################\n");
    		
    		logger.error(logBuffer.toString());
            
            return resObj;
    	} finally {
			errorLogBuffer = null;
			logBuffer = null;
		}
	}
    
    public static String getYYYYMMDDHHMISS() {
    	
    	Calendar 				calendar = Calendar.getInstance();
    	SimpleDateFormat 		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	return sdf.format(calendar.getTime());
    }
    
    private static String getAppName(JsonAdaptorObject resObj) {
    	
    	String appName = "";
    	Object appKey = BizmobUtil.getSessionValue(resObj, "appKey");
		if(appKey != null) {
			try {
				appName = APP.valueOf((String)appKey).getValue();	
			} catch (Exception e) {
				appName = (String)appKey;
			}
			
		}
		
		return appName;
    }
}

enum APP{
	CODTANP0("svcMgr"),
	COCDANP0("cody"),
	COCDANP1("cody"),
	COCDANP2("svcMgr"),
	COWMANP0("smartSales"),
	COWMIOP0("smartSales"),
	COHCANP0("homeCareDr");

	private final String value;
	
	APP(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
