package adapter.model.sample.TrCode;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class TrCodeRequest_Body {

	private String data;

	public TrCodeRequest_Body() {
	}

	public TrCodeRequest_Body(JsonNode jsonNode) {
		this.data = jsonNode.path("data").getTextValue();
	}


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}