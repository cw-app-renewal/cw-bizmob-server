package adapter.common;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.hybrid.common.code.Codes;

import adapter.model.header.CowayCommonHeader;

public class SapCommonResponse {

	private ObjectNode sapCommonResponse;

	public void setSapCommonResponse(ObjectNode sapCommonResponse) {
	
		this.sapCommonResponse = sapCommonResponse;
	}
	
	public ObjectNode getSapCommonResponse() {
		
		return sapCommonResponse;
	}
	
	public SapCommonResponse() {
		JsonNodeFactory factory = JsonNodeFactory.instance;

		sapCommonResponse = new ObjectNode(factory);
	}
	
	public SapCommonResponse(CowayCommonHeader cowayCommonHeader) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		sapCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
		sapCommonResponse.put(Codes._JSON_MESSAGE_HEADER, toJsonNode(cowayCommonHeader));
		sapCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public SapCommonResponse(JsonNode cowayCommonHeader) throws AdapterException {
		
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		sapCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "T");
		returnMap.put("MESSAGE", "");
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
		sapCommonResponse.put(Codes._JSON_MESSAGE_HEADER, cowayCommonHeader);
		sapCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
	}
	
	public void setSapCommonHeader(JsonNode header) {
		sapCommonResponse.put(Codes._JSON_MESSAGE_HEADER, header);
	}
	
	public void setSapErrorMessage(String errMsg) throws AdapterException {
		//response
		JsonNodeFactory factory = JsonNodeFactory.instance;

		sapCommonResponse = new ObjectNode(factory);
		ObjectNode resBodyNode = new ObjectNode(factory);	
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("TYPE", "F");
		returnMap.put("MESSAGE", errMsg);
		resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
		
		sapCommonResponse.put(Codes._JSON_MESSAGE_BODY, resBodyNode);		
			
	}
	
	private JsonNode toJsonNode(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(obj);
	}
}
