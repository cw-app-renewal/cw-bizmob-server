package adapter.cism;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.CISM0004.CISM0004Request;
import adapter.model.CISM0004.CISM0004Request_Body;
import adapter.model.CISM0004.CISM0004Response;
import adapter.model.CISM0004.CISM0004Response_Body;
import adapter.model.header.CowayCommonHeader;
import common.ItrustAPIUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CISM0004" })
public class CISM0004_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CISM0004_Adapter.class);

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

CISM0004Request				request		=	new CISM0004Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CISM0004Request_Body		reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CISM0004Response_Body		resBody		=	new CISM0004Response_Body();
		CISM0004Response			response	=	new CISM0004Response();
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + reqBody.getAccessToken());
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "10");
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.validate").replace("{serial}", reqBody.getSerial().toString());
				  
			long 					startTime			= System.currentTimeMillis();
				  
			ResponseEntity<ObjectNode> resEntity = ItrustAPIUtil.getSendMessage(url, headers);	  
			
			long					endTime						= System.currentTimeMillis();

			logger.debug("### ResponseEntity : " + resEntity.toString());
			
			if(!resEntity.getStatusCode().equals( HttpStatus.OK )) {
				return common.AdapterUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE , CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBody);
			}
			
			JsonNode 	responseBody 	= resEntity.getBody();
			JsonNode	getHeaderNode	= responseBody.findPath(CodesEx.TR_HEADER);
			
			String resultCode 		= getHeaderNode.findPath("result").asText();
			String resultMessage 	= getHeaderNode.findPath("message").asText();
			
			logger.debug("### resultCode : " + resultCode);
			logger.debug("### resultMessage : " + resultMessage);
			
			logger.debug("### resEntity.getBody => " + responseBody.toString());
			
			// 정상성공.
			if( StringUtils.equalsIgnoreCase(resultCode, CodesEx._API_SUCCESS) ){
				
				String		productLine 		= 	responseBody.findPath("productLine").asText();
				String		productLineCode 	= 	responseBody.findPath("productLineCode").asText();
				String 		productCode			= 	responseBody.findPath("productCode").asText();
				String		materialCode 		= 	responseBody.findPath("materialCode").asText();
				boolean  	ioCareYn 			= 	responseBody.findPath("ioCareYn").asBoolean();
				boolean		wifiYn 				= 	responseBody.findPath("wifiYn").asBoolean();
				boolean		bleYn 				= 	responseBody.findPath("bleYn").asBoolean();
				boolean		smartDetectionYn 	= 	responseBody.findPath("smartDetectionYn").asBoolean();
				
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
				
				return common.AdapterUtil.makeResponse(obj, response.toJsonNode(), trCode, (endTime - startTime));
			} else {
				
				return common.AdapterUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE , CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBody);
			}
			
		} catch (RestClientException e) {
			logger.error(e.getMessage(), e);
			return common.AdapterUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE, CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBody);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return common.AdapterUtil.makeFailResponse(obj, CodesEx.SYSTEM_ERROR_CODE , CodesEx.SYSTEM_ERROR_MESSAGE, trCode, reqBody);
		}
	}

}
