package adapter.cism;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.CISM0001.CISM0001Request;
import adapter.model.CISM0001.CISM0001Request_Body;
import adapter.model.CISM0001.CISM0001Response;
import adapter.model.CISM0001.CISM0001Response_Body;
import adapter.model.header.CowayCommonHeader;
import common.ItrustAPIUtil;
import common.util.CodesEx;

@Adapter(trcode = { "CISM0001" })
public class CISM0001_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CISM0001_Adapter.class);

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		CISM0001Request				request		=	new CISM0001Request(obj);
		
		CowayCommonHeader			reqHeader	=	request.getHeader();
		CISM0001Request_Body		reqBody		=	request.getBody();
		String						trCode		=	reqHeader.getTrcode();
		CISM0001Response_Body		resBody		=	new CISM0001Response_Body();
		CISM0001Response			response	=	new CISM0001Response();
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + SmartConfig.getString("coway.oauth.basic"));
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			
			ObjectNode reqData = JsonNodeFactory.instance.objectNode();
			reqData.put("grant_type", 	reqBody.getGrantType());
			
			logger.debug("### reqData : " + reqData.toString());
			
			long 					startTime			= System.currentTimeMillis();
			
			String url = SmartConfig.getString("coway.oauth.main") + SmartConfig.getString("coway.oauth.token");
			
			ResponseEntity<ObjectNode> resEntity = ItrustAPIUtil.postSendMessage(url, headers, reqData);
			
			long					endTime						= System.currentTimeMillis();

			logger.debug("### ResponseEntity : " + resEntity.toString());
			
			if(!resEntity.getStatusCode().equals( HttpStatus.OK )) {
				return common.AdapterUtil.makeFailResponse(obj, CodesEx.API_DEV_ERROR_CODE , CodesEx.API_DEV_ERROR_MESSAGE, trCode, reqBody);
			}
			
			JsonNode responseBody 	= 	resEntity.getBody();
			logger.debug("### access_token API resEntity.getBody => " + responseBody.toString());
			
			String accessToken = responseBody.findValue("access_token").getTextValue();
			String jti = responseBody.findValue("jti").getTextValue();
			String scope = responseBody.findValue("scope").getTextValue();
			String tokenType = responseBody.findValue("token_type").getTextValue();
			int expiresIn = responseBody.findValue("expires_in").getIntValue();
			
			resBody.setAccessToken(accessToken);
			resBody.setJti(jti);
			resBody.setScope(scope);
			resBody.setTokenType(tokenType);
			resBody.setExpiresIn(expiresIn);
			
			response.setHeader(reqHeader);
			response.setBody(resBody);
			
			return common.AdapterUtil.makeResponse(obj, response.toJsonNode(), trCode, (endTime - startTime));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return common.AdapterUtil.makeFailResponse(obj, common.util.CodesEx.SYSTEM_ERROR_CODE , common.util.CodesEx.SYSTEM_ERROR_MESSAGE, trCode, reqBody);
		}
	}

}
