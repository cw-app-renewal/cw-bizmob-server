package adapter.model.sample.RD044;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class RD044Request_Body {
	/**
	 * 조회년월
	 */
	private String I_STD_YM;
	/**
	 * 사용자번호
	 */
	private String I_UEMPL_NO;
	/**
	 * 전화번호
	 */
	private String I_INVNR;

	public RD044Request_Body() {
	}

	public RD044Request_Body(JsonNode jsonNode) {
		this.I_STD_YM = jsonNode.path("I_STD_YM").getTextValue();
		this.I_UEMPL_NO = jsonNode.path("I_UEMPL_NO").getTextValue();
		this.I_INVNR = jsonNode.path("I_INVNR").getTextValue();
	}

	public String getI_STD_YM() {
		return this.I_STD_YM;
	}

	public void setI_STD_YM(String I_STD_YM) {
		this.I_STD_YM = I_STD_YM;
	}

	public String getI_UEMPL_NO() {
		return this.I_UEMPL_NO;
	}

	public void setI_UEMPL_NO(String I_UEMPL_NO) {
		this.I_UEMPL_NO = I_UEMPL_NO;
	}

	public String getI_INVNR() {
		return this.I_INVNR;
	}

	public void setI_INVNR(String I_INVNR) {
		this.I_INVNR = I_INVNR;
	}
}