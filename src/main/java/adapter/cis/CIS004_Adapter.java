package adapter.cis;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.cis.service.GetAccessTokenService;
import adapter.model.CIS004.CIS004Request;
import adapter.model.CIS004.CIS004Request_Body;
import adapter.model.CIS004.CIS004Response;
import adapter.model.CIS004.CIS004Response_Body;
import adapter.model.header.CowayCommonHeader;
import common.ResponseUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CIS004" })
public class CIS004_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CIS004_Adapter.class);
	@Autowired
	private GetAccessTokenService getAccessTokenService;
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CIS004Request				request		=	new CIS004Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CIS004Request_Body			reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CIS004Response_Body			resBody		=	new CIS004Response_Body();
		CIS004Response				response	=	new CIS004Response();
		
		JsonNode 					reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 					reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
		try {
			
			String accessToken = getAccessTokenService.getAccessToken();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "10");
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.validate").replace("{serial}", reqBody.getSerial().toString());
				  
			long 					startTime			= System.currentTimeMillis();
			ObjectNode				responseBody 		= ItrustAPIUtil.getSendMessage(url, headers);
			long					endTime				= System.currentTimeMillis();

			JsonNode				getHeaderNode		= responseBody.findPath(CodesEx.TR_HEADER);
			
			String 					resultCode 			= getHeaderNode.findPath("result").getTextValue();
			String 					resultMessage 		= getHeaderNode.findPath("message").getTextValue();
			
			// 정상성공.
			if( StringUtils.equalsIgnoreCase(resultCode, CodesEx._API_SUCCESS) ){
				
				String		productLine 		= 	responseBody.findPath("productLine").getTextValue();
				String		productLineCode 	= 	responseBody.findPath("productLineCode").getTextValue();
				String 		productCode			= 	responseBody.findPath("productCode").getTextValue();
				String		materialCode 		= 	Integer.toString(responseBody.findPath("materialCode").getIntValue());
				boolean  	ioCareYn 			= 	responseBody.findPath("ioCareYn").getBooleanValue();
				boolean		wifiYn 				= 	responseBody.findPath("wifiYn").getBooleanValue();
				boolean		bleYn 				= 	responseBody.findPath("bleYn").getBooleanValue();
				boolean		smartDetectionYn 	= 	responseBody.findPath("smartDetectionYn").getBooleanValue();
				
				resBody.setBleYn(bleYn);
				resBody.setIoCareYn(ioCareYn);
				resBody.setMaterialCode(materialCode);
				resBody.setProductCode(productCode);
				resBody.setProductLineCode(productLineCode);
				resBody.setSmartDetectionYn(smartDetectionYn);
				resBody.setProductLine(productLine);
				resBody.setWifiYn(wifiYn);
				
				response.setHeader(reqHeader);
				response.setBody(resBody);
				
				return ResponseUtil.makeResponse(obj, response.toJsonNode(), trCode, (endTime - startTime), reqBodyNode, this.getClass().getName());
			} else {
				
				return ResponseUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE, CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBodyNode, null, this.getClass().getName());
			}
			
		} catch (RestClientException e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE, CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBodyNode, e, this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, CodesEx.SYSTEM_ERROR_CODE, CodesEx.SYSTEM_ERROR_MESSAGE, trCode, reqBodyNode, e, this.getClass().getName());
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			return ResponseUtil.makeFailResponse(obj, CodesEx.SYSTEM_ERROR_CODE, CodesEx.SYSTEM_ERROR_MESSAGE, trCode, reqBodyNode, null, this.getClass().getName());
		}
	}

}
