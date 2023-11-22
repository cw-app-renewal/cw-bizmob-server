package adapter.model.CDR000;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDR000Request_Body {
	/**
	 * 비밀번호
	 */
	private String I_ATWRT;
	/**
	 * 사용자 번호
	 */
	private String I_PERNR;

	public CDR000Request_Body() {
	}

	public CDR000Request_Body(JsonNode jsonNode) {
		this.I_ATWRT = jsonNode.path("I_ATWRT").getTextValue();
		this.I_PERNR = jsonNode.path("I_PERNR").getTextValue();
	}

	public String getI_ATWRT() {
		return this.I_ATWRT;
	}

	public void setI_ATWRT(String I_ATWRT) {
		this.I_ATWRT = I_ATWRT;
	}

	public String getI_PERNR() {
		return this.I_PERNR;
	}

	public void setI_PERNR(String I_PERNR) {
		this.I_PERNR = I_PERNR;
	}
}