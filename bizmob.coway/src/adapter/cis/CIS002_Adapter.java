package adapter.cis;

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
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.cis.service.GetAccessTokenService;
import adapter.model.CIS002.CIS002Request;
import adapter.model.CIS002.CIS002Request_Body;
import adapter.model.CIS002.CIS002Response;
import adapter.model.CIS002.CIS002Response_Body;
import adapter.model.CIS002.CIS002Response_Body_content;
import adapter.model.CIS002.CIS002Response_Body_lastEvaluatedKey;
import adapter.model.header.CowayCommonHeader;
import common.ResponseUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CIS002" })
public class CIS002_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CIS002_Adapter.class);
	@Autowired
	private GetAccessTokenService getAccessTokenService;
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CIS002Request				request		=	new CIS002Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CIS002Request_Body			reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CIS002Response_Body			resBody		=	new CIS002Response_Body();
		CIS002Response				response	=	new CIS002Response();
		
		JsonNode 					reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 					reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
		try {
			
			String accessToken = getAccessTokenService.getAccessToken();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "11");
			
			String reqCreationDt = reqBody.getCreationDt();
			if(reqCreationDt == null || "".equalsIgnoreCase(reqCreationDt)) {
				reqBody.setCreationDt("0");
			}
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.smart.list").replace("{serial}", reqBody.getSerial().toString());
				  url = url.replace("{id}", reqBody.getCreationDt().toString());
			
			long 					startTime			= System.currentTimeMillis();
			
			ResponseEntity<ObjectNode> resEntity = ItrustAPIUtil.getSendMessage(url, headers);
			
			long					endTime						= System.currentTimeMillis();

			logger.debug("### ResponseEntity : " + resEntity.toString());
			
			if(!resEntity.getStatusCode().equals( HttpStatus.OK )) {
				return ResponseUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE, CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBodyNode, null, this.getClass().getName());
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
				
				List<CIS002Response_Body_content> contents = new ArrayList<CIS002Response_Body_content>();
				List<CIS002Response_Body_lastEvaluatedKey> list = new ArrayList<CIS002Response_Body_lastEvaluatedKey>();
				
				if(payload != null && payload.size() > 0) {
					
					lastEvaluatedKey	=	payload.findValue("lastEvaluatedKey");
					content				=	payload.findValue("content");
					
					numberOfElements	=	payload.findValue("numberOfElements").getIntValue();
					size				=	payload.findValue("size").getIntValue();
					
					if(content != null && content.size() > 0) {
						
						for(int i = 0; i < content.size(); i++) {
							
							CIS002Response_Body_content contentData = new CIS002Response_Body_content();
							
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
						
						CIS002Response_Body_lastEvaluatedKey data = new CIS002Response_Body_lastEvaluatedKey();
						
						data.setCreationDt(Long.toString(lastEvaluatedKey.findPath("creationDt").getLongValue()));
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
