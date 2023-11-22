package adapter.model.sample.TrCode;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class TrCodeResponse_Body {
	/**
	 * result
	 */
	private String result;

	public TrCodeResponse_Body() {
	}

	public TrCodeResponse_Body(JsonNode jsonNode) {
		this.result = jsonNode.path("result").getTextValue();
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}