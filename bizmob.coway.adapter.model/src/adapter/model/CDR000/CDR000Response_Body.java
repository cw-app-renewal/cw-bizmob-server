package adapter.model.CDR000;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDR000Response_Body {
	/**
	 * 더미
	 */
	private String dummy = "";

	public CDR000Response_Body() {
	}

	public CDR000Response_Body(JsonNode jsonNode) {
		this.dummy = jsonNode.path("dummy").getTextValue();
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}
}