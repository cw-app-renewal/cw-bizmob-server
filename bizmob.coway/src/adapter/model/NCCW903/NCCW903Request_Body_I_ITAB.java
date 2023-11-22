package adapter.model.NCCW903;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 살균진행내역 리스트
 */
public class NCCW903Request_Body_I_ITAB {
	/**
	 * 설비 번호
	 */
	private String I_EQUNR = "";
	/**
	 * 파우셋살균 데이터 ( 0 or 1 )
	 */
	private String I_FAUCET = "";
	/**
	 * 고객주문번호
	 */
	private String I_ORDER_NO = "";
	/**
	 * 유로살균 데이터 ( 0 or 1 )
	 */
	private String I_FLOW = "";
	/**
	 * 순환살균 데이터 ( 0 or 1 )
	 */
	private String I_CYCLE = "";
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 데이터 날짜 ( 단말 )
	 */
	private String I_RAW_DATE = "";

	public NCCW903Request_Body_I_ITAB() {
	}

	public NCCW903Request_Body_I_ITAB(JsonNode jsonNode) {
		this.I_EQUNR = jsonNode.path("I_EQUNR").getTextValue();
		this.I_FAUCET = jsonNode.path("I_FAUCET").getTextValue();
		this.I_ORDER_NO = jsonNode.path("I_ORDER_NO").getTextValue();
		this.I_FLOW = jsonNode.path("I_FLOW").getTextValue();
		this.I_CYCLE = jsonNode.path("I_CYCLE").getTextValue();
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_RAW_DATE = jsonNode.path("I_RAW_DATE").getTextValue();
	}

	@JsonProperty("I_EQUNR")
	public String getI_EQUNR() {
		return this.I_EQUNR;
	}

	public void setI_EQUNR(String I_EQUNR) {
		this.I_EQUNR = I_EQUNR;
	}

	@JsonProperty("I_FAUCET")
	public String getI_FAUCET() {
		return this.I_FAUCET;
	}

	public void setI_FAUCET(String I_FAUCET) {
		this.I_FAUCET = I_FAUCET;
	}

	@JsonProperty("I_ORDER_NO")
	public String getI_ORDER_NO() {
		return this.I_ORDER_NO;
	}

	public void setI_ORDER_NO(String I_ORDER_NO) {
		this.I_ORDER_NO = I_ORDER_NO;
	}

	@JsonProperty("I_FLOW")
	public String getI_FLOW() {
		return this.I_FLOW;
	}

	public void setI_FLOW(String I_FLOW) {
		this.I_FLOW = I_FLOW;
	}

	@JsonProperty("I_CYCLE")
	public String getI_CYCLE() {
		return this.I_CYCLE;
	}

	public void setI_CYCLE(String I_CYCLE) {
		this.I_CYCLE = I_CYCLE;
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	@JsonProperty("I_RAW_DATE")
	public String getI_RAW_DATE() {
		return this.I_RAW_DATE;
	}

	public void setI_RAW_DATE(String I_RAW_DATE) {
		this.I_RAW_DATE = I_RAW_DATE;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW903Request_Body_I_ITAB [");
		builder.append("I_EQUNR=").append(I_EQUNR);
		builder.append(", ");
		builder.append("I_FAUCET=").append(I_FAUCET);
		builder.append(", ");
		builder.append("I_ORDER_NO=").append(I_ORDER_NO);
		builder.append(", ");
		builder.append("I_FLOW=").append(I_FLOW);
		builder.append(", ");
		builder.append("I_CYCLE=").append(I_CYCLE);
		builder.append(", ");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_RAW_DATE=").append(I_RAW_DATE);
		builder.append("]");
		return builder.toString();
	}
}