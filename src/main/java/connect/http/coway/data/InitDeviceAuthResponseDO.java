package connect.http.coway.data;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class InitDeviceAuthResponseDO {

	private String resultCode = "";
	private String messageCode = "";
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	@Override
	public String toString() {
		return "UpdateUserResponseDO [resultCode=" + resultCode
				+ ", messageCode=" + messageCode + "]";
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	
	public InitDeviceAuthResponseDO(JsonNode json) {
		this.resultCode = json.path("resultCode").getTextValue();
		this.messageCode = json.path("messageCode").getTextValue();
	}
}
