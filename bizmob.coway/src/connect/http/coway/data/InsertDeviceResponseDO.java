package connect.http.coway.data;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class InsertDeviceResponseDO {

	private String value = "";
	private String resultCode = "";
	private String messageCode = "";
	
	public InsertDeviceResponseDO(JsonNode json) {
		this.value = json.path("value").getTextValue();
		this.resultCode = json.path("resultCode").getTextValue();
		this.messageCode = json.path("messageCode").getTextValue();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

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
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	@Override
	public String toString() {
		return "InsertUserResponseDO [" + "value=" + value + ", resultCode=" + resultCode
				+ ", messageCode=" + messageCode + "]";
	}	
	
	
}
