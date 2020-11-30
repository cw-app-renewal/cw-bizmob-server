package adapter.model.sample.RD001;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class RD001Response_Body {
	/**
	 * 사용자번호
	 */
	private String O_PERNR;
	/**
	 * 사용자이름
	 */
	private String O_SNAME;
	/**
	 * 사용자부서
	 */
	private String O_DEPT_CD;
	/**
	 * 사용자구분
	 */
	private String O_USER_TP;
	/**
	 * 패스워드
	 */
	private String O_ATWRT;
	/**
	 * 사용자부서명
	 */
	private String O_DEPT_NM;

	public RD001Response_Body() {
	}

	public RD001Response_Body(JsonNode jsonNode) {
		this.O_PERNR = jsonNode.path("O_PERNR").getTextValue();
		this.O_SNAME = jsonNode.path("O_SNAME").getTextValue();
		this.O_DEPT_CD = jsonNode.path("O_DEPT_CD").getTextValue();
		this.O_USER_TP = jsonNode.path("O_USER_TP").getTextValue();
		this.O_ATWRT = jsonNode.path("O_ATWRT").getTextValue();
		this.O_DEPT_NM = jsonNode.path("O_DEPT_NM").getTextValue();
	}

	public String getO_PERNR() {
		return this.O_PERNR;
	}

	public void setO_PERNR(String O_PERNR) {
		this.O_PERNR = O_PERNR;
	}

	public String getO_SNAME() {
		return this.O_SNAME;
	}

	public void setO_SNAME(String O_SNAME) {
		this.O_SNAME = O_SNAME;
	}

	public String getO_DEPT_CD() {
		return this.O_DEPT_CD;
	}

	public void setO_DEPT_CD(String O_DEPT_CD) {
		this.O_DEPT_CD = O_DEPT_CD;
	}

	public String getO_USER_TP() {
		return this.O_USER_TP;
	}

	public void setO_USER_TP(String O_USER_TP) {
		this.O_USER_TP = O_USER_TP;
	}

	public String getO_ATWRT() {
		return this.O_ATWRT;
	}

	public void setO_ATWRT(String O_ATWRT) {
		this.O_ATWRT = O_ATWRT;
	}

	public String getO_DEPT_NM() {
		return this.O_DEPT_NM;
	}

	public void setO_DEPT_NM(String O_DEPT_NM) {
		this.O_DEPT_NM = O_DEPT_NM;
	}
}