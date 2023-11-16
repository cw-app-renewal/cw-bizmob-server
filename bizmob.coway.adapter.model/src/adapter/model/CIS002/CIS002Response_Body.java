package adapter.model.CIS002;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Comment
 */
public class CIS002Response_Body {
	/**
	* 스마트진단결과 이력목록
	*/
	private List<CIS002Response_Body_content> content = new ArrayList<CIS002Response_Body_content>();
	/**
	* 페이지당 최대 조회 항목 수
	*/
	private int size = 0;
	/**
	* 조회 항목 수
	*/
	private int numberOfElements = 0;
	/**
	* 다음 페이징 여부 및 페이징키 - lastEvaluatedKey == null 이면 다음 페이지 없음. - lastEvaluatedKey != null 이면 하위 creationDt 를 Request Parameter 로 전달
	*/
	private List<CIS002Response_Body_lastEvaluatedKey> lastEvaluatedKey = new ArrayList<CIS002Response_Body_lastEvaluatedKey>();

	public CIS002Response_Body() {
	}

	public CIS002Response_Body(JsonNode jsonNode) {
		this.content = new ArrayList<CIS002Response_Body_content>();
		JsonNode contentNode = jsonNode.path("content");
		for (Iterator<JsonNode> iter = contentNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			content.add(new CIS002Response_Body_content(node));
		}
		this.size = jsonNode.path("size").getIntValue();
		this.numberOfElements = jsonNode.path("numberOfElements").getIntValue();
		this.lastEvaluatedKey = new ArrayList<CIS002Response_Body_lastEvaluatedKey>();
		JsonNode lastEvaluatedKeyNode = jsonNode.path("lastEvaluatedKey");
		for (Iterator<JsonNode> iter = lastEvaluatedKeyNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			lastEvaluatedKey.add(new CIS002Response_Body_lastEvaluatedKey(node));
		}
	}

	@JsonProperty("content")
	public List<CIS002Response_Body_content> getContent() {
		return this.content;
	}

	public void setContent(List<CIS002Response_Body_content> content) {
		this.content = content;
	}

	@JsonProperty("size")
	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@JsonProperty("numberOfElements")
	public int getNumberOfElements() {
		return this.numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	@JsonProperty("lastEvaluatedKey")
	public List<CIS002Response_Body_lastEvaluatedKey> getLastEvaluatedKey() {
		return this.lastEvaluatedKey;
	}

	public void setLastEvaluatedKey(List<CIS002Response_Body_lastEvaluatedKey> lastEvaluatedKey) {
		this.lastEvaluatedKey = lastEvaluatedKey;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CIS002Response_Body [");
		builder.append("content=").append(content);
		builder.append(", ");
		builder.append("size=").append(size);
		builder.append(", ");
		builder.append("numberOfElements=").append(numberOfElements);
		builder.append(", ");
		builder.append("lastEvaluatedKey=").append(lastEvaluatedKey);
		builder.append("]");
		return builder.toString();
	}
}