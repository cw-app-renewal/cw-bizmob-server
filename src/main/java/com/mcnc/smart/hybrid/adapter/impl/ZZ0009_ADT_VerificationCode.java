package com.mcnc.smart.hybrid.adapter.impl;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.EncryptionUtil;
import common.ResponseUtil;

@Adapter(trcode = { "ZZ0009" })
public class ZZ0009_ADT_VerificationCode extends AbstractTemplateAdapter implements	IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(ZZ0009_ADT_VerificationCode.class);

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 	reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 	reqBodyNode 		= reqRootNode.findPath(Codes._JSON_MESSAGE_BODY);
		JsonNode 	reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		String 		trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		String 		encSavedCode 		= "";
		long 		start 				= 0;
		long 		end 				= 0;
		
		try {
		
			String key 				= reqBodyNode.path("appKey").getTextValue();
			String savedCode 		= SmartConfig.getString(key, "");
		
			if(savedCode.isEmpty()){
				logger.error("There is no value mapped to key[" + key + "]");
				return ResponseUtil.makeFailResponse(obj, "EMPTY", "매핑된 키값이 없습니다.", trCode, reqBodyNode, null, this.getClass().getName());
			} else {
				start			= System.currentTimeMillis();
				encSavedCode 	= EncryptionUtil.getEncryptAES256(savedCode);
				end				= System.currentTimeMillis();
			}
			
			String verificationCode = reqBodyNode.path("verificationCode").getTextValue();
			if(!verificationCode.equals(encSavedCode)){
				logger.error("The verification code does not match.");
				return ResponseUtil.makeFailResponse(obj, "MISMATCH", "검증코드가 일치하지 않습니다.", trCode, reqBodyNode, null, this.getClass().getName());
			}
			
			ObjectNode resRootNode = JsonNodeFactory.instance.objectNode();
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeaderNode);
			resRootNode.put(Codes._JSON_MESSAGE_BODY, JsonNodeFactory.instance.objectNode());
			
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			return ResponseUtil.makeResponse(resObj, resRootNode, trCode, (end - start), reqBodyNode,this.getClass().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		} 
	}

}
