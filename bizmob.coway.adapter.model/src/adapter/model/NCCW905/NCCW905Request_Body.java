package adapter.model.NCCW905;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCW905Request_Body {
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 호출 형태
	 */
	private String I_TYPE = "";

	public NCCW905Request_Body() {
	}

	public NCCW905Request_Body(JsonNode jsonNode) {
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_TYPE = jsonNode.path("I_TYPE").getTextValue();
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	@JsonProperty("I_TYPE")
	public String getI_TYPE() {
		return this.I_TYPE;
	}

	public void setI_TYPE(String I_TYPE) {
		this.I_TYPE = I_TYPE;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW905Request_Body [");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_TYPE=").append(I_TYPE);
		builder.append("]");
		return builder.toString();
	}
}