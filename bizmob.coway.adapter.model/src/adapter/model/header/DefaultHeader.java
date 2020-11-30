package adapter.model.header;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class DefaultHeader {
	/**
	 * 서버에서 전달하는 에러코드
	 */
	private String error_code;
	/**
	 * 화면에 보여 줄 에러 메시지
	 */
	private String error_text;
	/**
	 * 추가 정보
	 */
	private String info_text;
	/**
	 * hybrid web 세션 id
	 */
	private String login_session_id;
	/**
	 * 메시지 버전
	 */
	private String message_version;
	/**
	 * 메시지 처리 결과
	 */
	private boolean result;
	/**
	 * 메시지 코드
	 */
	private String trcode;

	public DefaultHeader() {
	}

	public DefaultHeader(JsonNode jsonNode) {
		this.error_code = jsonNode.path("error_code").getTextValue();
		this.error_text = jsonNode.path("error_text").getTextValue();
		this.info_text = jsonNode.path("info_text").getTextValue();
		this.login_session_id = jsonNode.path("login_session_id")
				.getTextValue();
		this.message_version = jsonNode.path("message_version").getTextValue();
		this.result = jsonNode.path("result").getBooleanValue();
		this.trcode = jsonNode.path("trcode").getTextValue();
	}

	public String getError_code() {
		return this.error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_text() {
		return this.error_text;
	}

	public void setError_text(String error_text) {
		this.error_text = error_text;
	}

	public String getInfo_text() {
		return this.info_text;
	}

	public void setInfo_text(String info_text) {
		this.info_text = info_text;
	}

	public String getLogin_session_id() {
		return this.login_session_id;
	}

	public void setLogin_session_id(String login_session_id) {
		this.login_session_id = login_session_id;
	}

	public String getMessage_version() {
		return this.message_version;
	}

	public void setMessage_version(String message_version) {
		this.message_version = message_version;
	}

	public boolean getResult() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getTrcode() {
		return this.trcode;
	}

	public void setTrcode(String trcode) {
		this.trcode = trcode;
	}
}