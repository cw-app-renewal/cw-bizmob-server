package adapter.model.CGR000;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Comment
 */
public class CGR000Request_Body {
	/**
	 * 디바이스토큰(APNS)
	 */
	private String I_DEVICE_TOKEN;
	/**
	 * 장비일련번호
	 */
	private String I_SERIAL_NO;
	/**
	 * 등록번호
	 */
	private String I_PERNR;
	/**
	 * 전화번호
	 */
	private String I_PHN_NO;
	/**
	 * 비밀번호
	 */
	private String I_PWD;

	public CGR000Request_Body() {
	}

	public CGR000Request_Body(JsonNode jsonNode) {
		this.I_DEVICE_TOKEN = jsonNode.path("I_DEVICE_TOKEN").getTextValue();
		this.I_SERIAL_NO = jsonNode.path("I_SERIAL_NO").getTextValue();
		this.I_PERNR = jsonNode.path("I_PERNR").getTextValue();
		this.I_PHN_NO = jsonNode.path("I_PHN_NO").getTextValue();
		this.I_PWD = jsonNode.path("I_PWD").getTextValue();
	}

	public String getI_DEVICE_TOKEN() {
		return this.I_DEVICE_TOKEN;
	}

	public void setI_DEVICE_TOKEN(String I_DEVICE_TOKEN) {
		this.I_DEVICE_TOKEN = I_DEVICE_TOKEN;
	}

	public String getI_SERIAL_NO() {
		return this.I_SERIAL_NO;
	}

	public void setI_SERIAL_NO(String I_SERIAL_NO) {
		this.I_SERIAL_NO = I_SERIAL_NO;
	}

	public String getI_PERNR() {
		return this.I_PERNR;
	}

	public void setI_PERNR(String I_PERNR) {
		this.I_PERNR = I_PERNR;
	}

	public String getI_PHN_NO() {
		return this.I_PHN_NO;
	}

	public void setI_PHN_NO(String I_PHN_NO) {
		this.I_PHN_NO = I_PHN_NO;
	}

	public String getI_PWD() {
		return this.I_PWD;
	}

	public void setI_PWD(String I_PWD) {
		this.I_PWD = I_PWD;
	}

	@Override
	public String toString() {
		return "CGR000Request_Body [I_DEVICE_TOKEN=" + I_DEVICE_TOKEN
				+ ", I_SERIAL_NO=" + I_SERIAL_NO + ", I_PERNR=" + I_PERNR
				+ ", I_PHN_NO=" + I_PHN_NO + ", I_PWD=" + I_PWD + "]";
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
}