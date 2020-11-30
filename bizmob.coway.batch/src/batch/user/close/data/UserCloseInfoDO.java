package batch.user.close.data;

public class UserCloseInfoDO {

	private String PERNR = "";
	private String SNAME = "";
	private String PHN_NO = "";
	private String PMNUM = "";
	private String GBN = "";			//A:사용자 단말기 모두 삭제, 1:단말기만 삭제
	
	
	public String getPERNR() {
		return PERNR;
	}
	public void setPERNR(String pERNR) {
		PERNR = pERNR;
	}
	public String getSNAME() {
		return SNAME;
	}
	public void setSNAME(String sNAME) {
		SNAME = sNAME;
	}
	public String getPHN_NO() {
		return PHN_NO;
	}
	public void setPHN_NO(String pHN_NO) {
		PHN_NO = pHN_NO;
	}
	public String getPMNUM() {
		return PMNUM;
	}
	public void setPMNUM(String pMNUM) {
		PMNUM = pMNUM;
	}
	public String getGBN() {
		return GBN;
	}
	public void setGBN(String gBN) {
		GBN = gBN;
	}
	@Override
	public String toString() {
		return "UserCloseInfoDO [PERNR=" + PERNR + ", SNAME=" + SNAME
				+ ", PHN_NO=" + PHN_NO + ", PMNUM=" + PMNUM + ", GBN=" + GBN
				+ "]";
	}
	
	
	
}
