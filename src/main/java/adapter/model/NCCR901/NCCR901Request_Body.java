package adapter.model.NCCR901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCR901Request_Body {
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";

	public NCCR901Request_Body() {
	}

	public NCCR901Request_Body(JsonNode jsonNode) {
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR901Request_Body [");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append("]");
		return builder.toString();
	}
}