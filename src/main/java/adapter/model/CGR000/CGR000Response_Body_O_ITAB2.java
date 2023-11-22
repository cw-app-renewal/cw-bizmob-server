package adapter.model.CGR000;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CGR000Response_Body_O_ITAB2 {
	/**
	 * 주소2
	 */
	private String ADDR2;
	/**
	 * 전화번호1
	 */
	private String TEL_AREA;
	/**
	 * 대표자명
	 */
	private String BOSS_NM;
	/**
	 * 주소1
	 */
	private String ADDR1;
	/**
	 * 전화번호2
	 */
	private String TEL_EXT;
	/**
	 * 업태
	 */
	private String BUSI_STATUS;
	/**
	 * 전화번호3
	 */
	private String TEL_NO;
	/**
	 * 사업자번호
	 */
	private String BUSI_NO;
	/**
	 * 업종
	 */
	private String IND;
	/**
	 * 회사명
	 */
	private String BUSI_NM;
	/**
	 * 회사구분
	 */
	private String COM_CD;
	/**
	 * 웹주소
	 */
	private String WEB_ADDR;

	public CGR000Response_Body_O_ITAB2() {
	}

	public CGR000Response_Body_O_ITAB2(JsonNode jsonNode) {
		this.ADDR2 = jsonNode.path("ADDR2").getTextValue();
		this.TEL_AREA = jsonNode.path("TEL_AREA").getTextValue();
		this.BOSS_NM = jsonNode.path("BOSS_NM").getTextValue();
		this.ADDR1 = jsonNode.path("ADDR1").getTextValue();
		this.TEL_EXT = jsonNode.path("TEL_EXT").getTextValue();
		this.BUSI_STATUS = jsonNode.path("BUSI_STATUS").getTextValue();
		this.TEL_NO = jsonNode.path("TEL_NO").getTextValue();
		this.BUSI_NO = jsonNode.path("BUSI_NO").getTextValue();
		this.IND = jsonNode.path("IND").getTextValue();
		this.BUSI_NM = jsonNode.path("BUSI_NM").getTextValue();
		this.COM_CD = jsonNode.path("COM_CD").getTextValue();
		this.WEB_ADDR = jsonNode.path("WEB_ADDR").getTextValue();
	}

	public String getADDR2() {
		return this.ADDR2;
	}

	public void setADDR2(String ADDR2) {
		this.ADDR2 = ADDR2;
	}

	public String getTEL_AREA() {
		return this.TEL_AREA;
	}

	public void setTEL_AREA(String TEL_AREA) {
		this.TEL_AREA = TEL_AREA;
	}

	public String getBOSS_NM() {
		return this.BOSS_NM;
	}

	public void setBOSS_NM(String BOSS_NM) {
		this.BOSS_NM = BOSS_NM;
	}

	public String getADDR1() {
		return this.ADDR1;
	}

	public void setADDR1(String ADDR1) {
		this.ADDR1 = ADDR1;
	}

	public String getTEL_EXT() {
		return this.TEL_EXT;
	}

	public void setTEL_EXT(String TEL_EXT) {
		this.TEL_EXT = TEL_EXT;
	}

	public String getBUSI_STATUS() {
		return this.BUSI_STATUS;
	}

	public void setBUSI_STATUS(String BUSI_STATUS) {
		this.BUSI_STATUS = BUSI_STATUS;
	}

	public String getTEL_NO() {
		return this.TEL_NO;
	}

	public void setTEL_NO(String TEL_NO) {
		this.TEL_NO = TEL_NO;
	}

	public String getBUSI_NO() {
		return this.BUSI_NO;
	}

	public void setBUSI_NO(String BUSI_NO) {
		this.BUSI_NO = BUSI_NO;
	}

	public String getIND() {
		return this.IND;
	}

	public void setIND(String IND) {
		this.IND = IND;
	}

	public String getBUSI_NM() {
		return this.BUSI_NM;
	}

	public void setBUSI_NM(String BUSI_NM) {
		this.BUSI_NM = BUSI_NM;
	}

	public String getCOM_CD() {
		return this.COM_CD;
	}

	public void setCOM_CD(String COM_CD) {
		this.COM_CD = COM_CD;
	}

	public String getWEB_ADDR() {
		return this.WEB_ADDR;
	}

	public void setWEB_ADDR(String WEB_ADDR) {
		this.WEB_ADDR = WEB_ADDR;
	}
}