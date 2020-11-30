package adapter.model.sample.WR002;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class WR002Request_Body {
	/**
	 * 설문시작일자
	 */
	private String I_START_DT;
	/**
	 * 처리자 사번
	 */
	private String I_PROC_ID;
	/**
	 * I_ITAB
	 */
	private List<WR002Request_Body_I_ITAB> I_ITAB;

	public WR002Request_Body() {
	}

	public WR002Request_Body(JsonNode jsonNode) {
		this.I_START_DT = jsonNode.path("I_START_DT").getTextValue();
		this.I_PROC_ID = jsonNode.path("I_PROC_ID").getTextValue();
		this.I_ITAB = new ArrayList<WR002Request_Body_I_ITAB>();
		JsonNode I_ITABNode = jsonNode.path("I_ITAB");
		for (Iterator<JsonNode> iter = I_ITABNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			I_ITAB.add(new WR002Request_Body_I_ITAB(node));
		}
	}

	public String getI_START_DT() {
		return this.I_START_DT;
	}

	public void setI_START_DT(String I_START_DT) {
		this.I_START_DT = I_START_DT;
	}

	public String getI_PROC_ID() {
		return this.I_PROC_ID;
	}

	public void setI_PROC_ID(String I_PROC_ID) {
		this.I_PROC_ID = I_PROC_ID;
	}

	public List<WR002Request_Body_I_ITAB> getI_ITAB() {
		return this.I_ITAB;
	}

	public void setI_ITAB(List<WR002Request_Body_I_ITAB> I_ITAB) {
		this.I_ITAB = I_ITAB;
	}
}