package adapter.cism;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.CISM0003.CISM0003Request;
import adapter.model.CISM0003.CISM0003Request_Body;
import adapter.model.CISM0003.CISM0003Response;
import adapter.model.CISM0003.CISM0003Response_Body;
import adapter.model.CISM0003.CISM0003Response_Body_itemTypes;
import adapter.model.CISM0003.CISM0003Response_Body_itemTypes_items;
import adapter.model.header.CowayCommonHeader;
import common.ItrustAPIUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CISM0003" })
public class CISM0003_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CISM0003_Adapter.class);

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CISM0003Request				request		=	new CISM0003Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CISM0003Request_Body		reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CISM0003Response_Body		resBody		=	new CISM0003Response_Body();
		CISM0003Response			response	=	new CISM0003Response();

		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + reqBody.getAccessToken());
			headers.add("Content-Type", "application/json");
			headers.add("X-IoCare-Request-Type", "10");
			
			String url = SmartConfig.getString("coway.iocare.main"); 
				  url += SmartConfig.getString("coway.iocare.smart.detail").replace("{serial}", reqBody.getSerial().toString());
				  url = url.replace("{id}", reqBody.getRequestId().toString());
			
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
				
				JsonNode	payload			=	responseBody.findValue("payload");
				JsonNode 	itemTypes 		= 	payload.findValue("itemTypes");
				
				String 		creationDt 		= 	payload.findValue("creationDt").getTextValue();
				String 		occDt 			= 	payload.findValue("occDt").getTextValue();
				String 		appTypeCode 	= 	payload.findValue("appTypeCode").getTextValue();
				String 		requestId 		= 	payload.findValue("requestId").getTextValue();
				String 		apiNo 			=	payload.findValue("apiNo").getTextValue();
				String 		serial 			= 	payload.findValue("serial").getTextValue();
				boolean 	normalOprYn 	= 	payload.findValue("normalOprYn").getBooleanValue();
				
				List<CISM0003Response_Body_itemTypes> its = new ArrayList<CISM0003Response_Body_itemTypes>();
				
				for(int i = 0; i < itemTypes.size(); i++) {
					
					CISM0003Response_Body_itemTypes itemTypesData = new CISM0003Response_Body_itemTypes();
					
					JsonNode data = itemTypes.get(i);
					JsonNode items = data.findValue("items");
					
					String 	itemTypeCode 		= 	data.findPath("itemTypeCode").getTextValue();
					String 	itemTypeName 		= 	data.findPath("itemTypeName").getTextValue();
					int 	sortNo				= 	data.findPath("sortNo").getIntValue();
					boolean itemTypesNormalOprYn 	= 	data.findPath("normalOprYn").getBooleanValue();
					
					List<CISM0003Response_Body_itemTypes_items> itsi = new ArrayList<CISM0003Response_Body_itemTypes_items>();
					
					for(int y = 0; y < items.size(); y++) {
						
						CISM0003Response_Body_itemTypes_items itemsData = new CISM0003Response_Body_itemTypes_items();
						
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
