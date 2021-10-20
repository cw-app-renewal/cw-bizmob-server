package adapter.model.CIS003;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 스마트진단항목유형
 */
public class CIS003Response_Body_itemTypes {
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;
	/**
	* 정렬순서
	*/
	private int sortNo = 0;
	/**
	* 스마트 진단 항목 유형 코드
	*/
	private String itemTypeCode = "";
	/**
	* 스마트진단 항목유형 > 항목 및 가이드 문구 [참조] items
	*/
	private List<CIS003Response_Body_itemTypes_items> items = new ArrayList<CIS003Response_Body_itemTypes_items>();
	/**
	* 스마트 진단 항목 유형 명 1 depth
	*/
	private String itemTypeName = "";

	public CIS003Response_Body_itemTypes() {
	}

	public CIS003Response_Body_itemTypes(JsonNode jsonNode) {
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
		this.sortNo = jsonNode.path("sortNo").getIntValue();
		this.itemTypeCode = jsonNode.path("itemTypeCode").getTextValue();
		this.items = new ArrayList<CIS003Response_Body_itemTypes_items>();
		JsonNode itemsNode = jsonNode.path("items");
		for (Iterator<JsonNode> iter = itemsNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			items.add(new CIS003Response_Body_itemTypes_items(node));
		}
		this.itemTypeName = jsonNode.path("itemTypeName").getTextValue();
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	@JsonProperty("sortNo")
	public int getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@JsonProperty("itemTypeCode")
	public String getItemTypeCode() {
		return this.itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	@JsonProperty("items")
	public List<CIS003Response_Body_itemTypes_items> getItems() {
		return this.items;
	}

	public void setItems(List<CIS003Response_Body_itemTypes_items> items) {
		this.items = items;
	}

	@JsonProperty("itemTypeName")
	public String getItemTypeName() {
		return this.itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS003Response_Body_itemTypes [");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append(", ");
		builder.append("sortNo=").append(sortNo);
		builder.append(", ");
		builder.append("itemTypeCode=").append(itemTypeCode);
		builder.append(", ");
		builder.append("items=").append(items);
		builder.append(", ");
		builder.append("itemTypeName=").append(itemTypeName);
		builder.append("]");
		return builder.toString();
	}
}