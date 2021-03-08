package adapter.model.CISM0003;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 스마트진단 항목유형 > 항목 및 가이드 문구 [참조] items
 */
public class CISM0003Response_Body_itemTypes_items {
	/**
	* 점검항목 텍스트
	*/
	private String checkList = "";
	/**
	* 연관부품 텍스트
	*/
	private String relateComponent = "";
	/**
	* item id
	*/
	private String itemId = "";
	/**
	* 서비스매니저앱 미사용
	*/
	private String symptoms = "";
	/**
	* 스마트 진단 항목명 2 depth
	*/
	private String itemName = "";
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;
	/**
	* 서비스매니저앱 미사용
	*/
	private String actItem = "";

	public CISM0003Response_Body_itemTypes_items() {
	}

	public CISM0003Response_Body_itemTypes_items(JsonNode jsonNode) {
		this.checkList = jsonNode.path("checkList").getTextValue();
		this.relateComponent = jsonNode.path("relateComponent").getTextValue();
		this.itemId = jsonNode.path("itemId").getTextValue();
		this.symptoms = jsonNode.path("symptoms").getTextValue();
		this.itemName = jsonNode.path("itemName").getTextValue();
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
		this.actItem = jsonNode.path("actItem").getTextValue();
	}

	@JsonProperty("checkList")
	public String getCheckList() {
		return this.checkList;
	}

	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}

	@JsonProperty("relateComponent")
	public String getRelateComponent() {
		return this.relateComponent;
	}

	public void setRelateComponent(String relateComponent) {
		this.relateComponent = relateComponent;
	}

	@JsonProperty("itemId")
	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@JsonProperty("symptoms")
	public String getSymptoms() {
		return this.symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	@JsonProperty("itemName")
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	@JsonProperty("actItem")
	public String getActItem() {
		return this.actItem;
	}

	public void setActItem(String actItem) {
		this.actItem = actItem;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0003Response_Body_itemTypes_items [");
		builder.append("checkList=").append(checkList);
		builder.append(", ");
		builder.append("relateComponent=").append(relateComponent);
		builder.append(", ");
		builder.append("itemId=").append(itemId);
		builder.append(", ");
		builder.append("symptoms=").append(symptoms);
		builder.append(", ");
		builder.append("itemName=").append(itemName);
		builder.append(", ");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append(", ");
		builder.append("actItem=").append(actItem);
		builder.append("]");
		return builder.toString();
	}
}