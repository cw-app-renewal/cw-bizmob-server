package adapter.model.CISM0002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0002Request_Body {
	/**
	* 최초 페이지 조회시 0 다음 페이징 여부 및 페이징키 - lastEvaluatedKey == null 이면 다음 페이지 없음. - lastEvaluatedKey != null 이면 하위 creationDt 를 Request Parameter 로 전달
	*/
	private String creationDt = "";
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";
	/**
	* i-Trust access token
	*/
	private String accessToken = "";

	public CISM0002Request_Body() {
	}

	public CISM0002Request_Body(JsonNode jsonNode) {
		this.creationDt = jsonNode.path("creationDt").getTextValue();
		this.serial = jsonNode.path("serial").getTextValue();
		this.accessToken = jsonNode.path("accessToken").getTextValue();
	}

	@JsonProperty("creationDt")
	public String getCreationDt() {
		return this.creationDt;
	}

	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}

	@JsonProperty("serial")
	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@JsonProperty("accessToken")
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0002Request_Body [");
		builder.append("creationDt=").append(creationDt);
		builder.append(", ");
		builder.append("serial=").append(serial);
		builder.append(", ");
		builder.append("accessToken=").append(accessToken);
		builder.append("]");
		return builder.toString();
	}
}