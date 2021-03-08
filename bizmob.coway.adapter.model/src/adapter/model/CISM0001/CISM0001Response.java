package adapter.model.CISM0001;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.header.CowayCommonHeader;

/**
 * i-Trust AccessToken 발급
Coway IoCare API 호출시 사용할Access Token 을 발급한다.
 */
public class CISM0001Response {
	/**
	* header object
	*/
	private CowayCommonHeader header = null;
	/**
	* body object
	*/
	private CISM0001Response_Body body = null;

	public CISM0001Response() {
	}

	public CISM0001Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CISM0001Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public CISM0001Response_Body getBody() {
		return this.body;
	}

	public void setBody(CISM0001Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0001Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}