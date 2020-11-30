package adapter.model.sample.CR011;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.header.HWHeader;

/**
 * 코디동행 설문여부
테스트
 */
public class CR011Response {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private CR011Response_Body body;

	public CR011Response() {
	}

	public CR011Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CR011Response_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public CR011Response_Body getBody() {
		return this.body;
	}

	public void setBody(CR011Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}