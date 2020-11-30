package adapter.model.DELETE;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class DELETEResponse_Body {
	/**
	 * 결과
	 */
	private boolean result;

	public DELETEResponse_Body() {
	}

	public DELETEResponse_Body(JsonNode jsonNode) {
		this.result = jsonNode.path("result").getBooleanValue();
	}

	public boolean getResult() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}