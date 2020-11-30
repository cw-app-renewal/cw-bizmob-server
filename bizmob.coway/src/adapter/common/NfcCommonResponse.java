package adapter.common;

import java.io.Console;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import adapter.model.header.CowayCommonHeader;
import ch.qos.logback.classic.Logger;

import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.sap.tc.logging.ConsoleLog;

public class NfcCommonResponse {

	private ObjectNode nfcCommonResponse;

	public void setNfcCommonResponse(ObjectNode nfcCommonResponse) {
	
		this.nfcCommonResponse = nfcCommonResponse;
	}
	
	public ObjectNode getNfcCommonResponse() {
		
		return nfcCommonResponse;
	}
	
	public NfcCommonResponse() {
		JsonNodeFactory factory = JsonNodeFactory.instance;

		nfcCommonResponse = new ObjectNode(factory);
	}
	
	public NfcCommonResponse(JsonNode header, ArrayNode arrayNode) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		nfcCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		resBodyNode.put("O_ITAB", arrayNode);
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, header);
		nfcCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public NfcCommonResponse(JsonNode header, ObjectNode resultNode) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;
		
		nfcCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));		
		resBodyNode.putAll(resultNode);
				
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, header);
		nfcCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public NfcCommonResponse(CowayCommonHeader cowayCommonHeader) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		nfcCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));		
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, toJsonNode(cowayCommonHeader));
		nfcCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public NfcCommonResponse(JsonNode header) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		nfcCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, header);
		nfcCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public void setNfcCommonHeader(JsonNode header) {
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, header);
	}
	
	public void setNfcCommonHeader(CowayCommonHeader cowayCommonHeader) {
		nfcCommonResponse.put(Codes._JSON_MESSAGE_HEADER, toJsonNode(cowayCommonHeader));
	}
	
	public void setNfcErrorMessage(String errMsg) throws AdapterException {
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		nfcCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "F");
		returnMap.put("MESSAGE", errMsg);
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
		nfcCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
			
	}
	
	private JsonNode toJsonNode(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(obj);
	}
}
