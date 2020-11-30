package connector.sqlite.doctor.dao.data;

public class DoctorQcBBSDO {

	private	String	GUBUN;		// BC구분(B,C)	1
	private String	CODE;		// 제품군/제품코드	18
	private String	SEQ;		// 순번			20
	private String	TITLE;		// 제목			255
	
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
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	
}
