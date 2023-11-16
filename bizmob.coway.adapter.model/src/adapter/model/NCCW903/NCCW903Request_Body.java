package adapter.model.NCCW903;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class NCCW903Request_Body {
	/**
	 * 살균진행내역 리스트
	 */
	private List<NCCW903Request_Body_I_ITAB> I_ITAB = new ArrayList<NCCW903Request_Body_I_ITAB>();

	public NCCW903Request_Body() {
	}

	public NCCW903Request_Body(JsonNode jsonNode) {
		this.I_ITAB = new ArrayList<NCCW903Request_Body_I_ITAB>();
		JsonNode I_ITABNode = jsonNode.path("I_ITAB");
		for (Iterator<JsonNode> iter = I_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			I_ITAB.add(new NCCW903Request_Body_I_ITAB(node));
		}
	}

	@JsonProperty("I_ITAB")
	public List<NCCW903Request_Body_I_ITAB> getI_ITAB() {
		return this.I_ITAB;
	}

	public void setI_ITAB(List<NCCW903Request_Body_I_ITAB> I_ITAB) {
		this.I_ITAB = I_ITAB;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW903Request_Body [");
		builder.append("I_ITAB=").append(I_ITAB);
		builder.append("]");
		return builder.toString();
	}
}