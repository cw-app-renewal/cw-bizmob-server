package adapter.model.sample.RD044;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.header.HWHeader;

/**
 * 작업실적조회
작업실적조회
 */
public class RD044Request {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private RD044Request_Body body;

	public RD044Request() {
	}

	public RD044Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new RD044Request_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public RD044Request_Body getBody() {
		return this.body;
	}

	public void setBody(RD044Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}