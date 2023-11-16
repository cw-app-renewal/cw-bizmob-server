package adapter.model.sample.CD001;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.header.HWHeader;

/**
 * 사용자별 sync 테이블 초기화
테스트
 */
public class CD001Request {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private CD001Request_Body body;

	public CD001Request() {
	}

	public CD001Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CD001Request_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public CD001Request_Body getBody() {
		return this.body;
	}

	public void setBody(CD001Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}