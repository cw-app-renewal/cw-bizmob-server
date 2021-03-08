package adapter.model.CISM0003;


import java.util.List;
import java.util.Iterator;
import org.codehaus.jackson.JsonNode;
import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CISM0003Response_Body {
	/**
	* 제품 시리얼 번호
	*/
	private String serial = "";
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;
	/**
	* 데이터 발생시간(KST)
	*/
	private String occDt = "";
	/**
	* 스마트 진단 요청 어플리케이션 구분 (01 : 제품, 02 : IoCare 앱, 10 : 서비스매니저 앱, 11 : CS 관제 웹)
	*/
	private String appTypeCode = "";
	/**
	* 요청 ID 스마트 진단 결과 상세 조회 시 사용하는 키 값
	*/
	private String requestId = "";
	/**
	* 스마트진단항목유형
	*/
	private List<CISM0003Response_Body_itemTypes> itemTypes = new ArrayList<CISM0003Response_Body_itemTypes>();
	/**
	* 스마트진단 구분 (A108F : 상시진단결과, A109F : 정밀진단결과)
	*/
	private String apiNo = "";
	/**
	* UTC Timestamp 값
	*/
	private String creationDt = "";

	public CISM0003Response_Body() {
	}

	public CISM0003Response_Body(JsonNode jsonNode) {
		this.serial = jsonNode.path("serial").getTextValue();
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
		this.occDt = jsonNode.path("occDt").getTextValue();
		this.appTypeCode = jsonNode.path("appTypeCode").getTextValue();
		this.requestId = jsonNode.path("requestId").getTextValue();
		this.itemTypes = new ArrayList<CISM0003Response_Body_itemTypes>();
		JsonNode itemTypesNode = jsonNode.path("itemTypes");
		for (Iterator<JsonNode> iter = itemTypesNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			itemTypes.add(new CISM0003Response_Body_itemTypes(node));
		}
		this.apiNo = jsonNode.path("apiNo").getTextValue();
		this.creationDt = jsonNode.path("creationDt").getTextValue();
	}

	@JsonProperty("serial")
	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	@JsonProperty("occDt")
	public String getOccDt() {
		return this.occDt;
	}

	public void setOccDt(String occDt) {
		this.occDt = occDt;
	}

	@JsonProperty("appTypeCode")
	public String getAppTypeCode() {
		return this.appTypeCode;
	}

	public void setAppTypeCode(String appTypeCode) {
		this.appTypeCode = appTypeCode;
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@JsonProperty("itemTypes")
	public List<CISM0003Response_Body_itemTypes> getItemTypes() {
		return this.itemTypes;
	}

	public void setItemTypes(List<CISM0003Response_Body_itemTypes> itemTypes) {
		this.itemTypes = itemTypes;
	}

	@JsonProperty("apiNo")
	public String getApiNo() {
		return this.apiNo;
	}

	public void setApiNo(String apiNo) {
		this.apiNo = apiNo;
	}

	@JsonProperty("creationDt")
	public String getCreationDt() {
		return this.creationDt;
	}

	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0003Response_Body [");
		builder.append("serial=").append(serial);
		builder.append(", ");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append(", ");
		builder.append("occDt=").append(occDt);
		builder.append(", ");
		builder.append("appTypeCode=").append(appTypeCode);
		builder.append(", ");
		builder.append("requestId=").append(requestId);
		builder.append(", ");
		builder.append("itemTypes=").append(itemTypes);
		builder.append(", ");
		builder.append("apiNo=").append(apiNo);
		builder.append(", ");
		builder.append("creationDt=").append(creationDt);
		builder.append("]");
		return builder.toString();
	}
}