package adapter.model.sample.TrCode;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import adapter.model.header.DefaultHeader;

/**
 * 
 */
public class TrCodeRequest {
	/**
	 * header object
	 */
	private DefaultHeader header;
	/**
	 * body object
	 */
	private TrCodeRequest_Body body;

	public TrCodeRequest() {
	}

	public TrCodeRequest(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DefaultHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new TrCodeRequest_Body(bodyNode);
	}

	public DefaultHeader getHeader() {
		return this.header;
	}

	public void setHeader(DefaultHeader header) {
		this.header = header;
	}

	public TrCodeRequest_Body getBody() {
		return this.body;
	}

	public void setBody(TrCodeRequest_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}