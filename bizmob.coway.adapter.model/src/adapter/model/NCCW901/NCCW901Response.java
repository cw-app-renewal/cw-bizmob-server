package adapter.model.NCCW901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 음용량 등록
60일치 음용량 등록 전문(1회 최대 60건)
 */
public class NCCW901Response {
	/**
	 * header object
	 */
	private CowayCommonHeader header = null;
	/**
	 * body object
	 */
	private NCCW901Response_Body body = null;

	public NCCW901Response() {
	}

	public NCCW901Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new NCCW901Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public NCCW901Response_Body getBody() {
		return this.body;
	}

	public void setBody(NCCW901Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW901Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}