package adapter.model.NCCW902;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCW902Request_Body {
	/**
	 * 고객주문번호
	 */
	private String I_ORDER_NO = "";
	/**
	 * 살균 예약시간( 0~23 )
	 */
	private String I_RSVN_HOUR = "";
	/**
	 * 살균주기 자동모드( 00 : 수동, 01 : 자동 )
	 */
	private String I_CYCLE_MODE = "";
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 데이터 생성 날짜 ( 단말 )
	 */
	private String I_CREATED_DATE = "";
	/**
	 * 살균 예약분( 0, 30 )
	 */
	private String I_RSVN_MINUTE = "";
	/**
	 * 살균주기 ( 03 OR 05 )
	 */
	private String I_CYCLE_DATE = "";
	/**
	 * 전송 타입( 0 : Normal, 1 : 미전송처리 )
	 */
	private String I_POST_TYPE = "";

	public NCCW902Request_Body() {
	}

	public NCCW902Request_Body(JsonNode jsonNode) {
		this.I_ORDER_NO = jsonNode.path("I_ORDER_NO").getTextValue();
		this.I_RSVN_HOUR = jsonNode.path("I_RSVN_HOUR").getTextValue();
		this.I_CYCLE_MODE = jsonNode.path("I_CYCLE_MODE").getTextValue();
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_CREATED_DATE = jsonNode.path("I_CREATED_DATE").getTextValue();
		this.I_RSVN_MINUTE = jsonNode.path("I_RSVN_MINUTE").getTextValue();
		this.I_CYCLE_DATE = jsonNode.path("I_CYCLE_DATE").getTextValue();
		this.I_POST_TYPE = jsonNode.path("I_POST_TYPE").getTextValue();
	}

	@JsonProperty("I_ORDER_NO")
	public String getI_ORDER_NO() {
		return this.I_ORDER_NO;
	}

	public void setI_ORDER_NO(String I_ORDER_NO) {
		this.I_ORDER_NO = I_ORDER_NO;
	}

	@JsonProperty("I_RSVN_HOUR")
	public String getI_RSVN_HOUR() {
		return this.I_RSVN_HOUR;
	}

	public void setI_RSVN_HOUR(String I_RSVN_HOUR) {
		this.I_RSVN_HOUR = I_RSVN_HOUR;
	}

	@JsonProperty("I_CYCLE_MODE")
	public String getI_CYCLE_MODE() {
		return this.I_CYCLE_MODE;
	}

	public void setI_CYCLE_MODE(String I_CYCLE_MODE) {
		this.I_CYCLE_MODE = I_CYCLE_MODE;
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	@JsonProperty("I_CREATED_DATE")
	public String getI_CREATED_DATE() {
		return this.I_CREATED_DATE;
	}

	public void setI_CREATED_DATE(String I_CREATED_DATE) {
		this.I_CREATED_DATE = I_CREATED_DATE;
	}

	@JsonProperty("I_RSVN_MINUTE")
	public String getI_RSVN_MINUTE() {
		return this.I_RSVN_MINUTE;
	}

	public void setI_RSVN_MINUTE(String I_RSVN_MINUTE) {
		this.I_RSVN_MINUTE = I_RSVN_MINUTE;
	}

	@JsonProperty("I_CYCLE_DATE")
	public String getI_CYCLE_DATE() {
		return this.I_CYCLE_DATE;
	}

	public void setI_CYCLE_DATE(String I_CYCLE_DATE) {
		this.I_CYCLE_DATE = I_CYCLE_DATE;
	}

	@JsonProperty("I_POST_TYPE")
	public String getI_POST_TYPE() {
		return this.I_POST_TYPE;
	}

	public void setI_POST_TYPE(String I_POST_TYPE) {
		this.I_POST_TYPE = I_POST_TYPE;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW902Request_Body [");
		builder.append("I_ORDER_NO=").append(I_ORDER_NO);
		builder.append(", ");
		builder.append("I_RSVN_HOUR=").append(I_RSVN_HOUR);
		builder.append(", ");
		builder.append("I_CYCLE_MODE=").append(I_CYCLE_MODE);
		builder.append(", ");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_CREATED_DATE=").append(I_CREATED_DATE);
		builder.append(", ");
		builder.append("I_RSVN_MINUTE=").append(I_RSVN_MINUTE);
		builder.append(", ");
		builder.append("I_CYCLE_DATE=").append(I_CYCLE_DATE);
		builder.append(", ");
		builder.append("I_POST_TYPE=").append(I_POST_TYPE);
		builder.append("]");
		return builder.toString();
	}
}