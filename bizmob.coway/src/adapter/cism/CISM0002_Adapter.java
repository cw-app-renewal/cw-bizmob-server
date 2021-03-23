package adapter.cism;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.cism.service.GetAccessTokenService;
import adapter.model.CISM0002.CISM0002Request;
import adapter.model.CISM0002.CISM0002Request_Body;
import adapter.model.CISM0002.CISM0002Response;
import adapter.model.CISM0002.CISM0002Response_Body;
import adapter.model.CISM0002.CISM0002Response_Body_content;
import adapter.model.CISM0002.CISM0002Response_Body_lastEvaluatedKey;
import adapter.model.header.CowayCommonHeader;
import common.ItrustAPIUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CISM0002" })
public class CISM0002_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CISM0002_Adapter.class);
	@Autowired
	private GetAccessTokenService getAccessTokenService;
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CISM0002Request				request		=	new CISM0002Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CISM0002Request_Body		reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CISM0002Response_Body		resBody		=	new CISM0002Response_Body();
		CISM0002Response			response	=	new CISM0002Response();
		
		
		try {
			
			String accessToken = getAccessTokenService.getAccessToken();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "11");
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.smart.list").replace("{serial}", reqBody.getSerial().toString());
				  url = url.replace("{id}", reqBody.getCreationDt().toString());
			
			long 					startTime			= System.currentTimeMillis();
			
			ResponseEntity<ObjectNode> resEntity = ItrustAPIUtil.getSendMessage(url, headers);
			
			long					endTime						= System.currentTimeMillis();

			logger.debug("### ResponseEntity : " + resEntity.toString());
			
			if(!resEntity.getStatusCode().equals( HttpStatus.OK )) {
				return common.AdapterUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE , CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBody);
			}
			
			ObjectNode	responseBody		= 	resEntity.getBody();
			JsonNode	getHeaderNode		= responseBody.findPath(CodesEx.TR_HEADER);
			
			String resultCode 		= getHeaderNode.findPath("result").getTextValue();
			String resultMessage 	= getHeaderNode.findPath("message").getTextValue();
			
			logger.debug("### resultCode : " + resultCode);
			logger.debug("### resultMessage : " + resultMessage);
			
			// 정상성공.
			if( StringUtils.equalsIgnoreCase(resultCode, CodesEx._API_SUCCESS) ){
				logger.debug("### Device Sterilize Time Success");
				
				JsonNode	payload				=	responseBody.findValue("payload");
				JsonNode	lastEvaluatedKey	=	null;
				JsonNode 	content				=	null;
				int 		numberOfElements	=	0;
				int			size				=	0;
				
				List<CISM0002Response_Body_content> contents = new ArrayList<CISM0002Response_Body_content>();
				List<CISM0002Response_Body_lastEvaluatedKey> list = new ArrayList<CISM0002Response_Body_lastEvaluatedKey>();
				
				if(payload != null && payload.size() > 0) {
					
					lastEvaluatedKey	=	payload.findValue("lastEvaluatedKey");
					content				=	payload.findValue("content");
					
					numberOfElements	=	payload.findValue("numberOfElements").getIntValue();
					size				=	payload.findValue("size").getIntValue();
					
					if(content != null && content.size() > 0) {
						
						for(int i = 0; i < content.size(); i++) {
							
							CISM0002Response_Body_content contentData = new CISM0002Response_Body_content();
							
							JsonNode data = content.get(i);
							
							String apiNo = data.findPath("apiNo").getTextValue();
							String serial = data.findPath("serial").getTextValue();
							String requestId = data.findPath("requestId").getTextValue();
							String appTypeCode = data.findPath("appTypeCode").getTextValue();
							String occDt = data.findPath("occDt").getTextValue();
							String creationDt = Long.toString(data.findPath("creationDt").getLongValue());
							boolean normalOprYn = data.findPath("normalOprYn").getBooleanValue();
							
							contentData.setApiNo(apiNo);
							contentData.setAppTypeCode(appTypeCode);
							contentData.setCreationDt(creationDt);
							contentData.setNormalOprYn(normalOprYn);
							contentData.setOccDt(occDt);
							contentData.setRequestId(requestId);
							contentData.setSerial(serial);
							
							contents.add(contentData);
						}
					}
					
					if(lastEvaluatedKey != null && lastEvaluatedKey.size() > 0) {
						
						CISM0002Response_Body_lastEvaluatedKey data = new CISM0002Response_Body_lastEvaluatedKey();
						
						data.setCreationDt(lastEvaluatedKey.findPath("creationDt").getTextValue());
						data.setSerial(lastEvaluatedKey.findPath("serial").getTextValue());
						
						list.add(data);
					}
				}
				
				resBody.setLastEvaluatedKey(list);
				resBody.setContent(contents);
				resBody.setNumberOfElements(numberOfElements);
				resBody.setSize(size);
				
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
