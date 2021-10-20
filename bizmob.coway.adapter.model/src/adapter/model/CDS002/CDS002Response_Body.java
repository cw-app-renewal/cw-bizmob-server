package adapter.model.CDS002;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDS002Response_Body {
	/**
	 * 전체 데이터 동기화 여부(Y:전체 데이터 동기화 WD_BOM.db 파일을 내려받는다)
	 */
	private String FULL_SYNC;
	/**
	 * LIST
	 */
	private List<CDS002Response_Body_LIST> LIST;

	public CDS002Response_Body() {
	}

	public CDS002Response_Body(JsonNode jsonNode) {
		this.FULL_SYNC = jsonNode.path("FULL_SYNC").getTextValue();
		this.LIST = new ArrayList<CDS002Response_Body_LIST>();
		JsonNode LISTNode = jsonNode.path("LIST");
		for (Iterator<JsonNode> iter = LISTNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			LIST.add(new CDS002Response_Body_LIST(node));
		}
	}

	public String getFULL_SYNC() {
		return this.FULL_SYNC;
	}

	public void setFULL_SYNC(String FULL_SYNC) {
		this.FULL_SYNC = FULL_SYNC;
	}

	public List<CDS002Response_Body_LIST> getLIST() {
		return this.LIST;
	}

	public void setLIST(List<CDS002Response_Body_LIST> LIST) {
		this.LIST = LIST;
	}
}