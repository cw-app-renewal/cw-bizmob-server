package adapter.model.sample.CR003;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CR003Request_Body {
	/**
	 * 사용자번호
	 */
	private String I_UEMPL_NO;

	public CR003Request_Body() {
	}

	public CR003Request_Body(JsonNode jsonNode) {
		this.I_UEMPL_NO = jsonNode.path("I_UEMPL_NO").getTextValue();
	}

	public String getI_UEMPL_NO() {
		return this.I_UEMPL_NO;
	}

	public void setI_UEMPL_NO(String I_UEMPL_NO) {
		this.I_UEMPL_NO = I_UEMPL_NO;
	}
}