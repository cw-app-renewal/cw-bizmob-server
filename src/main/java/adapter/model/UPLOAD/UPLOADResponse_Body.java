package adapter.model.UPLOAD;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class UPLOADResponse_Body {
	/**
	 * 결과
	 */
	private boolean result;

	public UPLOADResponse_Body() {
	}

	public UPLOADResponse_Body(JsonNode jsonNode) {
		this.result = jsonNode.path("result").getBooleanValue();
	}

	public boolean getResult() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}