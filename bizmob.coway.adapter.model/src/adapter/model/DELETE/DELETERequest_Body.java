package adapter.model.DELETE;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Comment
 */
public class DELETERequest_Body {
	/**
	 * img_list
	 */
	private List<DELETERequest_Body_img_list> I_ITAB;
	
	private String I_PROC_ID;

	public DELETERequest_Body() {
	}

	public DELETERequest_Body(JsonNode jsonNode) {
		this.I_ITAB = new ArrayList<DELETERequest_Body_img_list>();
		JsonNode img_listNode = jsonNode.path("I_ITAB");
		for (Iterator<JsonNode> iter = img_listNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			I_ITAB.add(new DELETERequest_Body_img_list(node));
		}
		this.I_PROC_ID = jsonNode.path("I_PROC_ID").getTextValue();
	}

	public List<DELETERequest_Body_img_list> getImg_list() {
		return this.I_ITAB;
	}

	public void setImg_list(List<DELETERequest_Body_img_list> img_list) {
		this.I_ITAB = img_list;
	}
	
	public String getI_PROC_ID() {
		return I_PROC_ID;
	}

	public void setI_PROC_ID(String i_PROC_ID) {
		I_PROC_ID = i_PROC_ID;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	@Override
	public String toString() {
		return "DELETERequest_Body [I_ITAB=" + I_ITAB + ", I_PROC_ID="
				+ I_PROC_ID + "]";
	}
	
	
}