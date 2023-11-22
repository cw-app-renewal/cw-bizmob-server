package adapter.model.CIS002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 다음 페이징 여부 및 페이징키 - lastEvaluatedKey == null 이면 다음 페이지 없음. - lastEvaluatedKey != null 이면 하위 creationDt 를 Request Parameter 로 전달
 */
public class CIS002Response_Body_lastEvaluatedKey {
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";
	/**
	* UTC Timestamp 값
	*/
	private String creationDt = "";

	public CIS002Response_Body_lastEvaluatedKey() {
	}

	public CIS002Response_Body_lastEvaluatedKey(JsonNode jsonNode) {
		this.serial = jsonNode.path("serial").getTextValue();
		this.creationDt = jsonNode.path("creationDt").getTextValue();
	}

	@JsonProperty("serial")
	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@JsonProperty("creationDt")
	public String getCreationDt() {
		return this.creationDt;
	}

	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS002Response_Body_lastEvaluatedKey [");
		builder.append("serial=").append(serial);
		builder.append(", ");
		builder.append("creationDt=").append(creationDt);
		builder.append("]");
		return builder.toString();
	}
}