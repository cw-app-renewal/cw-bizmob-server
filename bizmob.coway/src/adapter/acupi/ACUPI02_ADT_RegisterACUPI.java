package adapter.acupi;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import common.BizmobUtil;
import common.ResponseUtil;


/**
 * 2020.03.02
 * ACUPI 등록
 * @author regar
 * 
**/

@Adapter( trcode = {"ACUPI02"} )
public class ACUPI02_ADT_RegisterACUPI extends AbstractTemplateAdapter implements IAdapterJob{

	private ILogger logger = LoggerService.getLogger(ACUPI02_ADT_RegisterACUPI.class);
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 	reqHeaderNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 	reqBodyNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String		trCode			= reqHeaderNode.findPath("trcode").getValueAsText();
		String		errorMsg		= "고객 개인정보 수집/활용 데이터를 등록하던 중 오류가 발생하였습니다.";
		try {			
			String 		reqPayload 		= JsonUtil.toJson(reqBodyNode);
			String 		serverUrl 		= SmartConfig.getString("acupi.server.url");
			String 		originKey 		= SmartConfig.getString("acupi.origin.key");
			
			String 		version 		= reqBodyNode.findPath("version").getValueAsText() ;  // 버전
			serverUrl += "/acupi/" + version;
			
			HttpsURLConnection 	httpsURLConnection 	= BizmobUtil.getHttpsURLConnection(serverUrl);
			InputStream 		is					= null;
			try {
				long 					start 		= System.currentTimeMillis();
				httpsURLConnection.setRequestProperty("origin_key", originKey);
				httpsURLConnection.setDoOutput(true);
				DataOutputStream dos = new DataOutputStream(httpsURLConnection.getOutputStream());
				dos.writeBytes(reqPayload);
				dos.flush();
				dos.close();
				httpsURLConnection.connect();
				long 					end 		= System.currentTimeMillis();
				
				if (HttpURLConnection.HTTP_OK <= httpsURLConnection.getResponseCode() &&  httpsURLConnection.getResponseCode() <= 299) {
					
					is 	= httpsURLConnection.getInputStream();
					
					String 				resBody 		= IOUtils.toString( is );
					logger.debug("resBody : " + resBody);
					JsonNode 			resBodyNode 	= JsonUtil.toObject(resBody, JsonNode.class);

					JsonNodeFactory 	factory 		= JsonNodeFactory.instance;
					
					ObjectNode 			resRootNode 	= new ObjectNode(factory);
					resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
					resRootNode.put(Codes._JSON_MESSAGE_BODY, 	resBodyNode);
					
					return ResponseUtil.makeResponse(obj, resRootNode, trCode, (end - start), reqBodyNode,this.getClass().getName());
					
				} else {
					
					try {
						String	logMsg = String.valueOf(httpsURLConnection.getResponseCode()) + "::" + String.valueOf(httpsURLConnection.getResponseMessage());
						logger.error(logMsg);
					} catch (Exception e2) {
					}
					 
					return ResponseUtil.makeFailResponse(obj, "IMPL0001", errorMsg, trCode, reqBodyNode, null, this.getClass().getName());
				}
				
			} catch (Exception e) {
				String logMsg = "";
				
				try {
					logMsg = String.valueOf(httpsURLConnection.getResponseCode()) + "::" + String.valueOf(httpsURLConnection.getResponseMessage());	
				} catch (Exception e2) {
				}
				
				logger.error(logMsg, e); 
				return ResponseUtil.makeFailResponse(obj, "IMPL0002", errorMsg, trCode, reqBodyNode, e, this.getClass().getName());
			} finally {
				if(httpsURLConnection != null) {
					httpsURLConnection.disconnect();
				}
				if(is != null) {
					is.close();
				}
			}
			
		} catch( Exception e ) {
			logger.error("ACUPI02 Adapter Exception : ", e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0003", errorMsg, trCode, reqBodyNode, e, this.getClass().getName());
		}
		
	}

}
