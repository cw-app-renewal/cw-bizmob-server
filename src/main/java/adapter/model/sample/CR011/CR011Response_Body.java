package adapter.model.sample.CR011;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CR011Response_Body {
	/**
	 * 시작일
	 */
	private String O_START_DT;

	public CR011Response_Body() {
	}

	public CR011Response_Body(JsonNode jsonNode) {
		this.O_START_DT = jsonNode.path("O_START_DT").getTextValue();
	}

	public String getO_START_DT() {
		return this.O_START_DT;
	}

	public void setO_START_DT(String O_START_DT) {
		this.O_START_DT = O_START_DT;
	}
}