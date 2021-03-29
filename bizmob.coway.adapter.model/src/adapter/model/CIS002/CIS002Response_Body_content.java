package adapter.model.CIS002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 스마트진단결과 이력목록
 */
public class CIS002Response_Body_content {
	/**
	* 스마트 진단 요청 어플리케이션 구분 (01 : 제품, 02 : IoCare 앱, 10 : 서비스매니저 앱, 11 : CS 관제 웹)
	*/
	private String appTypeCode = "";
	/**
	* 데이터 발생시간(KST)
	*/
	private String occDt = "";
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";
	/**
	* 페이징키에 사용할 페이징 처리 키값
	*/
	private String creationDt = "";
	/**
	* 스마트진단 구분 (A108F : 상시진단결과, A109F : 정밀진단결과)
	*/
	private String apiNo = "";
	/**
	* 스마트 진단결과 상세 조회시 사용하는 키값
	*/
	private String requestId = "";

	public CIS002Response_Body_content() {
	}

	public CIS002Response_Body_content(JsonNode jsonNode) {
		this.appTypeCode = jsonNode.path("appTypeCode").getTextValue();
		this.occDt = jsonNode.path("occDt").getTextValue();
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
		this.serial = jsonNode.path("serial").getTextValue();
		this.creationDt = jsonNode.path("creationDt").getTextValue();
		this.apiNo = jsonNode.path("apiNo").getTextValue();
		this.requestId = jsonNode.path("requestId").getTextValue();
	}

	@JsonProperty("appTypeCode")
	public String getAppTypeCode() {
		return this.appTypeCode;
	}

	public void setAppTypeCode(String appTypeCode) {
		this.appTypeCode = appTypeCode;
	}

	@JsonProperty("occDt")
	public String getOccDt() {
		return this.occDt;
	}

	public void setOccDt(String occDt) {
		this.occDt = occDt;
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	@JsonProperty("serial")
	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@JsonProperty("creationDt")
	public String getCreationDt() {
		return this.creationDt;
	}

	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}

	@JsonProperty("apiNo")
	public String getApiNo() {
		return this.apiNo;
	}

	public void setApiNo(String apiNo) {
		this.apiNo = apiNo;
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS002Response_Body_content [");
		builder.append("appTypeCode=").append(appTypeCode);
		builder.append(", ");
		builder.append("occDt=").append(occDt);
		builder.append(", ");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append(", ");
		builder.append("serial=").append(serial);
		builder.append(", ");
		builder.append("creationDt=").append(creationDt);
		builder.append(", ");
		builder.append("apiNo=").append(apiNo);
		builder.append(", ");
		builder.append("requestId=").append(requestId);
		builder.append("]");
		return builder.toString();
	}
}