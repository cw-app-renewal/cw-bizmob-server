package connect.http.coway.data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

public class UpdateUserRequestDO {

	private String userId = "";					//사용자 ID
	private String userGroupId = "";				//사용자그룹ID (USGR0000000001:기본그룹)
	private String roleId = "";					//권한그룹ID (ROLE0000000003:단말사용자)
	private String companyId = "";				//회사ID
	private String userType = "";				//사용자유형 (USER:일반사용자, ADMIN:어드민)
	private String userName = "";				//사용자명
	private String lossFlag = "";				//분실여부 (Y:분실함, N:분실안함)
	private String remoteDeleteFlag = "";		//원격삭제여부 (Y:삭제함, N:삭제안함)
	private String downloadFlag = "";			//다운로드권한여부 (Y:On, N:Off)
	private String publicUseFlag = "";			//공용단말기사용여부 (Y:사용, N:사용안함)
	private String alias = "";					//별칭
	private String department = "";				//부서
	private String employeeNo = "";				//사번
	private String tel = "";						//연락처
	private String mail = "";					//메일
	private String option1 = "";					//추가정보1
	private String option2 = "";					//추가정보2
	private String option3 = "";					//추가정보3
	private String option4 = "";					//추가정보4
	private String option5 = "";					//추가정보5
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLossFlag() {
		return lossFlag;
	}
	public void setLossFlag(String lossFlag) {
		this.lossFlag = lossFlag;
	}
	public String getRemoteDeleteFlag() {
		return remoteDeleteFlag;
	}
	public void setRemoteDeleteFlag(String remoteDeleteFlag) {
		this.remoteDeleteFlag = remoteDeleteFlag;
	}
	public String getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	public String getPublicUseFlag() {
		return publicUseFlag;
	}
	public void setPublicUseFlag(String publicUseFlag) {
		this.publicUseFlag = publicUseFlag;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
		return "UpdateUserRequestDO [userId=" + userId + ", userGroupId="
				+ userGroupId + ", roleId=" + roleId + ", companyId="
				+ companyId + ", userType=" + userType + ", userName="
				+ userName + ", lossFlag=" + lossFlag + ", remoteDeleteFlag="
				+ remoteDeleteFlag + ", downloadFlag=" + downloadFlag
				+ ", publicUseFlag=" + publicUseFlag + ", alias=" + alias
				+ ", department=" + department + ", employeeNo=" + employeeNo
				+ ", tel=" + tel + ", mail=" + mail + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3
				+ ", option4=" + option4 + ", option5=" + option5 + "]";
	}
	
	public ArrayList<NameValuePair> getUpdateUserNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", this.userId));
		qparams.add(new BasicNameValuePair("userGroupId", this.userGroupId));
		qparams.add(new BasicNameValuePair("roleId", this.roleId));
		qparams.add(new BasicNameValuePair("companyId", this.companyId));
		qparams.add(new BasicNameValuePair("userType", this.userType));
		qparams.add(new BasicNameValuePair("userName", this.userName));
		qparams.add(new BasicNameValuePair("lossFlag", this.lossFlag));
		qparams.add(new BasicNameValuePair("remoteDeleteFlag", this.remoteDeleteFlag));
		qparams.add(new BasicNameValuePair("downloadFlag", this.downloadFlag));
		qparams.add(new BasicNameValuePair("publicUseFlag", this.publicUseFlag));
		qparams.add(new BasicNameValuePair("alias", this.alias));
		qparams.add(new BasicNameValuePair("department", this.department));
		qparams.add(new BasicNameValuePair("employeeNo", this.employeeNo));
		qparams.add(new BasicNameValuePair("tel", this.tel));
		qparams.add(new BasicNameValuePair("mail", this.mail));
		qparams.add(new BasicNameValuePair("option1", this.option1));
		qparams.add(new BasicNameValuePair("option2", this.option2));
		qparams.add(new BasicNameValuePair("option3", this.option3));
		qparams.add(new BasicNameValuePair("option4", this.option4));
		qparams.add(new BasicNameValuePair("option5", this.option5));
		
		return qparams;
	}
	
	
}
