package adapter.model.CISM0001;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0001Request_Body {
	/**
	* OAuth Grant Type (client_credentials 값으로 고정)
	*/
	private String grantType = "";

	public CISM0001Request_Body() {
	}

	public CISM0001Request_Body(JsonNode jsonNode) {
		this.grantType = jsonNode.path("grantType").getTextValue();
	}

	@JsonProperty("grantType")
	public String getGrantType() {
		return this.grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0001Request_Body [");
		builder.append("grantType=").append(grantType);
		builder.append("]");
		return builder.toString();
	}
}