package adapter.model.ERR002;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class ERR002Request_Body {
	/**
	 * 사용자번호
	 */
	private String user_id = "";
	/**
	 * 종료 목록
	 */
	private List<ERR002Request_Body_crushList> crushList = new ArrayList<ERR002Request_Body_crushList>();

	public ERR002Request_Body() {
	}

	public ERR002Request_Body(JsonNode jsonNode) {
		this.user_id = jsonNode.path("user_id").getTextValue();
		this.crushList = new ArrayList<ERR002Request_Body_crushList>();
		JsonNode crushListNode = jsonNode.path("crushList");
		for (Iterator<JsonNode> iter = crushListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			crushList.add(new ERR002Request_Body_crushList(node));
		}
	}

	@JsonProperty("user_id")
	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@JsonProperty("crushList")
	public List<ERR002Request_Body_crushList> getCrushList() {
		return this.crushList;
	}

	public void setCrushList(List<ERR002Request_Body_crushList> crushList) {
		this.crushList = crushList;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ERR002Request_Body [");
		builder.append("user_id=").append(user_id);
		builder.append(", ");
		builder.append("crushList=").append(crushList);
		builder.append("]");
		return builder.toString();
	}
}