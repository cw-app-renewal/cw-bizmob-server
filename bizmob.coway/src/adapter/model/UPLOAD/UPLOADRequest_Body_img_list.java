package adapter.model.UPLOAD;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class UPLOADRequest_Body_img_list {
	/**
	 * 제품코드
	 */
	private String GOODS_CD;
	/**
	 * 작업일자
	 */
	private String JOB_DT;
	/**
	 * 작업일련번호
	 */
	private String JOB_SEQ;
	/**
	 * 고객주문번호
	 */
	private String ORDER_NO;
	/**
	 * 이미지 일련번호
	 */
	private String IMG_SEQ;
	/**
	 * 작업구분
	 */
	private String JOB_TP;
	/**
	 * 이미지 타입 1:설치사진 2:주소사진 3:메모사진 6:품질코드(사진) 7:품질코드(동영상) 8:난공사 사진
	 */
	private String IMG_TYPE;
	/**
	 * 부품코드
	 */
	private String PARTS_CD;
	/**
	 * 이미지 uid
	 */
	private String IMG_UID;
	
	private String COMMAND;			//C:create, D:Delete
	private String GUBUN1;			//C:customer, W:work
	private String GUBUN2;			//NAN:난공사, QA_IMG:품직(사진), QA_MOV:품질(동영상), INST:설치, ADDR(주소)
	private String FILENAME;		//파일명
	private String FILECA;			//파일구분값(R:현상, C:원인, T:임시, F:근본)
	public UPLOADRequest_Body_img_list() {
	}

	public UPLOADRequest_Body_img_list(JsonNode jsonNode) {
		this.JOB_DT = jsonNode.path("JOB_DT").getTextValue();
		this.JOB_SEQ = jsonNode.path("JOB_SEQ").getTextValue();
		this.ORDER_NO = jsonNode.path("ORDER_NO").getTextValue();
		this.IMG_SEQ = jsonNode.path("IMG_SEQ").getTextValue();
		this.JOB_TP = jsonNode.path("JOB_TP").getTextValue();
		this.IMG_TYPE = jsonNode.path("IMG_TYPE").getTextValue();
		this.IMG_UID = jsonNode.path("IMG_UID").getTextValue();
		this.FILECA = jsonNode.path("FILECA").getTextValue();
	}

	public String getGOODS_CD() {
		return GOODS_CD;
	}

	public void setGOODS_CD(String gOODS_CD) {
		GOODS_CD = gOODS_CD;
	}

	public String getJOB_DT() {
		return JOB_DT;
	}

	public void setJOB_DT(String jOB_DT) {
		JOB_DT = jOB_DT;
	}

	public String getJOB_SEQ() {
		return JOB_SEQ;
	}

	public void setJOB_SEQ(String jOB_SEQ) {
		JOB_SEQ = jOB_SEQ;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}

	public String getIMG_SEQ() {
		return IMG_SEQ;
	}

	public void setIMG_SEQ(String iMG_SEQ) {
		IMG_SEQ = iMG_SEQ;
	}

	public String getJOB_TP() {
		return JOB_TP;
	}

	public void setJOB_TP(String jOB_TP) {
		JOB_TP = jOB_TP;
	}

	public String getIMG_TYPE() {
		return IMG_TYPE;
	}

	public void setIMG_TYPE(String iMG_TYPE) {
		IMG_TYPE = iMG_TYPE;
	}

	public String getPARTS_CD() {
		return PARTS_CD;
	}

	public void setPARTS_CD(String pARTS_CD) {
		PARTS_CD = pARTS_CD;
	}

	public String getIMG_UID() {
		return IMG_UID;
	}

	public void setIMG_UID(String iMG_UID) {
		IMG_UID = iMG_UID;
	}

	public String getCOMMAND() {
		return COMMAND;
	}

	public void setCOMMAND(String cOMMAND) {
		COMMAND = cOMMAND;
	}

	public String getGUBUN1() {
		return GUBUN1;
	}

	public void setGUBUN1(String gUBUN1) {
		GUBUN1 = gUBUN1;
	}

	public String getGUBUN2() {
		return GUBUN2;
	}

	public void setGUBUN2(String gUBUN2) {
		GUBUN2 = gUBUN2;
	}

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public String getFILECA() {
		return FILECA;
	}

	public void setFILECA(String fILECA) {
		FILECA = fILECA;
	}

	@Override
	public String toString() {
		return "UPLOADRequest_Body_img_list [GOODS_CD=" + GOODS_CD + ", JOB_DT=" + JOB_DT + ", JOB_SEQ=" + JOB_SEQ
				+ ", ORDER_NO=" + ORDER_NO + ", IMG_SEQ=" + IMG_SEQ + ", JOB_TP=" + JOB_TP + ", IMG_TYPE=" + IMG_TYPE
				+ ", PARTS_CD=" + PARTS_CD + ", IMG_UID=" + IMG_UID + ", COMMAND=" + COMMAND + ", GUBUN1=" + GUBUN1
				+ ", GUBUN2=" + GUBUN2 + ", FILENAME=" + FILENAME + ", FILECA=" + FILECA + "]";
	}

	
	
}