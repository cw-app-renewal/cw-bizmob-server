package adapter.model.CIS003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CIS003Request_Body {
	/**
	* 이력목록에 포함된 requestId
	*/
	private String requestId = "";
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";

	public CIS003Request_Body() {
	}

	public CIS003Request_Body(JsonNode jsonNode) {
		this.requestId = jsonNode.path("requestId").getTextValue();
		this.serial = jsonNode.path("serial").getTextValue();
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
		builder.append("CIS003Request_Body [");
		builder.append("requestId=").append(requestId);
		builder.append(", ");
		builder.append("serial=").append(serial);
		builder.append("]");
		return builder.toString();
	}
}