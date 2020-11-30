package adapter.model.NCCR901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 월음용량
 */
public class NCCR901Response_Body_O_ITAB {
	/**
	 * 월 정수 합(숫자)
	 */
	private Integer O_SUM_NORMAL_INT = null;
	/**
	 * 월 냉수 합(숫자)
	 */
	private Integer O_SUM_COLD_INT = null;
	/**
	 * 월 온수 합(숫자)
	 */
	private Integer O_SUM_HOT_INT = null;
	/**
	 * 음용월
	 */
	private String O_MONTH = "";

	public NCCR901Response_Body_O_ITAB() {
	}

	public NCCR901Response_Body_O_ITAB(JsonNode jsonNode) {
		this.O_SUM_NORMAL_INT = jsonNode.path("O_SUM_NORMAL_INT").getIntValue();
		this.O_SUM_COLD_INT = jsonNode.path("O_SUM_COLD_INT").getIntValue();
		this.O_SUM_HOT_INT = jsonNode.path("O_SUM_HOT_INT").getIntValue();
		this.O_MONTH = jsonNode.path("O_MONTH").getTextValue();
	}

	@JsonProperty("O_SUM_NORMAL_INT")
	public Integer getO_SUM_NORMAL_INT() {
		return this.O_SUM_NORMAL_INT;
	}

	public void setO_SUM_NORMAL_INT(Integer O_SUM_NORMAL_INT) {
		this.O_SUM_NORMAL_INT = O_SUM_NORMAL_INT;
	}

	@JsonProperty("O_SUM_COLD_INT")
	public Integer getO_SUM_COLD_INT() {
		return this.O_SUM_COLD_INT;
	}

	public void setO_SUM_COLD_INT(Integer O_SUM_COLD_INT) {
		this.O_SUM_COLD_INT = O_SUM_COLD_INT;
	}

	@JsonProperty("O_SUM_HOT_INT")
	public Integer getO_SUM_HOT_INT() {
		return this.O_SUM_HOT_INT;
	}

	public void setO_SUM_HOT_INT(Integer O_SUM_HOT_INT) {
		this.O_SUM_HOT_INT = O_SUM_HOT_INT;
	}

	@JsonProperty("O_MONTH")
	public String getO_MONTH() {
		return this.O_MONTH;
	}

	public void setO_MONTH(String O_MONTH) {
		this.O_MONTH = O_MONTH;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR901Response_Body_O_ITAB [");
		builder.append("O_SUM_NORMAL_INT=").append(O_SUM_NORMAL_INT);
		builder.append(", ");
		builder.append("O_SUM_COLD_INT=").append(O_SUM_COLD_INT);
		builder.append(", ");
		builder.append("O_SUM_HOT_INT=").append(O_SUM_HOT_INT);
		builder.append(", ");
		builder.append("O_MONTH=").append(O_MONTH);
		builder.append("]");
		return builder.toString();
	}
}