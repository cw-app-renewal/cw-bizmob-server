package adapter.model.ERR002;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 종료 목록
 */
public class ERR002Request_Body_crushList {
	/**
	 * zem128을 호출한 누적횟수
	 */
	private String zem128_cnt = "";
	/**
	 * 바코드 상태(NONE, ACTIVITY_START, ACTIVITY_FINISH, ACTIVITY_CRASH, READ_ZXING, READ_ZEM128, READ_ZEMQR)
	 */
	private String barcode_status = "";
	/**
	 * zxing을 호출한 횟수
	 */
	private String zxing_cnt = "";
	/**
	 * 생성일자
	 */
	private String inserted_date = "";
	/**
	 * zemQR에서 성공한 누적횟수
	 */
	private String zemqr_success_cnt = "";
	/**
	 * 마지막으로 수행된 command id
	 */
	private String last_cmd_id = "";
	/**
	 * user의 기기정보(제조사+기기모델명)
	 */
	private String device_name = "";
	/**
	 * zemQR을 호출한 누적횟수
	 */
	private String zemqr_cnt = "";
	/**
	 * zem128에서 성공한 누적횟수
	 */
	private String zem128_success_cnt = "";
	/**
	 * crash 여부(Y, N)
	 */
	private String crash_yn = "";
	/**
	 * zxing에서 성공한 누적횟수
	 */
	private String zxing_success_cnt = "";

	public ERR002Request_Body_crushList() {
	}

	public ERR002Request_Body_crushList(JsonNode jsonNode) {
		this.zem128_cnt = jsonNode.path("zem128_cnt").getTextValue();
		this.barcode_status = jsonNode.path("barcode_status").getTextValue();
		this.zxing_cnt = jsonNode.path("zxing_cnt").getTextValue();
		this.inserted_date = jsonNode.path("inserted_date").getTextValue();
		this.zemqr_success_cnt = jsonNode.path("zemqr_success_cnt")
				.getTextValue();
		this.last_cmd_id = jsonNode.path("last_cmd_id").getTextValue();
		this.device_name = jsonNode.path("device_name").getTextValue();
		this.zemqr_cnt = jsonNode.path("zemqr_cnt").getTextValue();
		this.zem128_success_cnt = jsonNode.path("zem128_success_cnt")
				.getTextValue();
		this.crash_yn = jsonNode.path("crash_yn").getTextValue();
		this.zxing_success_cnt = jsonNode.path("zxing_success_cnt")
				.getTextValue();
	}

	@JsonProperty("zem128_cnt")
	public String getZem128_cnt() {
		return this.zem128_cnt;
	}

	public void setZem128_cnt(String zem128_cnt) {
		this.zem128_cnt = zem128_cnt;
	}

	@JsonProperty("barcode_status")
	public String getBarcode_status() {
		return this.barcode_status;
	}

	public void setBarcode_status(String barcode_status) {
		this.barcode_status = barcode_status;
	}

	@JsonProperty("zxing_cnt")
	public String getZxing_cnt() {
		return this.zxing_cnt;
	}

	public void setZxing_cnt(String zxing_cnt) {
		this.zxing_cnt = zxing_cnt;
	}

	@JsonProperty("inserted_date")
	public String getInserted_date() {
		return this.inserted_date;
	}

	public void setInserted_date(String inserted_date) {
		this.inserted_date = inserted_date;
	}

	@JsonProperty("zemqr_success_cnt")
	public String getZemqr_success_cnt() {
		return this.zemqr_success_cnt;
	}

	public void setZemqr_success_cnt(String zemqr_success_cnt) {
		this.zemqr_success_cnt = zemqr_success_cnt;
	}

	@JsonProperty("last_cmd_id")
	public String getLast_cmd_id() {
		return this.last_cmd_id;
	}

	public void setLast_cmd_id(String last_cmd_id) {
		this.last_cmd_id = last_cmd_id;
	}

	@JsonProperty("device_name")
	public String getDevice_name() {
		return this.device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	@JsonProperty("zemqr_cnt")
	public String getZemqr_cnt() {
		return this.zemqr_cnt;
	}

	public void setZemqr_cnt(String zemqr_cnt) {
		this.zemqr_cnt = zemqr_cnt;
	}

	@JsonProperty("zem128_success_cnt")
	public String getZem128_success_cnt() {
		return this.zem128_success_cnt;
	}

	public void setZem128_success_cnt(String zem128_success_cnt) {
		this.zem128_success_cnt = zem128_success_cnt;
	}

	@JsonProperty("crash_yn")
	public String getCrash_yn() {
		return this.crash_yn;
	}

	public void setCrash_yn(String crash_yn) {
		this.crash_yn = crash_yn;
	}

	@JsonProperty("zxing_success_cnt")
	public String getZxing_success_cnt() {
		return this.zxing_success_cnt;
	}

	public void setZxing_success_cnt(String zxing_success_cnt) {
		this.zxing_success_cnt = zxing_success_cnt;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ERR002Request_Body_crushList [");
		builder.append("zem128_cnt=").append(zem128_cnt);
		builder.append(", ");
		builder.append("barcode_status=").append(barcode_status);
		builder.append(", ");
		builder.append("zxing_cnt=").append(zxing_cnt);
		builder.append(", ");
		builder.append("inserted_date=").append(inserted_date);
		builder.append(", ");
		builder.append("zemqr_success_cnt=").append(zemqr_success_cnt);
		builder.append(", ");
		builder.append("last_cmd_id=").append(last_cmd_id);
		builder.append(", ");
		builder.append("device_name=").append(device_name);
		builder.append(", ");
		builder.append("zemqr_cnt=").append(zemqr_cnt);
		builder.append(", ");
		builder.append("zem128_success_cnt=").append(zem128_success_cnt);
		builder.append(", ");
		builder.append("crash_yn=").append(crash_yn);
		builder.append(", ");
		builder.append("zxing_success_cnt=").append(zxing_success_cnt);
		builder.append("]");
		return builder.toString();
	}
}