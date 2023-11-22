package adapter.model.CDS001;


import adapter.model.header.CowayCommonHeader;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 코드 테이블 싱크
코드 테이블 싱크
 */
public class CDS001Request {
	/**
	 * header object
	 */
	private CowayCommonHeader header;
	/**
	 * body object
	 */
	private CDS001Request_Body body;

	public CDS001Request() {
	}

	public CDS001Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CDS001Request_Body(bodyNode);
	}

	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	public CDS001Request_Body getBody() {
		return this.body;
	}

	public void setBody(CDS001Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}