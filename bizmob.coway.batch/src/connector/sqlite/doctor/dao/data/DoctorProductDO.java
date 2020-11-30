package connector.sqlite.doctor.dao.data;

public class DoctorProductDO {
	
	private String GOODS_CD;				//제품코드
	private String PART_CD;					//부품코드
	private String PRDHA;					//제품계층구조
	private String PART_NM;					//부품(자재)명
	private String FILTER_YN;				//필터여부
	private String EXAM_YN;					//점검코드 여부
	private String BARCODE_YN;				//바코드 필수 여부
	private String EXPEND_YN;				//소모성 자재 여부
	private String GB_TP;					//설치 자재 여부(아답터 피팅류)
	private String SALE_AMT;				//판매금액
	private String EXTWG_TP;				//자재 세부 구분 (A:아답터류)
	private String HEART_PLUS_YN;			//순환키드 등록 대상여부
	private String SLT_PROC_TP;				//지정부품 대상 처리 형태
	private String LIMIT_QTY;				//입력제한 수량
	private String OPT_FILTER_YN;			//옵션 필터 여부
	
	public String getGOODS_CD() {
		return GOODS_CD;
	}
	public void setGOODS_CD(String gOODS_CD) {
		GOODS_CD = gOODS_CD;
	}
	public String getPART_CD() {
		return PART_CD;
	}
	public void setPART_CD(String pART_CD) {
		PART_CD = pART_CD;
	}
	public String getPRDHA() {
		return PRDHA;
	}
	public void setPRDHA(String pRDHA) {
		PRDHA = pRDHA;
	}
	public String getPART_NM() {
		return PART_NM;
	}
	public void setPART_NM(String pART_NM) {
		PART_NM = pART_NM;
	}
	public String getFILTER_YN() {
		return FILTER_YN;
	}
	public void setFILTER_YN(String fILTER_YN) {
		FILTER_YN = fILTER_YN;
	}
	public String getEXAM_YN() {
		return EXAM_YN;
	}
	public void setEXAM_YN(String eXAM_YN) {
		EXAM_YN = eXAM_YN;
	}
	public String getBARCODE_YN() {
		return BARCODE_YN;
	}
	public void setBARCODE_YN(String bARCODE_YN) {
		BARCODE_YN = bARCODE_YN;
	}
	public String getEXPEND_YN() {
		return EXPEND_YN;
	}
	public void setEXPEND_YN(String eXPEND_YN) {
		EXPEND_YN = eXPEND_YN;
	}
	public String getGB_TP() {
		return GB_TP;
	}
	public void setGB_TP(String gB_TP) {
		GB_TP = gB_TP;
	}
	public String getSALE_AMT() {
		return SALE_AMT;
	}
	public void setSALE_AMT(String sALE_AMT) {
		SALE_AMT = sALE_AMT;
	}
	public String getEXTWG_TP() {
		return EXTWG_TP;
	}
	public void setEXTWG_TP(String eXTWG_TP) {
		EXTWG_TP = eXTWG_TP;
	}
	public String getHEART_PLUS_YN() {
		return HEART_PLUS_YN;
	}
	public void setHEART_PLUS_YN(String hEART_PLUS_YN) {
		HEART_PLUS_YN = hEART_PLUS_YN;
	}
	public String getSLT_PROC_TP() {
		return SLT_PROC_TP;
	}
	public void setSLT_PROC_TP(String sLT_PROC_TP) {
		SLT_PROC_TP = sLT_PROC_TP;
	}
	public String getLIMIT_QTY() {
		return LIMIT_QTY;
	}
	public void setLIMIT_QTY(String lIMIT_QTY) {
		LIMIT_QTY = lIMIT_QTY;
	}
	public String getOPT_FILTER_YN() {
		return OPT_FILTER_YN;
	}
	public void setOPT_FILTER_YN(String oPT_FILTER_YN) {
		OPT_FILTER_YN = oPT_FILTER_YN;
	}
	
	
	

}
