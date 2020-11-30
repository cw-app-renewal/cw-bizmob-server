package adapter.model.CGR000;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CGR000Response_Body_O_ITAB3 {
	/**
	 * 공지일자
	 */
	private String CREATEDDATE;
	/**
	 * 문서번호
	 */
	private String DOCID;
	/**
	 * 신규구분
	 */
	private String NEW_YN;
	/**
	 * 제목
	 */
	private String SUBJECT;
	/**
	 * 첨부파일 유무
	 */
	private String ATTACH_YN;

	public CGR000Response_Body_O_ITAB3() {
	}

	public CGR000Response_Body_O_ITAB3(JsonNode jsonNode) {
		this.CREATEDDATE = jsonNode.path("CREATEDDATE").getTextValue();
		this.DOCID = jsonNode.path("DOCID").getTextValue();
		this.NEW_YN = jsonNode.path("NEW_YN").getTextValue();
		this.SUBJECT = jsonNode.path("SUBJECT").getTextValue();
		this.ATTACH_YN = jsonNode.path("ATTACH_YN").getTextValue();
	}

	public String getCREATEDDATE() {
		return this.CREATEDDATE;
	}

	public void setCREATEDDATE(String CREATEDDATE) {
		this.CREATEDDATE = CREATEDDATE;
	}

	public String getDOCID() {
		return this.DOCID;
	}

	public void setDOCID(String DOCID) {
		this.DOCID = DOCID;
	}

	public String getNEW_YN() {
		return this.NEW_YN;
	}

	public void setNEW_YN(String NEW_YN) {
		this.NEW_YN = NEW_YN;
	}

	public String getSUBJECT() {
		return this.SUBJECT;
	}

	public void setSUBJECT(String SUBJECT) {
		this.SUBJECT = SUBJECT;
	}

	public String getATTACH_YN() {
		return this.ATTACH_YN;
	}

	public void setATTACH_YN(String ATTACH_YN) {
		this.ATTACH_YN = ATTACH_YN;
	}
}