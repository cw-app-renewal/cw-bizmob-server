package adapter.model.sample.WR002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.header.HWHeader;

/**
 * 로그인 설문내역 등록
테스트
 */
public class WR002Request {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private WR002Request_Body body;

	public WR002Request() {
	}

	public WR002Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new WR002Request_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public WR002Request_Body getBody() {
		return this.body;
	}

	public void setBody(WR002Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}