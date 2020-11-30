package adapter.model.sample.CR011;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CR011Request_Body {
	/**
	 * 설문일자
	 */
	private String I_SRCH_DT;
	/**
	 * 회사구분(코디:2000, 닥터:2100)
	 */
	private String I_COM_CD;
	/**
	 * pda전화번호('-' 삭제)
	 */
	private String I_INVNR;
	/**
	 * 사용자번호
	 */
	private String I_UEMPL_NO;

	public CR011Request_Body() {
	}

	public CR011Request_Body(JsonNode jsonNode) {
		this.I_SRCH_DT = jsonNode.path("I_SRCH_DT").getTextValue();
		this.I_COM_CD = jsonNode.path("I_COM_CD").getTextValue();
		this.I_INVNR = jsonNode.path("I_INVNR").getTextValue();
		this.I_UEMPL_NO = jsonNode.path("I_UEMPL_NO").getTextValue();
	}

	public String getI_SRCH_DT() {
		return this.I_SRCH_DT;
	}

	public void setI_SRCH_DT(String I_SRCH_DT) {
		this.I_SRCH_DT = I_SRCH_DT;
	}

	public String getI_COM_CD() {
		return this.I_COM_CD;
	}

	public void setI_COM_CD(String I_COM_CD) {
		this.I_COM_CD = I_COM_CD;
	}

	public String getI_INVNR() {
		return this.I_INVNR;
	}

	public void setI_INVNR(String I_INVNR) {
		this.I_INVNR = I_INVNR;
	}

	public String getI_UEMPL_NO() {
		return this.I_UEMPL_NO;
	}

	public void setI_UEMPL_NO(String I_UEMPL_NO) {
		this.I_UEMPL_NO = I_UEMPL_NO;
	}
}