package adapter.model.CDS002;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDS002Request_Body {
	/**
	 * 최종 동기화 시간
	 */
	private String SYNC_TIME;
	/**
	 * 최종 동기화 일자
	 */
	private String SYNC_DATE;

	public CDS002Request_Body() {
	}

	public CDS002Request_Body(JsonNode jsonNode) {
		this.SYNC_TIME = jsonNode.path("SYNC_TIME").getTextValue();
		this.SYNC_DATE = jsonNode.path("SYNC_DATE").getTextValue();
	}

	public String getSYNC_TIME() {
		return this.SYNC_TIME;
	}

	public void setSYNC_TIME(String SYNC_TIME) {
		this.SYNC_TIME = SYNC_TIME;
	}

	public String getSYNC_DATE() {
		return this.SYNC_DATE;
	}

	public void setSYNC_DATE(String SYNC_DATE) {
		this.SYNC_DATE = SYNC_DATE;
	}
}