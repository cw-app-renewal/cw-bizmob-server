package adapter.model.CISM0003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0003Request_Body {
	/**
	* 이력목록에 포함된 requestId
	*/
	private String requestId = "";
	/**
	* i-Trust access token
	*/
	private String accessToken = "";
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";

	public CISM0003Request_Body() {
	}

	public CISM0003Request_Body(JsonNode jsonNode) {
		this.requestId = jsonNode.path("requestId").getTextValue();
		this.accessToken = jsonNode.path("accessToken").getTextValue();
		this.serial = jsonNode.path("serial").getTextValue();
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@JsonProperty("accessToken")
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("serial")
	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0003Request_Body [");
		builder.append("requestId=").append(requestId);
		builder.append(", ");
		builder.append("accessToken=").append(accessToken);
		builder.append(", ");
		builder.append("serial=").append(serial);
		builder.append("]");
		return builder.toString();
	}
}