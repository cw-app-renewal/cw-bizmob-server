package adapter.model.NCCW904;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 오류내용 리스트
 */
public class NCCW904Request_Body_I_ITAB {
	/**
	 * 고객주문번호
	 */
	private String I_ORDER_NO = "";
	/**
	 * 에러발생일자 ( YYYYMMDD )
	 */
	private String I_ERROR_DATE = "";
	/**
	 * 제품바코드
	 */
	private String I_GOODS_SN = "";
	/**
	 * 에러코드 ( 16진수 )
	 */
	private String I_E_CODE = "";
	/**
	 * 설비번호, 설비단위
	 */
	private String I_EQUNR = "";
	/**
	 * 등록자
	 */
	private String I_USER_ID = "";

	public NCCW904Request_Body_I_ITAB() {
	}

	public NCCW904Request_Body_I_ITAB(JsonNode jsonNode) {
		this.I_ORDER_NO = jsonNode.path("I_ORDER_NO").getTextValue();
		this.I_ERROR_DATE = jsonNode.path("I_ERROR_DATE").getTextValue();
		this.I_GOODS_SN = jsonNode.path("I_GOODS_SN").getTextValue();
		this.I_E_CODE = jsonNode.path("I_E_CODE").getTextValue();
		this.I_EQUNR = jsonNode.path("I_EQUNR").getTextValue();
		this.I_USER_ID = jsonNode.path("I_USER_ID").getTextValue();
	}

	@JsonProperty("I_ORDER_NO")
	public String getI_ORDER_NO() {
		return this.I_ORDER_NO;
	}

	public void setI_ORDER_NO(String I_ORDER_NO) {
		this.I_ORDER_NO = I_ORDER_NO;
	}

	@JsonProperty("I_ERROR_DATE")
	public String getI_ERROR_DATE() {
		return this.I_ERROR_DATE;
	}

	public void setI_ERROR_DATE(String I_ERROR_DATE) {
		this.I_ERROR_DATE = I_ERROR_DATE;
	}

	@JsonProperty("I_GOODS_SN")
	public String getI_GOODS_SN() {
		return this.I_GOODS_SN;
	}

	public void setI_GOODS_SN(String I_GOODS_SN) {
		this.I_GOODS_SN = I_GOODS_SN;
	}

	@JsonProperty("I_E_CODE")
	public String getI_E_CODE() {
		return this.I_E_CODE;
	}

	public void setI_E_CODE(String I_E_CODE) {
		this.I_E_CODE = I_E_CODE;
	}

	@JsonProperty("I_EQUNR")
	public String getI_EQUNR() {
		return this.I_EQUNR;
	}

	public void setI_EQUNR(String I_EQUNR) {
		this.I_EQUNR = I_EQUNR;
	}

	@JsonProperty("I_USER_ID")
	public String getI_USER_ID() {
		return this.I_USER_ID;
	}

	public void setI_USER_ID(String I_USER_ID) {
		this.I_USER_ID = I_USER_ID;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW904Request_Body_I_ITAB [");
		builder.append("I_ORDER_NO=").append(I_ORDER_NO);
		builder.append(", ");
		builder.append("I_ERROR_DATE=").append(I_ERROR_DATE);
		builder.append(", ");
		builder.append("I_GOODS_SN=").append(I_GOODS_SN);
		builder.append(", ");
		builder.append("I_E_CODE=").append(I_E_CODE);
		builder.append(", ");
		builder.append("I_EQUNR=").append(I_EQUNR);
		builder.append(", ");
		builder.append("I_USER_ID=").append(I_USER_ID);
		builder.append("]");
		return builder.toString();
	}
}