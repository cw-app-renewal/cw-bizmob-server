package adapter.model.DELETE;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class DELETERequest_Body_img_list {
	/**
	 * 고객주문번호
	 */
	private String ORDER_NO;
	/**
	 * 제품코드
	 */
	private String GOODS_CD;
	/**
	 * 작업일자
	 */
	private String JOB_DT;
	/**
	 * 이미지 일련번호
	 */
	private String IMG_SEQ;
	/**
	 * 작업 일련번호
	 */
	private String JOB_SEQ;
	/**
	 * 작업구분
	 */
	private String JOB_TP;
	/**
	 * 이미지 타입 1:설치사진 2:주소사진 3:메모사진
	 */
	private String IMG_TYPE;
	/**
	 * 부품코드
	 */
	private String PARTS_CD;

	private String FILECA;
	
	public DELETERequest_Body_img_list() {
	}

	public DELETERequest_Body_img_list(JsonNode jsonNode) {
		this.ORDER_NO = jsonNode.path("ORDER_NO").getTextValue();
		this.JOB_DT = jsonNode.path("JOB_DT").getTextValue();
		this.IMG_SEQ = jsonNode.path("IMG_SEQ").getTextValue();
		this.JOB_SEQ = jsonNode.path("JOB_SEQ").getTextValue();
		this.JOB_TP = jsonNode.path("JOB_TP").getTextValue();
		this.IMG_TYPE = jsonNode.path("IMG_TYPE").getTextValue();
		this.FILECA = jsonNode.path("FILECA").getTextValue();
	}

	public String getFILECA() {
		return FILECA;
	}

	public void setFILECA(String fILECA) {
		FILECA = fILECA;
	}

	public String getOrder_no() {
		return this.ORDER_NO;
	}

	public void setOrder_no(String order_no) {
		this.ORDER_NO = order_no;
	}

	public String getGoods_cd() {
		return this.GOODS_CD;
	}

	public void setGoods_cd(String goods_cd) {
		this.GOODS_CD = goods_cd;
	}

	public String getJob_dt() {
		return this.JOB_DT;
	}

	public void setJob_dt(String job_dt) {
		this.JOB_DT = job_dt;
	}

	public String getImg_seq() {
		return this.IMG_SEQ;
	}

	public void setImg_seq(String img_seq) {
		this.IMG_SEQ = img_seq;
	}

	public String getJob_seq() {
		return this.JOB_SEQ;
	}

	public void setJob_seq(String job_seq) {
		this.JOB_SEQ = job_seq;
	}

	public String getJob_tp() {
		return this.JOB_TP;
	}

	public void setJob_tp(String job_tp) {
		this.JOB_TP = job_tp;
	}

	public String getImg_type() {
		return this.IMG_TYPE;
	}

	public void setImg_type(String img_type) {
		this.IMG_TYPE = img_type;
	}

	public String getParts_cd() {
		return this.PARTS_CD;
	}

	public void setParts_cd(String parts_cd) {
		this.PARTS_CD = parts_cd;
	}

	@Override
	public String toString() {
		return "DELETERequest_Body_img_list [ORDER_NO=" + ORDER_NO
				+ ", GOODS_CD=" + GOODS_CD + ", JOB_DT=" + JOB_DT
				+ ", IMG_SEQ=" + IMG_SEQ + ", JOB_SEQ=" + JOB_SEQ + ", JOB_TP="
				+ JOB_TP + ", IMG_TYPE=" + IMG_TYPE + ", PARTS_CD=" + PARTS_CD
				+ "]";
	}
	
	
}