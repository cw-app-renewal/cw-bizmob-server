package adapter.model.sample.WR002;


import adapter.model.sample.header.HWHeader;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 로그인 설문내역 등록
테스트
 */
public class WR002Response {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private WR002Response_Body body;

	public WR002Response() {
	}

	public WR002Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new WR002Response_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public WR002Response_Body getBody() {
		return this.body;
	}

	public void setBody(WR002Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}