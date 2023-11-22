package connector.sqlite.doctor.dao.data;

public class DoctorBBSImgDO {
	
	private	String	GUBUN;			// BC구분(B,C)	1
	private String	CODE;			// 제품군/제품코드	18
	private String	SEQ;			// 순번			20
	private String	FILENAME;		// 첨부파일명		200
	private String	PATH_FILENAME;	// 첨부파일경로		200
	
	public String getGUBUN() {
		return GUBUN;
	}
	public void setGUBUN(String gUBUN) {
		GUBUN = gUBUN;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getPATH_FILENAME() {
		return PATH_FILENAME;
	}
	public void setPATH_FILENAME(String pATH_FILENAME) {
		PATH_FILENAME = pATH_FILENAME;
	}
	
}
