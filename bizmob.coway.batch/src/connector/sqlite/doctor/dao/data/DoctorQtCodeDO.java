package connector.sqlite.doctor.dao.data;

public class DoctorQtCodeDO {

	private String DIV_CD;							//코드분류
	private String PRDHA;							//제품 계층 구조
	private String CODEGROUP;					//그룹 코드
	private String CODEGROUP_DTL;				//그룹 상세 코드
	private String CODEGROUP_NM;				//그룹 코드 명
	private String CODEGROUP_DTL_NM;			//그룹 상세 코드 명
	private String PL_YN;								//PL 여부
	private String PROC_DTL_OPT;					//처리 형태 상세 제한 구분
	private String PART_CHG_YN;					//부품 교환 필수 여부
	private String AS_IN_YN;						//수리 입고 구분 필수 여부
	private String CAUSEBYCUST_YN;				//고객 과실 여부
	
	
	public String getDIV_CD() {
		return DIV_CD;
	}
	public void setDIV_CD(String dIV_CD) {
		DIV_CD = dIV_CD;
	}
	public String getPRDHA() {
		return PRDHA;
	}
	public void setPRDHA(String pRDHA) {
		PRDHA = pRDHA;
	}
	public String getCODEGROUP() {
		return CODEGROUP;
	}
	public void setCODEGROUP(String cODEGROUP) {
		CODEGROUP = cODEGROUP;
	}
	public String getCODEGROUP_DTL() {
		return CODEGROUP_DTL;
	}
	public void setCODEGROUP_DTL(String cODEGROUP_DTL) {
		CODEGROUP_DTL = cODEGROUP_DTL;
	}
	public String getCODEGROUP_NM() {
		return CODEGROUP_NM;
	}
	public void setCODEGROUP_NM(String cODEGROUP_NM) {
		CODEGROUP_NM = cODEGROUP_NM;
	}
	public String getCODEGROUP_DTL_NM() {
		return CODEGROUP_DTL_NM;
	}
	public void setCODEGROUP_DTL_NM(String cODEGROUP_DTL_NM) {
		CODEGROUP_DTL_NM = cODEGROUP_DTL_NM;
	}
	public String getPL_YN() {
		return PL_YN;
	}
	public void setPL_YN(String pL_YN) {
		PL_YN = pL_YN;
	}
	public String getPROC_DTL_OPT() {
		return PROC_DTL_OPT;
	}
	public void setPROC_DTL_OPT(String pROC_DTL_OPT) {
		PROC_DTL_OPT = pROC_DTL_OPT;
	}
	public String getPART_CHG_YN() {
		return PART_CHG_YN;
	}
	public void setPART_CHG_YN(String pART_CHG_YN) {
		PART_CHG_YN = pART_CHG_YN;
	}
	public String getAS_IN_YN() {
		return AS_IN_YN;
	}
	public void setAS_IN_YN(String aS_IN_YN) {
		AS_IN_YN = aS_IN_YN;
	}
	public String getCAUSEBYCUST_YN() {
		return CAUSEBYCUST_YN;
	}
	public void setCAUSEBYCUST_YN(String cAUSEBYCUST_YN) {
		CAUSEBYCUST_YN = cAUSEBYCUST_YN;
	}
	
	
	
	
}
