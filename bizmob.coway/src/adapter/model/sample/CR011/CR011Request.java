package adapter.model.sample.CR011;


import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.sample.header.HWHeader;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 코디동행 설문여부
테스트
 */
public class CR011Request {
	/**
	 * header object
	 */
	private HWHeader header;
	/**
	 * body object
	 */
	private CR011Request_Body body;

	public CR011Request() {
	}

	public CR011Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new HWHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CR011Request_Body(bodyNode);
	}

	public HWHeader getHeader() {
		return this.header;
	}

	public void setHeader(HWHeader header) {
		this.header = header;
	}

	public CR011Request_Body getBody() {
		return this.body;
	}

	public void setBody(CR011Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}