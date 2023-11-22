package adapter.model.sample.RD044;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class RD044Response_Body_O_ITAB {
	/**
	 * JOB_TP
	 */
	private String JOB_TP;
	/**
	 * CUST_NM
	 */
	private String CUST_NM;
	/**
	 * GOODS_NM
	 */
	private String GOODS_NM;
	/**
	 * JOB_DT
	 */
	private String JOB_DT;
	/**
	 * JOB_TP_NM
	 */
	private String JOB_TP_NM;
	/**
	 * ORDER_NO
	 */
	private String ORDER_NO;
	/**
	 * RECP_AMT
	 */
	private String RECP_AMT;
	/**
	 * JOB_SEQ
	 */
	private String JOB_SEQ;
	/**
	 * TOT_AMT
	 */
	private String TOT_AMT;

	public RD044Response_Body_O_ITAB() {
	}

	public RD044Response_Body_O_ITAB(JsonNode jsonNode) {
		this.JOB_TP = jsonNode.path("JOB_TP").getTextValue();
		this.CUST_NM = jsonNode.path("CUST_NM").getTextValue();
		this.GOODS_NM = jsonNode.path("GOODS_NM").getTextValue();
		this.JOB_DT = jsonNode.path("JOB_DT").getTextValue();
		this.JOB_TP_NM = jsonNode.path("JOB_TP_NM").getTextValue();
		this.ORDER_NO = jsonNode.path("ORDER_NO").getTextValue();
		this.RECP_AMT = jsonNode.path("RECP_AMT").getTextValue();
		this.JOB_SEQ = jsonNode.path("JOB_SEQ").getTextValue();
		this.TOT_AMT = jsonNode.path("TOT_AMT").getTextValue();
	}

	public String getJOB_TP() {
		return this.JOB_TP;
	}

	public void setJOB_TP(String JOB_TP) {
		this.JOB_TP = JOB_TP;
	}

	public String getCUST_NM() {
		return this.CUST_NM;
	}

	public void setCUST_NM(String CUST_NM) {
		this.CUST_NM = CUST_NM;
	}

	public String getGOODS_NM() {
		return this.GOODS_NM;
	}

	public void setGOODS_NM(String GOODS_NM) {
		this.GOODS_NM = GOODS_NM;
	}

	public String getJOB_DT() {
		return this.JOB_DT;
	}

	public void setJOB_DT(String JOB_DT) {
		this.JOB_DT = JOB_DT;
	}

	public String getJOB_TP_NM() {
		return this.JOB_TP_NM;
	}

	public void setJOB_TP_NM(String JOB_TP_NM) {
		this.JOB_TP_NM = JOB_TP_NM;
	}

	public String getORDER_NO() {
		return this.ORDER_NO;
	}

	public void setORDER_NO(String ORDER_NO) {
		this.ORDER_NO = ORDER_NO;
	}

	public String getRECP_AMT() {
		return this.RECP_AMT;
	}

	public void setRECP_AMT(String RECP_AMT) {
		this.RECP_AMT = RECP_AMT;
	}

	public String getJOB_SEQ() {
		return this.JOB_SEQ;
	}

	public void setJOB_SEQ(String JOB_SEQ) {
		this.JOB_SEQ = JOB_SEQ;
	}

	public String getTOT_AMT() {
		return this.TOT_AMT;
	}

	public void setTOT_AMT(String TOT_AMT) {
		this.TOT_AMT = TOT_AMT;
	}
}