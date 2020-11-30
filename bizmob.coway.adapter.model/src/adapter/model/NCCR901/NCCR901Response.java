package adapter.model.NCCR901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 음용량 조회
1년치 음용량 조회
 */
public class NCCR901Response {
	/**
	 * header object
	 */
	private CowayCommonHeader header = null;
	/**
	 * body object
	 */
	private NCCR901Response_Body body = null;

	public NCCR901Response() {
	}

	public NCCR901Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new NCCR901Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public NCCR901Response_Body getBody() {
		return this.body;
	}

	public void setBody(NCCR901Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR901Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}