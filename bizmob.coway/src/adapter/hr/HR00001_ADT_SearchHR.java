package adapter.hr;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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


/**
 * 
 * 2020.03.02
 * HR 조회
 * @author regar
 * 
 * **/

@Adapter( trcode = {"HR00001"} )
public class HR00001_ADT_SearchHR extends AbstractTemplateAdapter implements IAdapterJob{

	private ILogger logger = LoggerService.getLogger(HR00001_ADT_SearchHR.class);
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {
			
			JsonNode 	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
			JsonNode 	reqHeaderNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
			JsonNode 	reqBodyNode 	= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
			String 		userId 			= (String) JsonUtil.getValue(reqBodyNode, "empNo");
			
			String 		serverUrl 		= SmartConfig.getString("hr.server.url") + SmartConfig.getString("hr.daily.url");
			serverUrl += "/" + userId;
			logger.debug("userId :: " + userId +  "serverUrl :: " + serverUrl);
			
			HttpClient 		httpClient 		= new DefaultHttpClient();
			httpClient.getParams().setParameter("http.protocol.expect-continue", false);//HttpClient POST 요청시 Expect 헤더정보 사용 x
			httpClient.getParams().setParameter("http.connection.timeout", 3 * 1000);// 원격 호스트와 연결을 설정하는 시간
			httpClient.getParams().setParameter("http.socket.timeout",  3 * 1000);//데이터를 기다리는 시간
			httpClient.getParams().setParameter("http.connection-manager.timeout",  3 * 1000);// 연결 및 소켓 시간 초과 
			httpClient.getParams().setParameter("http.protocol.head-body-timeout",  3 * 1000);
	        
			HttpGet 		getMethod 		= new HttpGet(serverUrl);
			
			HttpResponse 	httpResponse 	= httpClient.execute(getMethod);
			HttpEntity 		resEntity	 	= httpResponse.getEntity();
			
			InputStream 		is					= null;
			try {
				is = resEntity.getContent();
				
				String 				resBody 		= IOUtils.toString( is, "UTF-8" );
				
				JsonNode 			resBodyNode 	= JsonUtil.toObject(resBody, JsonNode.class);
				boolean 			result 			= (Boolean)JsonUtil.getValue(resBodyNode, "success");
				
				if(result) {
					JsonNodeFactory 	factory 		= JsonNodeFactory.instance;
					JsonAdaptorObject 	resObj 			= new JsonAdaptorObject();
					ObjectNode 			resRootNode 	= new ObjectNode(factory);
					resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
					resRootNode.put(Codes._JSON_MESSAGE_BODY, 	resBodyNode);
					
					return makeResponse(resObj, resRootNode);
				} else {
					String errorCode = (String)JsonUtil.getValue(resBodyNode, "errorCode");
					String errorMsg = (String)JsonUtil.getValue(resBodyNode, "errorMsg");
					
					logger.error(errorCode + " :: " + resBodyNode.toString());
					return makeFailReesponse("HR00001" +errorCode, errorMsg);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				
				logger.error("conn.getResponseCode() ::: " + String.valueOf(httpResponse.getStatusLine().getStatusCode()));
				return makeFailReesponse("HR00001_IMPL0002", "HR정보조회 요청중 오류가 발생하였습니다");
			} finally {
				if(is != null) {
					is.close();
				}
			}
			
		} catch( Exception e ) {
			
			logger.error("HR00001 Adapter Exception : ", e);
			return makeFailReesponse("HR00001_IMPL0003", "HR정보조회 요청중 오류가 발생하였습니다");
		} 
	}
}
