package adapter.model.CISM0004;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.header.CowayCommonHeader;

/**
 * IoCare 제품 여부 조회
i.Trust 에 등록된 IoT 기기 정보전달
 * - *SAP 연동 없음.
 * - IoT개발팀 협의없이 출시된 제품 및 NFC 제품 제외.
 */
public class CISM0004Request {
	/**
	* header object
	*/
	private CowayCommonHeader header = null;
	/**
	* body object
	*/
	private CISM0004Request_Body body = null;

	public CISM0004Request() {
	}

	public CISM0004Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CISM0004Request_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public CISM0004Request_Body getBody() {
		return this.body;
	}

	public void setBody(CISM0004Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0004Request [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}