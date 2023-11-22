package adapter.model.CDS000;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDS000Request_Body {
	/**
	 * 마지막 동기화 일자
	 */
	private String SYNC_DATE;
	/**
	 * 마지막 동기화 시간
	 */
	private String SYNC_TIME;

	public CDS000Request_Body() {
	}

	public CDS000Request_Body(JsonNode jsonNode) {
		this.SYNC_DATE = jsonNode.path("SYNC_DATE").getTextValue();
		this.SYNC_TIME = jsonNode.path("SYNC_TIME").getTextValue();
	}

	public String getSYNC_DATE() {
		return this.SYNC_DATE;
	}

	public void setSYNC_DATE(String SYNC_DATE) {
		this.SYNC_DATE = SYNC_DATE;
	}

	public String getSYNC_TIME() {
		return this.SYNC_TIME;
	}

	public void setSYNC_TIME(String SYNC_TIME) {
		this.SYNC_TIME = SYNC_TIME;
	}
}