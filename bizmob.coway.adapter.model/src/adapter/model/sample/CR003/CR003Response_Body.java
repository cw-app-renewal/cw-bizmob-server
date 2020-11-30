package adapter.model.sample.CR003;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CR003Response_Body {
	/**
	 * O_ITAB
	 */
	private List<CR003Response_Body_O_ITAB> O_ITAB;

	public CR003Response_Body() {
	}

	public CR003Response_Body(JsonNode jsonNode) {
		this.O_ITAB = new ArrayList<CR003Response_Body_O_ITAB>();
		JsonNode O_ITABNode = jsonNode.path("O_ITAB");
		for (Iterator<JsonNode> iter = O_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB.add(new CR003Response_Body_O_ITAB(node));
		}
	}

	public List<CR003Response_Body_O_ITAB> getO_ITAB() {
		return this.O_ITAB;
	}

	public void setO_ITAB(List<CR003Response_Body_O_ITAB> O_ITAB) {
		this.O_ITAB = O_ITAB;
	}
}