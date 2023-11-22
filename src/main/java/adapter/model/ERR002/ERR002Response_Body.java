package adapter.model.ERR002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class ERR002Response_Body {
	/**
	 * 요청 목록 갯수
	 */
	private String crushListCnt = "";
	/**
	 * insert된 목록 갯수
	 */
	private String insertListCnt = "";

	public ERR002Response_Body() {
	}

	public ERR002Response_Body(JsonNode jsonNode) {
		this.crushListCnt = jsonNode.path("crushListCnt").getTextValue();
		this.insertListCnt = jsonNode.path("insertListCnt").getTextValue();
	}

	@JsonProperty("crushListCnt")
	public String getCrushListCnt() {
		return this.crushListCnt;
	}

	public void setCrushListCnt(String crushListCnt) {
		this.crushListCnt = crushListCnt;
	}

	@JsonProperty("insertListCnt")
	public String getInsertListCnt() {
		return this.insertListCnt;
	}

	public void setInsertListCnt(String insertListCnt) {
		this.insertListCnt = insertListCnt;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ERR002Response_Body [");
		builder.append("crushListCnt=").append(crushListCnt);
		builder.append(", ");
		builder.append("insertListCnt=").append(insertListCnt);
		builder.append("]");
		return builder.toString();
	}
}