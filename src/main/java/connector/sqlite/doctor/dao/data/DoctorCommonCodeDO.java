package connector.sqlite.doctor.dao.data;

public class DoctorCommonCodeDO {

	private String OTH_CLASS;			//코드 그룹
	private String OTH_CD;				//코드
	private String OTH_CD_NM;			//코드명
	private String SORT;				//정렬
	
	public String getOTH_CLASS() {
		return OTH_CLASS;
	}
	public void setOTH_CLASS(String oTH_CLASS) {
		OTH_CLASS = oTH_CLASS;
	}
	public String getOTH_CD() {
		return OTH_CD;
	}
	public void setOTH_CD(String oTH_CD) {
		OTH_CD = oTH_CD;
	}
	public String getOTH_CD_NM() {
		return OTH_CD_NM;
	}
	public void setOTH_CD_NM(String oTH_CD_NM) {
		OTH_CD_NM = oTH_CD_NM;
	}
	
	public String getSORT() {
		return SORT;
	}
	public void setSORT(String sORT) {
		SORT = sORT;
	}
	
	
}
