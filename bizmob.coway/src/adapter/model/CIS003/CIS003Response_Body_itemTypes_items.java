package adapter.model.CIS003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 스마트진단 항목유형 > 항목 및 가이드 문구 [참조] items
 */
public class CIS003Response_Body_itemTypes_items {
	/**
	* 연관부품 텍스트
	*/
	private String relateComponent = "";
	/**
	* 스마트 진단 항목명 2 depth
	*/
	private String itemName = "";
	/**
	* item id
	*/
	private String itemId = "";
	/**
	* 점검항목 텍스트
	*/
	private String checkList = "";
	/**
	* 서비스매니저앱 미사용
	*/
	private String actItem = "";
	/**
	* 서비스매니저앱 미사용
	*/
	private String symptoms = "";
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;

	public CIS003Response_Body_itemTypes_items() {
	}

	public CIS003Response_Body_itemTypes_items(JsonNode jsonNode) {
		this.relateComponent = jsonNode.path("relateComponent").getTextValue();
		this.itemName = jsonNode.path("itemName").getTextValue();
		this.itemId = jsonNode.path("itemId").getTextValue();
		this.checkList = jsonNode.path("checkList").getTextValue();
		this.actItem = jsonNode.path("actItem").getTextValue();
		this.symptoms = jsonNode.path("symptoms").getTextValue();
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
	}

	@JsonProperty("relateComponent")
	public String getRelateComponent() {
		return this.relateComponent;
	}

	public void setRelateComponent(String relateComponent) {
		this.relateComponent = relateComponent;
	}

	@JsonProperty("itemName")
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@JsonProperty("itemId")
	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@JsonProperty("checkList")
	public String getCheckList() {
		return this.checkList;
	}

	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}

	@JsonProperty("actItem")
	public String getActItem() {
		return this.actItem;
	}

	public void setActItem(String actItem) {
		this.actItem = actItem;
	}

	@JsonProperty("symptoms")
	public String getSymptoms() {
		return this.symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS003Response_Body_itemTypes_items [");
		builder.append("relateComponent=").append(relateComponent);
		builder.append(", ");
		builder.append("itemName=").append(itemName);
		builder.append(", ");
		builder.append("itemId=").append(itemId);
		builder.append(", ");
		builder.append("checkList=").append(checkList);
		builder.append(", ");
		builder.append("actItem=").append(actItem);
		builder.append(", ");
		builder.append("symptoms=").append(symptoms);
		builder.append(", ");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append("]");
		return builder.toString();
	}
}