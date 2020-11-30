package adapter.acupi;

import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import common.AdapterUtil;


/**
 * 
 * 2020.03.02
 * ACUPI 히스토리 조회
 * @author regar
 * 
 * **/

@Adapter( trcode = {"ACUPI03"} )
public class ACUPI03_ADT_HistoryACUPI2 extends AbstractTemplateAdapter implements IAdapterJob{

	private ILogger logger = LoggerService.getLogger(ACUPI03_ADT_HistoryACUPI2.class);
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {
			
			JsonNode 	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode 	reqHeaderNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode 	reqBodyNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
			String 		serverUrl 		= SmartConfig.getString("acupi.server.url");
			String 		originKey 		= SmartConfig.getString("acupi.origin.key");
			
			String 		version 		= reqBodyNode.findPath("version").getValueAsText() ;  // 버전
			String 		cid 			= reqBodyNode.findPath("cid").getValueAsText() ;  	// cid
			String 		from 			= reqBodyNode.findPath("from").getValueAsText() ;  	// from
			String 		to 				= reqBodyNode.findPath("to").getValueAsText() ;  	// to
			
			serverUrl += "/acupi/" + version + "/" + cid + "/history?start_date=" + from + "&end_date=" + to;
			
			HttpsURLConnection 	httpsURLConnection 	= AdapterUtil.getHttpsURLConnection(serverUrl);
			InputStream 		is					= null;
			try {
				httpsURLConnection.setRequestProperty("origin_key", originKey);
				httpsURLConnection.connect();
				
				if (HttpURLConnection.HTTP_OK <= httpsURLConnection.getResponseCode() &&  httpsURLConnection.getResponseCode() <= 299) {
					
					is 	= httpsURLConnection.getInputStream();
					
					String 				resBody 		= IOUtils.toString( is );
					logger.debug("resBody : " + resBody);
					JsonNode 			resBodyNode 	= JsonUtil.toObject(resBody, JsonNode.class);

					JsonNodeFactory 	factory 		= JsonNodeFactory.instance;
					JsonAdaptorObject 	resObj 			= new JsonAdaptorObject();
					ObjectNode 			resRootNode 	= new ObjectNode(factory);
					resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
					resRootNode.put(Codes._JSON_MESSAGE_BODY, 	resBodyNode);
					
					return makeResponse(resObj, resRootNode);
				}else{
					logger.error("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseCode()));
					logger.error("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseMessage()));
					return makeFailReesponse("ACUPI03_IMPL0001", "고객 개인정보 수집/활용에 대한 조회 요청중 오류가 발생하였습니다");
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseCode()));
				logger.error("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseMessage()));
				return makeFailReesponse("ACUPI03_IMPL0002", "고객 개인정보 수집/활용에 대한 조회 요청중 오류가 발생하였습니다");
			} finally {
				if(httpsURLConnection != null) {
					httpsURLConnection.disconnect();
				}
				if(is != null) {
					is.close();
				}
			}
			
		} catch( Exception e ) {
			
			logger.error("ACUPI03 Adapter Exception : ", e);
			return makeFailReesponse("ACUPI03_IMPL0003", "고객 개인정보 수집/활용에 대한 조회 요청중 오류가 발생하였습니다");
		}
		
	}

}
