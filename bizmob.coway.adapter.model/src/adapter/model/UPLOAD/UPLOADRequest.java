package adapter.model.UPLOAD;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;

/**
 * 이미지 업로드
웅진 코웨이 이미지 업로드
 */
public class UPLOADRequest {
	/**
	 * header object
	 */
	private CowayCommonHeader header;
	/**
	 * body object
	 */
	private UPLOADRequest_Body body;

	public UPLOADRequest() {
	}

	public UPLOADRequest(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new UPLOADRequest_Body(bodyNode);
	}

	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	public UPLOADRequest_Body getBody() {
		return this.body;
	}

	public void setBody(UPLOADRequest_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}