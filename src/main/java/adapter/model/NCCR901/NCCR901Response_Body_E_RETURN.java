package adapter.model.NCCR901;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 결과리스트
 */
public class NCCR901Response_Body_E_RETURN {
	/**
	 * 메세지
	 */
	private String MESSGAE = "";
	/**
	 * 결과값 ( T : true, F : false )
	 */
	private String TYPE = "";

	public NCCR901Response_Body_E_RETURN() {
	}

	public NCCR901Response_Body_E_RETURN(JsonNode jsonNode) {
		this.MESSGAE = jsonNode.path("MESSGAE").getTextValue();
		this.TYPE = jsonNode.path("TYPE").getTextValue();
	}

	@JsonProperty("MESSGAE")
	public String getMESSGAE() {
		return this.MESSGAE;
	}

	public void setMESSGAE(String MESSGAE) {
		this.MESSGAE = MESSGAE;
	}

	@JsonProperty("TYPE")
	public String getTYPE() {
		return this.TYPE;
	}

	public void setTYPE(String TYPE) {
		this.TYPE = TYPE;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCCR901Response_Body_E_RETURN [");
		builder.append("MESSGAE=").append(MESSGAE);
		builder.append(", ");
		builder.append("TYPE=").append(TYPE);
		builder.append("]");
		return builder.toString();
	}
}