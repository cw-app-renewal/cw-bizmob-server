package adapter.model.NCCW903;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCW903Response_Body {
	/**
	 * 등록 결과
	 */
	private Boolean RESULT = null;
	/**
	 * 에러 메세지
	 */
	private String ERR_MSG = "";

	public NCCW903Response_Body() {
	}

	public NCCW903Response_Body(JsonNode jsonNode) {
		this.RESULT = jsonNode.path("RESULT").getBooleanValue();
		this.ERR_MSG = jsonNode.path("ERR_MSG").getTextValue();
	}

	@JsonProperty("RESULT")
	public Boolean getRESULT() {
		return this.RESULT;
	}

	public void setRESULT(Boolean RESULT) {
		this.RESULT = RESULT;
	}

	@JsonProperty("ERR_MSG")
	public String getERR_MSG() {
		return this.ERR_MSG;
	}

	public void setERR_MSG(String ERR_MSG) {
		this.ERR_MSG = ERR_MSG;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW903Response_Body [");
		builder.append("RESULT=").append(RESULT);
		builder.append(", ");
		builder.append("ERR_MSG=").append(ERR_MSG);
		builder.append("]");
		return builder.toString();
	}
}