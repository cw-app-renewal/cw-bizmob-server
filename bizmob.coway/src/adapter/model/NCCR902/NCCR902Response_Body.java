package adapter.model.NCCR902;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCR902Response_Body {
	/**
	 * 살균주기 자동모드 ( 00 : 수동, 01 : 자동 )
	 */
	private String O_CYCLE_MODE = "";
	/**
	 * 전송타입( 0 : normal, 1 ; 미전송처리 )
	 */
	private String O_POST_TYPE = "";
	/**
	 * 살균 예약시간 ( 0 ~ 23 )
	 */
	private String O_RSVN_HOUR = "";
	/**
	 * 살균 예약분 ( 0, 30 )
	 */
	private String O_RSVN_MINUTE = "";
	/**
	 * 데이터 생성 날짜(단말)
	 */
	private String O_CREATED_DATE = "";
	/**
	 * 살균주기 ( 03 or 05 )
	 */
	private String O_CYCLE_DATE = "";
	/**
	 * 제품바코드
	 */
	private String O_GOODS_SN = "";
	/**
	 * 고객주문번호
	 */
	private String O_ORDER_NO = "";

	public NCCR902Response_Body() {
	}

	public NCCR902Response_Body(JsonNode jsonNode) {
		this.O_CYCLE_MODE = jsonNode.path("O_CYCLE_MODE").getTextValue();
		this.O_POST_TYPE = jsonNode.path("O_POST_TYPE").getTextValue();
		this.O_RSVN_HOUR = jsonNode.path("O_RSVN_HOUR").getTextValue();
		this.O_RSVN_MINUTE = jsonNode.path("O_RSVN_MINUTE").getTextValue();
		this.O_CREATED_DATE = jsonNode.path("O_CREATED_DATE").getTextValue();
		this.O_CYCLE_DATE = jsonNode.path("O_CYCLE_DATE").getTextValue();
		this.O_GOODS_SN = jsonNode.path("O_GOODS_SN").getTextValue();
		this.O_ORDER_NO = jsonNode.path("O_ORDER_NO").getTextValue();
	}

	@JsonProperty("O_CYCLE_MODE")
	public String getO_CYCLE_MODE() {
		return this.O_CYCLE_MODE;
	}

	public void setO_CYCLE_MODE(String O_CYCLE_MODE) {
		this.O_CYCLE_MODE = O_CYCLE_MODE;
	}

	@JsonProperty("O_POST_TYPE")
	public String getO_POST_TYPE() {
		return this.O_POST_TYPE;
	}

	public void setO_POST_TYPE(String O_POST_TYPE) {
		this.O_POST_TYPE = O_POST_TYPE;
	}

	@JsonProperty("O_RSVN_HOUR")
	public String getO_RSVN_HOUR() {
		return this.O_RSVN_HOUR;
	}

	public void setO_RSVN_HOUR(String O_RSVN_HOUR) {
		this.O_RSVN_HOUR = O_RSVN_HOUR;
	}

	@JsonProperty("O_RSVN_MINUTE")
	public String getO_RSVN_MINUTE() {
		return this.O_RSVN_MINUTE;
	}

	public void setO_RSVN_MINUTE(String O_RSVN_MINUTE) {
		this.O_RSVN_MINUTE = O_RSVN_MINUTE;
	}

	@JsonProperty("O_CREATED_DATE")
	public String getO_CREATED_DATE() {
		return this.O_CREATED_DATE;
	}

	public void setO_CREATED_DATE(String O_CREATED_DATE) {
		this.O_CREATED_DATE = O_CREATED_DATE;
	}

	@JsonProperty("O_CYCLE_DATE")
	public String getO_CYCLE_DATE() {
		return this.O_CYCLE_DATE;
	}

	public void setO_CYCLE_DATE(String O_CYCLE_DATE) {
		this.O_CYCLE_DATE = O_CYCLE_DATE;
	}

	@JsonProperty("O_GOODS_SN")
	public String getO_GOODS_SN() {
		return this.O_GOODS_SN;
	}

	public void setO_GOODS_SN(String O_GOODS_SN) {
		this.O_GOODS_SN = O_GOODS_SN;
	}

	@JsonProperty("O_ORDER_NO")
	public String getO_ORDER_NO() {
		return this.O_ORDER_NO;
	}

	public void setO_ORDER_NO(String O_ORDER_NO) {
		this.O_ORDER_NO = O_ORDER_NO;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR902Response_Body [");
		builder.append("O_CYCLE_MODE=").append(O_CYCLE_MODE);
		builder.append(", ");
		builder.append("O_POST_TYPE=").append(O_POST_TYPE);
		builder.append(", ");
		builder.append("O_RSVN_HOUR=").append(O_RSVN_HOUR);
		builder.append(", ");
		builder.append("O_RSVN_MINUTE=").append(O_RSVN_MINUTE);
		builder.append(", ");
		builder.append("O_CREATED_DATE=").append(O_CREATED_DATE);
		builder.append(", ");
		builder.append("O_CYCLE_DATE=").append(O_CYCLE_DATE);
		builder.append(", ");
		builder.append("O_GOODS_SN=").append(O_GOODS_SN);
		builder.append(", ");
		builder.append("O_ORDER_NO=").append(O_ORDER_NO);
		builder.append("]");
		return builder.toString();
	}
}