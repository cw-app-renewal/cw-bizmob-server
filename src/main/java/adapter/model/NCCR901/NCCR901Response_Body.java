package adapter.model.NCCR901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Comment
 */
public class NCCR901Response_Body {
	/**
	 * 결과리스트
	 */
	private List<NCCR901Response_Body_E_RETURN> E_RETURN = new ArrayList<NCCR901Response_Body_E_RETURN>();
	/**
	 * 월음용량
	 */
	private List<NCCR901Response_Body_O_ITAB> O_ITAB = new ArrayList<NCCR901Response_Body_O_ITAB>();

	public NCCR901Response_Body() {
	}

	public NCCR901Response_Body(JsonNode jsonNode) {
		this.E_RETURN = new ArrayList<NCCR901Response_Body_E_RETURN>();
		JsonNode E_RETURNNode = jsonNode.path("E_RETURN");
		for (Iterator<JsonNode> iter = E_RETURNNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			E_RETURN.add(new NCCR901Response_Body_E_RETURN(node));
		}
		this.O_ITAB = new ArrayList<NCCR901Response_Body_O_ITAB>();
		JsonNode O_ITABNode = jsonNode.path("O_ITAB");
		for (Iterator<JsonNode> iter = O_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB.add(new NCCR901Response_Body_O_ITAB(node));
		}
	}

	@JsonProperty("E_RETURN")
	public List<NCCR901Response_Body_E_RETURN> getE_RETURN() {
		return this.E_RETURN;
	}

	public void setE_RETURN(List<NCCR901Response_Body_E_RETURN> E_RETURN) {
		this.E_RETURN = E_RETURN;
	}

	@JsonProperty("O_ITAB")
	public List<NCCR901Response_Body_O_ITAB> getO_ITAB() {
		return this.O_ITAB;
	}

	public void setO_ITAB(List<NCCR901Response_Body_O_ITAB> O_ITAB) {
		this.O_ITAB = O_ITAB;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR901Response_Body [");
		builder.append("E_RETURN=").append(E_RETURN);
		builder.append(", ");
		builder.append("O_ITAB=").append(O_ITAB);
		builder.append("]");
		return builder.toString();
	}
}