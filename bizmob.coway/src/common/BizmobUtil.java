package common;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject.TYPE;


public class BizmobUtil {

	private static ILogger logger = LoggerService.getLogger(BizmobUtil.class);
	
    public static boolean putSessionData(JsonAdaptorObject obj, String key, Object value) {
    	try {
    		if(obj.containsKey(TYPE.META)) {
    			ObjectNode node = (ObjectNode) obj.get(TYPE.META);
    			JsonUtil.putValue(node, key, value);
    			return true;
    		} else {
    			return false;
    		}
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		return false;
    	}
	}
	
	/**
     * 세션에 저장되어 있는 TRANSACTION_ID를 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @return TRANSACTION_ID 값. 만약, 세션 data가 없거나, TRANSACTION_ID가 없으면 0
     */
    public static long getTransactionId(JsonAdaptorObject obj) {
        long id = 0;
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                id = (Long) JsonUtil.getValue(node, "TRANSACTION_ID");
            } catch(Exception e) {
                id = 0;
            }
        }
        
        return id;
    }
    
    /**
     * 세션에 저장되어 있는 값을 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @param key 세션에 저장한 key 이름
     * @return 세션에 저장되어 있는 값 값. 만약, 세션 data가 없거나, key가 없으면 null
     */
    public static Object getSessionValue(JsonAdaptorObject obj, String key) {
        Object data = null;
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                JsonNode aNode = JsonUtil.find(node, key);
                
                if(!aNode.isMissingNode()) {
                    data = JsonUtil.getValue(node, key);
                }
            } catch(Exception e) {
                data = null;
            }
        }
        
        return data;
    }
    
    /**
     * 세션 ID를 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @return 세션 ID
     */
    public static String getSessionId(JsonAdaptorObject obj) {
        String id = "Unknown";
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                id = (String) JsonUtil.getValue(node, "id");
            } catch(Exception e) {
                id = "Unknown";
            }
        }
        
        return id;
    }
    
    /** SSL 무시 커넥션
     * @param receiverURL 대상URL
     * @return HttpsURLConnection
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static HttpsURLConnection getHttpsURLConnection(String receiverURL) throws NoSuchAlgorithmException, KeyManagementException, IOException {
    
    	 javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[] { new javax.net.ssl.X509TrustManager() {
             public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null;}
             public void checkClientTrusted(X509Certificate[] certs, String authType) {}
             public void checkServerTrusted(X509Certificate[] certs, String authType) {}
     		}
    	 };
    	 
	     javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL"); 
	     sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
	     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	     HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	         	public boolean verify(String string,SSLSession ssls) { return true; }
				}
			);
//	     System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2"); 
		
		URL url = new URL(null, receiverURL, new sun.net.www.protocol.https.Handler());
		
		HttpsURLConnection conn = (HttpsURLConnection ) url.openConnection();
		conn.setRequestProperty("Accept", "application/json");
		
		return conn;
    }
    
    public static JsonAdaptorObject makeResponse(JsonAdaptorObject obj, JsonNode resNode, String trCode, long legacyTime) {
		
    	ObjectNode resultNode = JsonUtil.objectNode();
		resultNode.put("result", true);
		resultNode.put("error_code", "");
		resultNode.put("error_text", "");
		resultNode.put("info_text", "");
		
		obj.put(TYPE.RESULT, resultNode);
		obj.put(TYPE.RESPONSE, resNode);
		
		String 			reqJson 		= JsonUtil.toJson(obj.get(TYPE.REQUEST), false);
		String			trName			= SmartConfig.getString(trCode + ".tr.name", "");
		StringBuffer	logBuffer		= new StringBuffer();
		try {
			logBuffer.append("\n###################################################################\n")
			.append("LegacyTime: " + legacyTime +  " ms\n")
			.append(trCode + " - " + trName +  "\n")
			.append("RESULT	: " + JsonUtil.toJson(obj.get(TYPE.RESULT), false )+ "\n")
			.append("REQ	: " + reqJson + "\n")
			.append("###################################################################\n");
			
			//안정화 기간동안 responseBody 출력
			//boolean isStableStep = SmartConfig.getBoolean("is.stable.step", false);
			if(true){
				logBuffer.append("RESP	: ").append(resNode.path(Codes._JSON_MESSAGE_BODY)).append("\n")
				.append("###################################################################\n");
			}
						
			logger.info(logBuffer.toString());
			
			return obj;
		} finally {
			if(logBuffer != null) {
				logBuffer = null;
			}
		}
	}
    
    public static JsonAdaptorObject makeFailResponse(JsonAdaptorObject resObj, String errorCode, String errorMessage, String trCode, Object reqBody){
    	
    	String error = trCode + errorCode;
    	
    	resObj.put(TYPE.RESULT, new SimpleJsonResponse(false, error, errorMessage, null).toJson());
    	
    	String			reqJson 		= JsonUtil.toJson(reqBody, false);
		String			trName			= SmartConfig.getString(trCode + ".tr.name");	
		
		StringBuffer	logBuffer		= new StringBuffer();
		logBuffer.append("\n###################################################################\n")
		.append(trCode + " - " + trName +  "\n")
		.append("RESULT	: " + JsonUtil.toJson(resObj.get(TYPE.RESULT), false )+ "\n")
		.append("REQ	: " + reqJson + "\n")
		.append("ERROR	: "  + error + " - " + errorMessage + "\n") 
		.append("###################################################################\n");
		
		logger.info(logBuffer.toString());
		
        return resObj;
	}
}
