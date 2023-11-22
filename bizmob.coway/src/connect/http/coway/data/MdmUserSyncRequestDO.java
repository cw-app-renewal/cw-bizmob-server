package connect.http.coway.data;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class MdmUserSyncRequestDO {

	private String userId;
	private String userNm;
	private String hphone;
	private String deptId;
	private String companyId;
	private String positionId;
	
	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getHphone() {
		return hphone;
	}
	public void setHphone(String hphone) {
		String[] phone = getSplitTelNo(hphone); 
		this.hphone = phone[0] + "-" + phone[1] + "-" + phone[2];
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	
	public ArrayList<NameValuePair> getMdmUserSyncNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("userNm", this.userNm));
		qparams.add(new BasicNameValuePair("hphone", this.hphone));
		qparams.add(new BasicNameValuePair("deptId", this.deptId));
		qparams.add(new BasicNameValuePair("companyId", this.companyId));
		qparams.add(new BasicNameValuePair("positionId", this.positionId));

		return qparams;
	}
	
	@Override
	public String toString() {
		return "MdmUserSyncRequestDO [userId=" + userId + ", userNm=" + userNm + ", hphone=" + hphone + ", deptId="
				+ deptId + ", companyId=" + companyId + ", positionId=" + positionId + "]";
	}
	
	private String[] getSplitTelNo(String noStr) {
		
		Pattern telPattern = Pattern.compile("^(01\\d{1}|\\+82\\d{2})-?(\\d{3,4})-?(\\d{4})");
		if(noStr == null)
			return new String[]{"", "", ""};
		Matcher matcher = telPattern.matcher(noStr);
		if(matcher.matches()) {
			return new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
		} else {
			return new String[]{"", "", ""};
		}		
	}
}
