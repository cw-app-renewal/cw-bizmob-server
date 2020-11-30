package adapter.model.NCCR902;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCR902Request_Body {
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 고객주문번호
	 */
	private String I_ORDER_NO = "";

	public NCCR902Request_Body() {
	}

	public NCCR902Request_Body(JsonNode jsonNode) {
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_ORDER_NO = jsonNode.path("I_ORDER_NO").getTextValue();
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	@JsonProperty("I_ORDER_NO")
	public String getI_ORDER_NO() {
		return this.I_ORDER_NO;
	}

	public void setI_ORDER_NO(String I_ORDER_NO) {
		this.I_ORDER_NO = I_ORDER_NO;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR902Request_Body [");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_ORDER_NO=").append(I_ORDER_NO);
		builder.append("]");
		return builder.toString();
	}
}