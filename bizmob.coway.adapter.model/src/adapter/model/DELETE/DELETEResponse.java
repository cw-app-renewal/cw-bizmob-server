package adapter.model.DELETE;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;

/**
 * 웅진 코웨이 이미지 삭제
웅진 코웨이 이미지 삭제
 */
public class DELETEResponse {
	/**
	 * header object
	 */
	private CowayCommonHeader header;
	/**
	 * body object
	 */
	private DELETEResponse_Body body;

	public DELETEResponse() {
	}

	public DELETEResponse(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new DELETEResponse_Body(bodyNode);
	}

	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	public DELETEResponse_Body getBody() {
		return this.body;
	}

	public void setBody(DELETEResponse_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}