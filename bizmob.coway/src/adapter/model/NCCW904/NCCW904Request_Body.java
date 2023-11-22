package adapter.model.NCCW904;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Comment
 */
public class NCCW904Request_Body {
	/**
	 * 오류내용 리스트
	 */
	private List<NCCW904Request_Body_I_ITAB> I_ITAB = new ArrayList<NCCW904Request_Body_I_ITAB>();

	public NCCW904Request_Body() {
	}

	public NCCW904Request_Body(JsonNode jsonNode) {
		this.I_ITAB = new ArrayList<NCCW904Request_Body_I_ITAB>();
		JsonNode I_ITABNode = jsonNode.path("I_ITAB");
		for (Iterator<JsonNode> iter = I_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			I_ITAB.add(new NCCW904Request_Body_I_ITAB(node));
		}
	}

	@JsonProperty("I_ITAB")
	public List<NCCW904Request_Body_I_ITAB> getI_ITAB() {
		return this.I_ITAB;
	}

	public void setI_ITAB(List<NCCW904Request_Body_I_ITAB> I_ITAB) {
		this.I_ITAB = I_ITAB;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCW904Request_Body [");
		builder.append("I_ITAB=").append(I_ITAB);
		builder.append("]");
		return builder.toString();
	}
}