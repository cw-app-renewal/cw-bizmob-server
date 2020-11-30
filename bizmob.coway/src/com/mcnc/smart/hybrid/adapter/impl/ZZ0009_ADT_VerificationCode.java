package com.mcnc.smart.hybrid.adapter.impl;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import adapter.common.EncryptionUtil;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject.TYPE;

@Adapter(trcode = { "ZZ0009" })
public class ZZ0009_ADT_VerificationCode extends AbstractTemplateAdapter implements	IAdapterJob {

	private static final ILogger logger = LoggerService.getLogger(ZZ0009_ADT_VerificationCode.class);

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode request = obj.get(TYPE.REQUEST);
		JsonNode requestBody = request.path(Codes._JSON_MESSAGE_BODY);
		JsonNode requestHeader = request.path(Codes._JSON_MESSAGE_HEADER);
		String trCode = requestHeader.path(Codes._JSON_MESSAGE_TRCODE).getTextValue();
		
		String key = requestBody.path("appKey").getTextValue();
		
		String savedCode = SmartConfig.getString(key, "");
		String encSavedCode = "";
		
		try {
			if(savedCode.isEmpty()){
				logger.error("There is no value mapped to key[" + key + "]");
				return makeFailReesponse(trCode + "EMPTY", "There is no value mapped to key.");
			} else {
				encSavedCode = EncryptionUtil.getEncryptAES256(savedCode);
			}
		} catch (Exception e) {
			logger.error("", e);
			return makeFailReesponse(trCode + "SYSTEM", "System error.");
		} 
		
		String verificationCode = requestBody.path("verificationCode").getTextValue();
		if(!verificationCode.equals(encSavedCode)){
			logger.error("The verification code does not match.");
			return makeFailReesponse(trCode + "MISMATCH", "The verification code does not match.");
		}
		
		JsonAdaptorObject resObj = new JsonAdaptorObject();
		
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		response.put(Codes._JSON_MESSAGE_HEADER, requestHeader);
		response.put(Codes._JSON_MESSAGE_BODY, JsonNodeFactory.instance.objectNode());
		
		return makeResponse(resObj, response);
	}

}
