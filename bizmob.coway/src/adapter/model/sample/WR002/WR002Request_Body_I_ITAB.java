package adapter.model.sample.WR002;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class WR002Request_Body_I_ITAB {
	/**
	 * RSCH_ANS_SEQ
	 */
	private String RSCH_ANS_SEQ;
	/**
	 * RSCH_ASK_SEQ
	 */
	private String RSCH_ASK_SEQ;

	public WR002Request_Body_I_ITAB() {
	}

	public WR002Request_Body_I_ITAB(JsonNode jsonNode) {
		this.RSCH_ANS_SEQ = jsonNode.path("RSCH_ANS_SEQ").getTextValue();
		this.RSCH_ASK_SEQ = jsonNode.path("RSCH_ASK_SEQ").getTextValue();
	}

	public String getRSCH_ANS_SEQ() {
		return this.RSCH_ANS_SEQ;
	}

	public void setRSCH_ANS_SEQ(String RSCH_ANS_SEQ) {
		this.RSCH_ANS_SEQ = RSCH_ANS_SEQ;
	}

	public String getRSCH_ASK_SEQ() {
		return this.RSCH_ASK_SEQ;
	}

	public void setRSCH_ASK_SEQ(String RSCH_ASK_SEQ) {
		this.RSCH_ASK_SEQ = RSCH_ASK_SEQ;
	}
}