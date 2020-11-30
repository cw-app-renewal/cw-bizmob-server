package adapter.model.sample.RD044;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class RD044Response_Body {
	/**
	 * O_ITAB
	 */
	private List<RD044Response_Body_O_ITAB> O_ITAB;

	public RD044Response_Body() {
	}

	public RD044Response_Body(JsonNode jsonNode) {
		this.O_ITAB = new ArrayList<RD044Response_Body_O_ITAB>();
		JsonNode O_ITABNode = jsonNode.path("O_ITAB");
		for (Iterator<JsonNode> iter = O_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB.add(new RD044Response_Body_O_ITAB(node));
		}
	}

	public List<RD044Response_Body_O_ITAB> getO_ITAB() {
		return this.O_ITAB;
	}

	public void setO_ITAB(List<RD044Response_Body_O_ITAB> O_ITAB) {
		this.O_ITAB = O_ITAB;
	}
}