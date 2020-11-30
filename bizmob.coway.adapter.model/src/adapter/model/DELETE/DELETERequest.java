package adapter.model.DELETE;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;

/**
 * 웅진 코웨이 이미지 삭제
웅진 코웨이 이미지 삭제
 */
public class DELETERequest {
	/**
	 * header object
	 */
	private CowayCommonHeader header;
	/**
	 * body object
	 */
	private DELETERequest_Body body;

	public DELETERequest() {
	}

	public DELETERequest(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new DELETERequest_Body(bodyNode);
	}

	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	public DELETERequest_Body getBody() {
		return this.body;
	}

	public void setBody(DELETERequest_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}