package adapter.model.NCCW901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 음용량 리스트
 */
public class NCCW901Request_Body_I_ITAB {
	/**
	 * 정수 ( 숫자 )
	 */
	private Integer I_NORMAL_INT = null;
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 고객주문번호
	 */
	private String I_ORDER_NO = "";
	/**
	 * 냉수 ( 숫자 )
	 */
	private Integer I_COLD_INT = null;
	/**
	 * 데이터 날짜 ( 단말 )
	 */
	private String I_RAW_DATE = "";
	/**
	 * 설비 번호
	 */
	private String I_EQUNR = "";
	/**
	 * 정수 ( 16진수 )
	 */
	private String I_NORMAL = "";
	/**
	 * 냉수 ( 16진수 )
	 */
	private String I_COLD = "";
	/**
	 * 온수 ( 16진수 )
	 */
	private String I_HOT = "";
	/**
	 * 온수 ( 숫자 )
	 */
	private Integer I_HOT_INT = null;

	public NCCW901Request_Body_I_ITAB() {
	}

	public NCCW901Request_Body_I_ITAB(JsonNode jsonNode) {
		this.I_NORMAL_INT = jsonNode.path("I_NORMAL_INT").getIntValue();
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_ORDER_NO = jsonNode.path("I_ORDER_NO").getTextValue();
		this.I_COLD_INT = jsonNode.path("I_COLD_INT").getIntValue();
		this.I_RAW_DATE = jsonNode.path("I_RAW_DATE").getTextValue();
		this.I_EQUNR = jsonNode.path("I_EQUNR").getTextValue();
		this.I_NORMAL = jsonNode.path("I_NORMAL").getTextValue();
		this.I_COLD = jsonNode.path("I_COLD").getTextValue();
		this.I_HOT = jsonNode.path("I_HOT").getTextValue();
		this.I_HOT_INT = jsonNode.path("I_HOT_INT").getIntValue();
	}

	@JsonProperty("I_NORMAL_INT")
	public Integer getI_NORMAL_INT() {
		return this.I_NORMAL_INT;
	}

	public void setI_NORMAL_INT(Integer I_NORMAL_INT) {
		this.I_NORMAL_INT = I_NORMAL_INT;
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

	@JsonProperty("I_COLD_INT")
	public Integer getI_COLD_INT() {
		return this.I_COLD_INT;
	}

	public void setI_COLD_INT(Integer I_COLD_INT) {
		this.I_COLD_INT = I_COLD_INT;
	}

	@JsonProperty("I_RAW_DATE")
	public String getI_RAW_DATE() {
		return this.I_RAW_DATE;
	}

	public void setI_RAW_DATE(String I_RAW_DATE) {
		this.I_RAW_DATE = I_RAW_DATE;
	}

	@JsonProperty("I_EQUNR")
	public String getI_EQUNR() {
		return this.I_EQUNR;
	}

	public void setI_EQUNR(String I_EQUNR) {
		this.I_EQUNR = I_EQUNR;
	}

	@JsonProperty("I_NORMAL")
	public String getI_NORMAL() {
		return this.I_NORMAL;
	}

	public void setI_NORMAL(String I_NORMAL) {
		this.I_NORMAL = I_NORMAL;
	}

	@JsonProperty("I_COLD")
	public String getI_COLD() {
		return this.I_COLD;
	}

	public void setI_COLD(String I_COLD) {
		this.I_COLD = I_COLD;
	}

	@JsonProperty("I_HOT")
	public String getI_HOT() {
		return this.I_HOT;
	}

	public void setI_HOT(String I_HOT) {
		this.I_HOT = I_HOT;
	}

	@JsonProperty("I_HOT_INT")
	public Integer getI_HOT_INT() {
		return this.I_HOT_INT;
	}

	public void setI_HOT_INT(Integer I_HOT_INT) {
		this.I_HOT_INT = I_HOT_INT;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW901Request_Body_I_ITAB [");
		builder.append("I_NORMAL_INT=").append(I_NORMAL_INT);
		builder.append(", ");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_ORDER_NO=").append(I_ORDER_NO);
		builder.append(", ");
		builder.append("I_COLD_INT=").append(I_COLD_INT);
		builder.append(", ");
		builder.append("I_RAW_DATE=").append(I_RAW_DATE);
		builder.append(", ");
		builder.append("I_EQUNR=").append(I_EQUNR);
		builder.append(", ");
		builder.append("I_NORMAL=").append(I_NORMAL);
		builder.append(", ");
		builder.append("I_COLD=").append(I_COLD);
		builder.append(", ");
		builder.append("I_HOT=").append(I_HOT);
		builder.append(", ");
		builder.append("I_HOT_INT=").append(I_HOT_INT);
		builder.append("]");
		return builder.toString();
	}
}