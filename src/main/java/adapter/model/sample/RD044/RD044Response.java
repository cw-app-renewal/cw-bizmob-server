package adapter.model.sample.RD044;


import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.sample.header.HWHeader;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 작업실적조회
작업실적조회
 */
public class RD044Response {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private RD044Response_Body body;

	public RD044Response() {
	}

	public RD044Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new RD044Response_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public RD044Response_Body getBody() {
		return this.body;
	}

	public void setBody(RD044Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}