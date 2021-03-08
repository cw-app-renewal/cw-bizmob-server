package adapter.model.CISM0001;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0001Response_Body {
	/**
	* 만료기간 1h
	*/
	private int expiresIn = 0;
	/**
	* scope
	*/
	private String scope = "";
	/**
	* jti
	*/
	private String jti = "";
	/**
	* CowayIoCare API 호출시 사용할 AccessToken
	*/
	private String accessToken = "";
	/**
	* token type
	*/
	private String tokenType = "";

	public CISM0001Response_Body() {
	}

	public CISM0001Response_Body(JsonNode jsonNode) {
		this.expiresIn = jsonNode.path("expiresIn").getIntValue();
		this.scope = jsonNode.path("scope").getTextValue();
		this.jti = jsonNode.path("jti").getTextValue();
		this.accessToken = jsonNode.path("accessToken").getTextValue();
		this.tokenType = jsonNode.path("tokenType").getTextValue();
	}

	@JsonProperty("expiresIn")
	public int getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@JsonProperty("scope")
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonProperty("jti")
	public String getJti() {
		return this.jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	@JsonProperty("accessToken")
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("tokenType")
	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0001Response_Body [");
		builder.append("expiresIn=").append(expiresIn);
		builder.append(", ");
		builder.append("scope=").append(scope);
		builder.append(", ");
		builder.append("jti=").append(jti);
		builder.append(", ");
		builder.append("accessToken=").append(accessToken);
		builder.append(", ");
		builder.append("tokenType=").append(tokenType);
		builder.append("]");
		return builder.toString();
	}
}