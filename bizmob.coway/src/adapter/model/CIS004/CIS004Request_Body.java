package adapter.model.CIS004;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CIS004Request_Body {
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";

	public CIS004Request_Body() {
	}

	public CIS004Request_Body(JsonNode jsonNode) {
		this.serial = jsonNode.path("serial").getTextValue();
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
		builder.append("CIS004Request_Body [");
		builder.append("serial=").append(serial);
		builder.append("]");
		return builder.toString();
	}
}