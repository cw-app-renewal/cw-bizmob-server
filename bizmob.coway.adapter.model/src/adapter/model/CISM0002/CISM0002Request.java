package adapter.model.CISM0002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.header.CowayCommonHeader;

/**
 * 스마트 진단결과 이력목록 페이징 조회
스마트진단결과 이력목록을 조회한다.
이력목록에는 해당 진단결과에 정상/이상감지 여부 포함
진단항목유형/항목/가이드 등은 포함하지 않는다.
최대 조회 기간: 2 주
최대 페이징수 : 20
이상감지 항목만 출력
 */
public class CISM0002Request {
	/**
	* header object
	*/
	private CowayCommonHeader header = null;
	/**
	* body object
	*/
	private CISM0002Request_Body body = null;

	public CISM0002Request() {
	}

	public CISM0002Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new CowayCommonHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new CISM0002Request_Body(bodyNode);
	}

	@JsonProperty("header")
	public CowayCommonHeader getHeader() {
		return this.header;
	}

	public void setHeader(CowayCommonHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public CISM0002Request_Body getBody() {
		return this.body;
	}

	public void setBody(CISM0002Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0002Request [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}