package adapter.model.NCCW904;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.CowayCommonHeader;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 오류내용 저장
오류내용 저장
 */
public class NCCW904Response {
	/**
	 * header object
	 */
	private CowayCommonHeader header = null;
	/**
	 * body object
	 */
	private NCCW904Response_Body body = null;

	public NCCW904Response() {
	}

	public NCCW904Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new NCCW904Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public NCCW904Response_Body getBody() {
		return this.body;
	}

	public void setBody(NCCW904Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW904Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}