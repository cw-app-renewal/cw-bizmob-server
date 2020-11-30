package connect.http.coway.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class InitDeviceAuthRequestDO {

	private String userId = "";					//사용자ID
	private String withPublicFlag = "";			//공용단말기 포함여부 (Y:포함, N:포함안함(기본값))
	private String deviceAuthNoArray01 = "";		//단말기인증번호 #01
	private String deviceAuthNoArray02 = "";		//단말기인증번호 #02
	private String deviceAuthNoArray03 = "";		//단말기인증번호 #03
	private String deviceAuthNoArray04 = "";		//단말기인증번호 #04
	private String deviceAuthNoArray05 = "";		//단말기인증번호 #05
	private List<String> deviceAuthArray = null;
		
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWithPublicFlag() {
		return withPublicFlag;
	}
	public void setWithPublicFlag(String withPublicFlag) {
		this.withPublicFlag = withPublicFlag;
	}
	
	public String getDeviceAuthNoArray01() {
		return deviceAuthNoArray01;
	}
	public void setDeviceAuthNoArray01(String deviceAuthNoArray01) {
		this.deviceAuthNoArray01 = deviceAuthNoArray01;
	}
	public String getDeviceAuthNoArray02() {
		return deviceAuthNoArray02;
	}
	public void setDeviceAuthNoArray02(String deviceAuthNoArray02) {
		this.deviceAuthNoArray02 = deviceAuthNoArray02;
	}
	public String getDeviceAuthNoArray03() {
		return deviceAuthNoArray03;
	}
	public void setDeviceAuthNoArray03(String deviceAuthNoArray03) {
		this.deviceAuthNoArray03 = deviceAuthNoArray03;
	}
	public String getDeviceAuthNoArray04() {
		return deviceAuthNoArray04;
	}
	public void setDeviceAuthNoArray04(String deviceAuthNoArray04) {
		this.deviceAuthNoArray04 = deviceAuthNoArray04;
	}
	public String getDeviceAuthNoArray05() {
		return deviceAuthNoArray05;
	}
	public void setDeviceAuthNoArray05(String deviceAuthNoArray05) {
		this.deviceAuthNoArray05 = deviceAuthNoArray05;
	}
	public List<String> getDeviceAuthArray() {
		return deviceAuthArray;
	}
	public void setDeviceAuthArray(List<String> deviceAuthArray) {
		this.deviceAuthArray = deviceAuthArray;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	
	public ArrayList<NameValuePair> getInitDeviceAuthNameValueLimitList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("withPublicFlag", this.withPublicFlag));
		qparams.add(new BasicNameValuePair("deviceAuthNoArray", this.deviceAuthNoArray01));
		qparams.add(new BasicNameValuePair("deviceAuthNoArray", this.deviceAuthNoArray02));
		qparams.add(new BasicNameValuePair("deviceAuthNoArray", this.deviceAuthNoArray03));
		qparams.add(new BasicNameValuePair("deviceAuthNoArray", this.deviceAuthNoArray04));
		qparams.add(new BasicNameValuePair("deviceAuthNoArray", this.deviceAuthNoArray05));
		
		return qparams;
	}	
	
	public ArrayList<NameValuePair> getInitDeviceAuthNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("withPublicFlag", this.withPublicFlag));
		
		for(String deviceAuth : this.deviceAuthArray) {
			qparams.add(new BasicNameValuePair("deviceAuthNoArray", deviceAuth));
		}
				
		return qparams;
	}
}
