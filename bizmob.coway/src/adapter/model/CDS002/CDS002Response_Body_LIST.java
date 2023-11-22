package adapter.model.CDS002;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CDS002Response_Body_LIST {
	/**
	 * LIMIT_QTY
	 */
	private String LIMIT_QTY;
	/**
	 * EXAM_YN
	 */
	private String EXAM_YN;
	/**
	 * FILTER_YN
	 */
	private String FILTER_YN;
	/**
	 * DB_FLAG
	 */
	private String DB_FLAG;
	/**
	 * PART_CD
	 */
	private String PART_CD;
	/**
	 * EXTWG_TP
	 */
	private String EXTWG_TP;
	/**
	 * HEART_PLUS_YN
	 */
	private String HEART_PLUS_YN;
	/**
	 * SLT_PROC_TP
	 */
	private String SLT_PROC_TP;
	/**
	 * GOODS_CD
	 */
	private String GOODS_CD;
	/**
	 * SALE_AMT
	 */
	private String SALE_AMT;
	/**
	 * GB_TP
	 */
	private String GB_TP;
	/**
	 * PRDHA
	 */
	private String PRDHA;
	/**
	 * PART_NM
	 */
	private String PART_NM;

	public CDS002Response_Body_LIST() {
	}

	public CDS002Response_Body_LIST(JsonNode jsonNode) {
		this.LIMIT_QTY = jsonNode.path("LIMIT_QTY").getTextValue();
		this.EXAM_YN = jsonNode.path("EXAM_YN").getTextValue();
		this.FILTER_YN = jsonNode.path("FILTER_YN").getTextValue();
		this.DB_FLAG = jsonNode.path("DB_FLAG").getTextValue();
		this.PART_CD = jsonNode.path("PART_CD").getTextValue();
		this.EXTWG_TP = jsonNode.path("EXTWG_TP").getTextValue();
		this.HEART_PLUS_YN = jsonNode.path("HEART_PLUS_YN").getTextValue();
		this.SLT_PROC_TP = jsonNode.path("SLT_PROC_TP").getTextValue();
		this.GOODS_CD = jsonNode.path("GOODS_CD").getTextValue();
		this.SALE_AMT = jsonNode.path("SALE_AMT").getTextValue();
		this.GB_TP = jsonNode.path("GB_TP").getTextValue();
		this.PRDHA = jsonNode.path("PRDHA").getTextValue();
		this.PART_NM = jsonNode.path("PART_NM").getTextValue();
	}

	public String getLIMIT_QTY() {
		return this.LIMIT_QTY;
	}

	public void setLIMIT_QTY(String LIMIT_QTY) {
		this.LIMIT_QTY = LIMIT_QTY;
	}

	public String getEXAM_YN() {
		return this.EXAM_YN;
	}

	public void setEXAM_YN(String EXAM_YN) {
		this.EXAM_YN = EXAM_YN;
	}

	public String getFILTER_YN() {
		return this.FILTER_YN;
	}

	public void setFILTER_YN(String FILTER_YN) {
		this.FILTER_YN = FILTER_YN;
	}

	public String getDB_FLAG() {
		return this.DB_FLAG;
	}

	public void setDB_FLAG(String DB_FLAG) {
		this.DB_FLAG = DB_FLAG;
	}

	public String getPART_CD() {
		return this.PART_CD;
	}

	public void setPART_CD(String PART_CD) {
		this.PART_CD = PART_CD;
	}

	public String getEXTWG_TP() {
		return this.EXTWG_TP;
	}

	public void setEXTWG_TP(String EXTWG_TP) {
		this.EXTWG_TP = EXTWG_TP;
	}

	public String getHEART_PLUS_YN() {
		return this.HEART_PLUS_YN;
	}

	public void setHEART_PLUS_YN(String HEART_PLUS_YN) {
		this.HEART_PLUS_YN = HEART_PLUS_YN;
	}

	public String getSLT_PROC_TP() {
		return this.SLT_PROC_TP;
	}

	public void setSLT_PROC_TP(String SLT_PROC_TP) {
		this.SLT_PROC_TP = SLT_PROC_TP;
	}

	public String getGOODS_CD() {
		return this.GOODS_CD;
	}

	public void setGOODS_CD(String GOODS_CD) {
		this.GOODS_CD = GOODS_CD;
	}

	public String getSALE_AMT() {
		return this.SALE_AMT;
	}

	public void setSALE_AMT(String SALE_AMT) {
		this.SALE_AMT = SALE_AMT;
	}

	public String getGB_TP() {
		return this.GB_TP;
	}

	public void setGB_TP(String GB_TP) {
		this.GB_TP = GB_TP;
	}

	public String getPRDHA() {
		return this.PRDHA;
	}

	public void setPRDHA(String PRDHA) {
		this.PRDHA = PRDHA;
	}

	public String getPART_NM() {
		return this.PART_NM;
	}

	public void setPART_NM(String PART_NM) {
		this.PART_NM = PART_NM;
	}
}