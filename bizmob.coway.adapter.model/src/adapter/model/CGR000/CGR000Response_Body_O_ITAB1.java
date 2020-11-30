package adapter.model.CGR000;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CGR000Response_Body_O_ITAB1 {
	/**
	 * 구분2(직급)
	 */
	private String GUBUN2;
	/**
	 * 사원번호
	 */
	private String PERNR;
	/**
	 * 사무실연락처
	 */
	private String TEL_NUMBER;
	/**
	 * 연락처
	 */
	private String MOB_NUMBER;
	/**
	 * 이름
	 */
	private String UEMPL_NM;
	/**
	 * 구분1(소속)
	 */
	private String GUBUN1;

	public CGR000Response_Body_O_ITAB1() {
	}

	public CGR000Response_Body_O_ITAB1(JsonNode jsonNode) {
		this.GUBUN2 = jsonNode.path("GUBUN2").getTextValue();
		this.PERNR = jsonNode.path("PERNR").getTextValue();
		this.TEL_NUMBER = jsonNode.path("TEL_NUMBER").getTextValue();
		this.MOB_NUMBER = jsonNode.path("MOB_NUMBER").getTextValue();
		this.UEMPL_NM = jsonNode.path("UEMPL_NM").getTextValue();
		this.GUBUN1 = jsonNode.path("GUBUN1").getTextValue();
	}

	public String getGUBUN2() {
		return this.GUBUN2;
	}

	public void setGUBUN2(String GUBUN2) {
		this.GUBUN2 = GUBUN2;
	}

	public String getPERNR() {
		return this.PERNR;
	}

	public void setPERNR(String PERNR) {
		this.PERNR = PERNR;
	}

	public String getTEL_NUMBER() {
		return this.TEL_NUMBER;
	}

	public void setTEL_NUMBER(String TEL_NUMBER) {
		this.TEL_NUMBER = TEL_NUMBER;
	}

	public String getMOB_NUMBER() {
		return this.MOB_NUMBER;
	}

	public void setMOB_NUMBER(String MOB_NUMBER) {
		this.MOB_NUMBER = MOB_NUMBER;
	}

	public String getUEMPL_NM() {
		return this.UEMPL_NM;
	}

	public void setUEMPL_NM(String UEMPL_NM) {
		this.UEMPL_NM = UEMPL_NM;
	}

	public String getGUBUN1() {
		return this.GUBUN1;
	}

	public void setGUBUN1(String GUBUN1) {
		this.GUBUN1 = GUBUN1;
	}
}