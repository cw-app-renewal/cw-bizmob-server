package adapter.model.sample.RD001;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.header.HWHeader;

/**
 * 인증받기
샘플
 */
public class RD001Request {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private RD001Request_Body body;

	public RD001Request() {
	}

	public RD001Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new RD001Request_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public RD001Request_Body getBody() {
		return this.body;
	}

	public void setBody(RD001Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}