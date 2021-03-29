package adapter.model.CIS004;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CIS004Response_Body {
	/**
	* 스마트진단 적용 여부 (default false)
	*/
	private boolean smartDetectionYn = false;
	/**
	* 제품군 코드 (01 : 청정기, 02 : 정수기)
	*/
	private String productLineCode = "";
	/**
	* 와이파이 모듈 적용 여부 (default false) 
	*/
	private boolean wifiYn = false;
	/**
	* BLE 모듈 적용 여부 (default false)
	*/
	private boolean bleYn = false;
	/**
	* 제품군 텍스트 (SvcCode 기준)
	*/
	private String productLine = "";
	/**
	* i Trust 등록 여부 (default false)
	*/
	private boolean ioCareYn = false;
	/**
	* 자재코드
	*/
	private String materialCode = "";
	/**
	* POP 코드 / 시리얼 기준 제품코드
	*/
	private String productCode = "";

	public CIS004Response_Body() {
	}

	public CIS004Response_Body(JsonNode jsonNode) {
		this.smartDetectionYn = jsonNode.path("smartDetectionYn").getBooleanValue();
		this.productLineCode = jsonNode.path("productLineCode").getTextValue();
		this.wifiYn = jsonNode.path("wifiYn").getBooleanValue();
		this.bleYn = jsonNode.path("bleYn").getBooleanValue();
		this.productLine = jsonNode.path("productLine").getTextValue();
		this.ioCareYn = jsonNode.path("ioCareYn").getBooleanValue();
		this.materialCode = jsonNode.path("materialCode").getTextValue();
		this.productCode = jsonNode.path("productCode").getTextValue();
	}

	@JsonProperty("smartDetectionYn")
	public boolean getSmartDetectionYn() {
		return this.smartDetectionYn;
	}

	public void setSmartDetectionYn(boolean smartDetectionYn) {
		this.smartDetectionYn = smartDetectionYn;
	}

	@JsonProperty("productLineCode")
	public String getProductLineCode() {
		return this.productLineCode;
	}

	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}

	@JsonProperty("wifiYn")
	public boolean getWifiYn() {
		return this.wifiYn;
	}

	public void setWifiYn(boolean wifiYn) {
		this.wifiYn = wifiYn;
	}

	@JsonProperty("bleYn")
	public boolean getBleYn() {
		return this.bleYn;
	}

	public void setBleYn(boolean bleYn) {
		this.bleYn = bleYn;
	}

	@JsonProperty("productLine")
	public String getProductLine() {
		return this.productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	@JsonProperty("ioCareYn")
	public boolean getIoCareYn() {
		return this.ioCareYn;
	}

	public void setIoCareYn(boolean ioCareYn) {
		this.ioCareYn = ioCareYn;
	}

	@JsonProperty("materialCode")
	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	@JsonProperty("productCode")
	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS004Response_Body [");
		builder.append("smartDetectionYn=").append(smartDetectionYn);
		builder.append(", ");
		builder.append("productLineCode=").append(productLineCode);
		builder.append(", ");
		builder.append("wifiYn=").append(wifiYn);
		builder.append(", ");
		builder.append("bleYn=").append(bleYn);
		builder.append(", ");
		builder.append("productLine=").append(productLine);
		builder.append(", ");
		builder.append("ioCareYn=").append(ioCareYn);
		builder.append(", ");
		builder.append("materialCode=").append(materialCode);
		builder.append(", ");
		builder.append("productCode=").append(productCode);
		builder.append("]");
		return builder.toString();
	}
}