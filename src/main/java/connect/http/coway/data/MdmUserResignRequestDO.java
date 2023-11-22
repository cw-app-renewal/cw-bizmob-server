package connect.http.coway.data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdmUserResignRequestDO {

	private String userId;
	private String userNm;
	private String hphone;
	
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
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	
	@Override
	public String toString() {
		return "MdmUserResignRequestDO [userId=" + userId + ", userNm="
				+ userNm + ", hphone=" + hphone + "]";
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
	
	public ArrayList<NameValuePair> getMdmUserResignNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("userNm", this.userNm));
		qparams.add(new BasicNameValuePair("hphone", this.hphone));

		return qparams;
	}
}
