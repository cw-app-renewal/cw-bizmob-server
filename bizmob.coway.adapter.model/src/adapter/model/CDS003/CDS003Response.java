package adapter.model.CDS003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;

/**
 * 작업 목록 일괄 수신
작업 묵록 일괄 수신
 */
public class CDS003Response {
	/**
	 * header object
	 */
	private CowayCommonHeader header;
	/**
	 * body object
	 */
	private CDS003Response_Body body;

	public CDS003Response() {
	}

	public CDS003Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CDS003Response_Body(bodyNode);
	}

	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	public CDS003Response_Body getBody() {
		return this.body;
	}

	public void setBody(CDS003Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}