package connect.http.coway.data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

public class InsertDeviceRequestDO {

	private String deviceType = "";				//단말기구분 (PUBLIC:공용단말기, PRIVATE:개인단말기)
	private String companyId = "";				//회사ID
	private String userId = "";					//사용자ID
	private String carrierCode = "";			//이통사코드 (SKT:SKT, KT:KT, LGU:LG U+)
	private String deviceAuthNo = "";			//단말기인증번호 (미입력시 시스템에서 자동부여)
	private String alias = "";					//별칭
	private String option1 = "";				//추가정보01
	private String option2 = "";				//추가정보02
	private String option3 = "";				//추가정보03
	private String option4 = "";				//추가정보04
	private String option5 = "";				//추가정보05
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getDeviceAuthNo() {
		return deviceAuthNo;
	}
	public void setDeviceAuthNo(String deviceAuthNo) {
		this.deviceAuthNo = deviceAuthNo;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getOption5() {
		return option5;
	}
	public void setOption5(String option5) {
		this.option5 = option5;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	
	@Override
	public String toString() {
		return "InsertDeviceDO [deviceType=" + deviceType + ", companyId="
				+ companyId + ", userId=" + userId + ", carrierCode="
				+ carrierCode + ", deviceAuthNo=" + deviceAuthNo + ", alias="
				+ alias + ", option1=" + option1 + ", option2=" + option2
				+ ", option3=" + option3 + ", option4=" + option4
				+ ", option5=" + option5 + "]";
	}	
	
	public ArrayList<NameValuePair> getInsertDeviceNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("deviceType", this.deviceType));
		qparams.add(new BasicNameValuePair("companyId", this.companyId));
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("carrierCode", this.carrierCode));
		qparams.add(new BasicNameValuePair("deviceAuthNo", this.deviceAuthNo));
		qparams.add(new BasicNameValuePair("alias", this.alias));
		qparams.add(new BasicNameValuePair("option1", this.option1));
		qparams.add(new BasicNameValuePair("option2", this.option2));
		qparams.add(new BasicNameValuePair("option3", this.option3));
		qparams.add(new BasicNameValuePair("option4", this.option4));
		qparams.add(new BasicNameValuePair("option5", this.option5));
		
		return qparams;
	}
	
}
