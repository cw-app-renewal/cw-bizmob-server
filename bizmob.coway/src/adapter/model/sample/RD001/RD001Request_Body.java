package adapter.model.sample.RD001;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class RD001Request_Body {
	/**
	 * PDA전화번호('-'삭제)
	 */
	private String I_INVNR;

	public RD001Request_Body() {
	}

	public RD001Request_Body(JsonNode jsonNode) {
		this.I_INVNR = jsonNode.path("I_INVNR").getTextValue();
	}

	public String getI_INVNR() {
		return this.I_INVNR;
	}

	public void setI_INVNR(String I_INVNR) {
		this.I_INVNR = I_INVNR;
	}
}