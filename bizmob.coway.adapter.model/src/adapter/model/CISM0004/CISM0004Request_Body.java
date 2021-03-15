package adapter.model.CISM0004;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0004Request_Body {
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";
	/**
	* i-Trust access token
	*/
	private String accessToken = "";

	public CISM0004Request_Body() {
	}

	public CISM0004Request_Body(JsonNode jsonNode) {
		this.serial = jsonNode.path("serial").getTextValue();
		this.accessToken = jsonNode.path("accessToken").getTextValue();
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
		builder.append("CISM0004Request_Body [");
		builder.append("serial=").append(serial);
		builder.append(", ");
		builder.append("accessToken=").append(accessToken);
		builder.append("]");
		return builder.toString();
	}
}