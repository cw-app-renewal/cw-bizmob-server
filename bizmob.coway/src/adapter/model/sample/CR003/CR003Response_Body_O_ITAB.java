package adapter.model.sample.CR003;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CR003Response_Body_O_ITAB {
	/**
	 * JOB_DT
	 */
	private String JOB_DT;
	/**
	 * JOB_SEQ
	 */
	private String JOB_SEQ;
	/**
	 * TEXT_GB
	 */
	private String TEXT_GB;
	/**
	 * SCHEDULE_TEXT
	 */
	private String SCHEDULE_TEXT;

	public CR003Response_Body_O_ITAB() {
	}

	public CR003Response_Body_O_ITAB(JsonNode jsonNode) {
		this.JOB_DT = jsonNode.path("JOB_DT").getTextValue();
		this.JOB_SEQ = jsonNode.path("JOB_SEQ").getTextValue();
		this.TEXT_GB = jsonNode.path("TEXT_GB").getTextValue();
		this.SCHEDULE_TEXT = jsonNode.path("SCHEDULE_TEXT").getTextValue();
	}

	public String getJOB_DT() {
		return this.JOB_DT;
	}

	public void setJOB_DT(String JOB_DT) {
		this.JOB_DT = JOB_DT;
	}

	public String getJOB_SEQ() {
		return this.JOB_SEQ;
	}

	public void setJOB_SEQ(String JOB_SEQ) {
		this.JOB_SEQ = JOB_SEQ;
	}

	public String getTEXT_GB() {
		return this.TEXT_GB;
	}

	public void setTEXT_GB(String TEXT_GB) {
		this.TEXT_GB = TEXT_GB;
	}

	public String getSCHEDULE_TEXT() {
		return this.SCHEDULE_TEXT;
	}

	public void setSCHEDULE_TEXT(String SCHEDULE_TEXT) {
		this.SCHEDULE_TEXT = SCHEDULE_TEXT;
	}
}