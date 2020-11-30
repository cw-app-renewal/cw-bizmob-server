package adapter.model.NCCW905;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * NFC 호출 로그 등록
NFC 데이터 호출 로그 등록
 */
public class NCCW905Response {
	/**
	 * header object
	 */
	private CowayCommonHeader header = null;
	/**
	 * body object
	 */
	private NCCW905Response_Body body = null;

	public NCCW905Response() {
	}

	public NCCW905Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new NCCW905Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public NCCW905Response_Body getBody() {
		return this.body;
	}

	public void setBody(NCCW905Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW905Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}