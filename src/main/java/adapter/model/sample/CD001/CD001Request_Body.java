package adapter.model.sample.CD001;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CD001Request_Body {
	/**
	 * 작업상태(A:전체)
	 */
	private String I_STATUS;
	/**
	 * 사용자번호
	 */
	private String I_UEMPL_NO;

	public CD001Request_Body() {
	}

	public CD001Request_Body(JsonNode jsonNode) {
		this.I_STATUS = jsonNode.path("I_STATUS").getTextValue();
		this.I_UEMPL_NO = jsonNode.path("I_UEMPL_NO").getTextValue();
	}

	public String getI_STATUS() {
		return this.I_STATUS;
	}

	public void setI_STATUS(String I_STATUS) {
		this.I_STATUS = I_STATUS;
	}

	public String getI_UEMPL_NO() {
		return this.I_UEMPL_NO;
	}

	public void setI_UEMPL_NO(String I_UEMPL_NO) {
		this.I_UEMPL_NO = I_UEMPL_NO;
	}
}