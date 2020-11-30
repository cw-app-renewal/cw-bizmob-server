package adapter.model.NCCR902;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 살균순환 정보 조회
살균순환 정보 조회
 */
public class NCCR902Request {
	/**
	 * header object
	 */
	private CowayCommonHeader header = null;
	/**
	 * body object
	 */
	private NCCR902Request_Body body = null;

	public NCCR902Request() {
	}

	public NCCR902Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new NCCR902Request_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public NCCR902Request_Body getBody() {
		return this.body;
	}

	public void setBody(NCCR902Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR902Request [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}