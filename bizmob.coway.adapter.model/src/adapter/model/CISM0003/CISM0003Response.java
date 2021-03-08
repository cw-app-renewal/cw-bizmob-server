package adapter.model.CISM0003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.header.CowayCommonHeader;

/**
 * 스마트 진단결과 상세 조회
스마트진단결과 상세정보를 조회한다.
 * 진단이력 / 진단항목유형 / 진단항목 / 가이드 정보를 포함한다.
 * 이상감지 항목만 출력
 */
public class CISM0003Response {
	/**
	* header object
	*/
	private CowayCommonHeader header = null;
	/**
	* body object
	*/
	private CISM0003Response_Body body = null;

	public CISM0003Response() {
	}

	public CISM0003Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CISM0003Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public CISM0003Response_Body getBody() {
		return this.body;
	}

	public void setBody(CISM0003Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0003Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}