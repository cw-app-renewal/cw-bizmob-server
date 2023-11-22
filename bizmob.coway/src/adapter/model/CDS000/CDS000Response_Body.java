package adapter.model.CDS000;


import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CDS000Response_Body {
	/**
	 * 테이블 스키마 업데이트 여부(Y:신규 테이블 스키만 있음. 기존 테이블 디비 파일 삭제 후, WD_WORK.db 파일을 내려받는다.)
	 */
	private String NEW_SCHEMA;

	public CDS000Response_Body() {
	}

	public CDS000Response_Body(JsonNode jsonNode) {
		this.NEW_SCHEMA = jsonNode.path("NEW_SCHEMA").getTextValue();
	}

	public String getNEW_SCHEMA() {
		return this.NEW_SCHEMA;
	}

	public void setNEW_SCHEMA(String NEW_SCHEMA) {
		this.NEW_SCHEMA = NEW_SCHEMA;
	}
}