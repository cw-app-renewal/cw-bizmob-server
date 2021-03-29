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
import adapter.model.CIS003.CIS003Request;
import adapter.model.CIS003.CIS003Request_Body;
import adapter.model.CIS003.CIS003Response;
import adapter.model.CIS003.CIS003Response_Body;
import adapter.model.CIS003.CIS003Response_Body_itemTypes;
import adapter.model.CIS003.CIS003Response_Body_itemTypes_items;
import adapter.model.header.CowayCommonHeader;
import common.ResponseUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CIS003" })
public class CIS003_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CIS003_Adapter.class);
	@Autowired
	private GetAccessTokenService getAccessTokenService;
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CIS003Request				request		=	new CIS003Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CIS003Request_Body			reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CIS003Response_Body			resBody		=	new CIS003Response_Body();
		CIS003Response				response	=	new CIS003Response();

		JsonNode 					reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 					reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		
		try {
			
			String accessToken = getAccessTokenService.getAccessToken();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "10");
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.smart.detail").replace("{serial}", reqBody.getSerial().toString());
				  url = url.replace("{id}", reqBody.getRequestId().toString());
			
			long 					startTime			= System.currentTimeMillis();
			ObjectNode				responseBody 		= ItrustAPIUtil.getSendMessage(url, headers);
			long					endTime				= System.currentTimeMillis();
			
			JsonNode				getHeaderNode		= responseBody.findPath(CodesEx.TR_HEADER);
			String 					resultCode 			= getHeaderNode.findPath("result").getTextValue();
			
			// 정상성공.
			if( StringUtils.equalsIgnoreCase(resultCode, CodesEx._API_SUCCESS) ){
				
				JsonNode	payload			=	responseBody.findValue("payload");
				JsonNode 	itemTypes 		= 	null;
				
				String 		creationDt 		= 	"";
				String 		occDt 			= 	"";
				String 		appTypeCode 	= 	"";
				String 		requestId 		= 	"";
				String 		apiNo 			=	"";
				String 		serial 			= 	"";
				boolean 	normalOprYn 	= 	false;
				
				List<CIS003Response_Body_itemTypes> its = new ArrayList<CIS003Response_Body_itemTypes>();
				
				if(payload != null && payload.size() > 0) {
					
					creationDt 		= 	Long.toString(payload.findValue("creationDt").getLongValue());
					occDt 			= 	payload.findValue("occDt").getTextValue();
					appTypeCode 	= 	payload.findValue("appTypeCode").getTextValue();
					requestId 		= 	payload.findValue("requestId").getTextValue();
					apiNo 			=	payload.findValue("apiNo").getTextValue();
					serial 			= 	payload.findValue("serial").getTextValue();
					normalOprYn 	= 	payload.findValue("normalOprYn").getBooleanValue();
					
					itemTypes = payload.findValue("itemTypes");
					
					if(itemTypes != null && itemTypes.size() > 0) {
						
//						int testCnt = 5;
//						for(int i = 0; i < testCnt; i++) {
						for(int i = 0; i < itemTypes.size(); i++) {
							
							CIS003Response_Body_itemTypes itemTypesData = new CIS003Response_Body_itemTypes();
							
//							JsonNode data = itemTypes.get(0);
							JsonNode data = itemTypes.get(i);
							JsonNode items = data.findValue("items");
							
//							int 	sortNo				= 	(i+1);
//							String 	itemTypeName 		= 	"센서 기능 " + sortNo;
							String 	itemTypeCode 		= 	data.findPath("itemTypeCode").getTextValue();
							String 	itemTypeName 		= 	data.findPath("itemTypeName").getTextValue();
							int 	sortNo				= 	data.findPath("sortNo").getIntValue();
							boolean itemTypesNormalOprYn 	= 	data.findPath("normalOprYn").getBooleanValue();
							
							List<CIS003Response_Body_itemTypes_items> itsi = new ArrayList<CIS003Response_Body_itemTypes_items>();
							
							for(int y = 0; y < items.size(); y++) {
								
								CIS003Response_Body_itemTypes_items itemsData = new CIS003Response_Body_itemTypes_items();
								
								String 	itemId 				= 	items.findValue("itemId").getTextValue();
								String 	itemName 			= 	items.findValue("itemName").getTextValue();
								String 	symptoms 			= 	items.findValue("symptoms").getTextValue();
								String 	relateComponent 	= 	items.findValue("relateComponent").getTextValue();
								String  checkList			=	items.findValue("checkList").getTextValue();
								String  actItem				=	items.findValue("actItem").getTextValue();
								boolean itemNormalOprYn		=	items.findValue("normalOprYn").getBooleanValue();
								
								itemsData.setActItem(actItem);
								itemsData.setCheckList(checkList);
								itemsData.setItemId(itemId);
								itemsData.setItemName(itemName);;
								itemsData.setNormalOprYn(itemNormalOprYn);
								itemsData.setRelateComponent(relateComponent);
								itemsData.setSymptoms(symptoms);
								
								itsi.add(itemsData);
							}
							
							itemTypesData.setItems(itsi);
							itemTypesData.setItemTypeCode(itemTypeCode);
							itemTypesData.setItemTypeName(itemTypeName);
							itemTypesData.setNormalOprYn(itemTypesNormalOprYn);
							itemTypesData.setSortNo(sortNo);
							
							its.add(itemTypesData);
						}
					}
				}
				
				resBody.setApiNo(apiNo);
				resBody.setAppTypeCode(appTypeCode);
				resBody.setCreationDt(creationDt);
				resBody.setItemTypes(its);
				resBody.setNormalOprYn(normalOprYn);
				resBody.setOccDt(occDt);
				resBody.setRequestId(requestId);
				resBody.setSerial(serial);
				
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
