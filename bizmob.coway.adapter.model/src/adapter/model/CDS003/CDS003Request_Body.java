package adapter.model.CDS003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Comment
 */
public class CDS003Request_Body {
	/**
	 * 작업 일자
	 */
	private String I_JOB_DT;
	/**
	 * 사용자 번호
	 */
	private String I_UEMPL_NO;
	/**
	 * PDA 전화번호(-삭제)
	 */
	private String I_INVNR;

	public CDS003Request_Body() {
	}

	public CDS003Request_Body(JsonNode jsonNode) {
		this.I_JOB_DT = jsonNode.path("I_JOB_DT").getTextValue();
		this.I_UEMPL_NO = jsonNode.path("I_UEMPL_NO").getTextValue();
		this.I_INVNR = jsonNode.path("I_INVNR").getTextValue();
	}

	public String getI_JOB_DT() {
		return this.I_JOB_DT;
	}

	public void setI_JOB_DT(String I_JOB_DT) {
		this.I_JOB_DT = I_JOB_DT;
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
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}