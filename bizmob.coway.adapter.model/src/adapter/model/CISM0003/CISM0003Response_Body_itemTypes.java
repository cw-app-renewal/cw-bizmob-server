package adapter.model.CISM0003;


import java.util.List;
import java.util.Iterator;
import org.codehaus.jackson.JsonNode;
import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 스마트진단항목유형
 */
public class CISM0003Response_Body_itemTypes {
	/**
	* 정상동작 여부 (true : 정상, false : 이상감지)
	*/
	private boolean normalOprYn = false;
	/**
	* 스마트 진단 항목 유형 명 1 depth
	*/
	private String itemTypeName = "";
	/**
	* 정렬순서
	*/
	private int sortNo = 0;
	/**
	* 스마트진단 항목유형 > 항목 및 가이드 문구 [참조] items
	*/
	private List<CISM0003Response_Body_itemTypes_items> items = new ArrayList<CISM0003Response_Body_itemTypes_items>();
	/**
	* 스마트 진단 항목 유형 코드
	*/
	private String itemTypeCode = "";

	public CISM0003Response_Body_itemTypes() {
	}

	public CISM0003Response_Body_itemTypes(JsonNode jsonNode) {
		this.normalOprYn = jsonNode.path("normalOprYn").getBooleanValue();
		this.itemTypeName = jsonNode.path("itemTypeName").getTextValue();
		this.sortNo = jsonNode.path("sortNo").getIntValue();
		this.items = new ArrayList<CISM0003Response_Body_itemTypes_items>();
		JsonNode itemsNode = jsonNode.path("items");
		for (Iterator<JsonNode> iter = itemsNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			items.add(new CISM0003Response_Body_itemTypes_items(node));
		}
		this.itemTypeCode = jsonNode.path("itemTypeCode").getTextValue();
	}

	@JsonProperty("normalOprYn")
	public boolean getNormalOprYn() {
		return this.normalOprYn;
	}

	public void setNormalOprYn(boolean normalOprYn) {
		this.normalOprYn = normalOprYn;
	}

	@JsonProperty("itemTypeName")
	public String getItemTypeName() {
		return this.itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	@JsonProperty("sortNo")
	public int getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@JsonProperty("items")
	public List<CISM0003Response_Body_itemTypes_items> getItems() {
		return this.items;
	}

	public void setItems(List<CISM0003Response_Body_itemTypes_items> items) {
		this.items = items;
	}

	@JsonProperty("itemTypeCode")
	public String getItemTypeCode() {
		return this.itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISM0003Response_Body_itemTypes [");
		builder.append("normalOprYn=").append(normalOprYn);
		builder.append(", ");
		builder.append("itemTypeName=").append(itemTypeName);
		builder.append(", ");
		builder.append("sortNo=").append(sortNo);
		builder.append(", ");
		builder.append("items=").append(items);
		builder.append(", ");
		builder.append("itemTypeCode=").append(itemTypeCode);
		builder.append("]");
		return builder.toString();
	}
}