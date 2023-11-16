package adapter.model.CGR000;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

/**
 * Comment
 */
public class CGR000Response_Body {
	/**
	 * 차용재고인수조회_미서명일자
	 */
	private String O_IR_DT;
	/**
	 * 사용자 그룹구분
	 */
	private String O_PERSG;
	/**
	 * 서약존재여부
	 */
	private String O_PLEDGE_YN;
	/**
	 * 차용재고인수조회_미서명여부
	 */
	private String O_IR_YN;
	/**
	 * 사용자이름
	 */
	private String O_SNAME;
	/**
	 * 부서코드
	 */
	private String O_DEPT_CD;
	/**
	 * 단일설문조사여부
	 */
	private String O_SNGL_RSCH_YN;
	/**
	 * 출고전입고내역존재여부
	 */
	private String O_DO_CANCEL_YN;
	/**
	 * 설문조사여부
	 */
	private String O_RSCH_YN;
	/**
	 * 부서명
	 */
	private String O_DEPT_NM;
	/**
	 * O_ITAB2
	 */
	private List<CGR000Response_Body_O_ITAB2> O_ITAB2;
	/**
	 * O_ITAB1
	 */
	private List<CGR000Response_Body_O_ITAB1> O_ITAB1;
	/**
	 * O_ITAB3
	 */
	private List<CGR000Response_Body_O_ITAB3> O_ITAB3;

	public CGR000Response_Body() {
	}

	public CGR000Response_Body(JsonNode jsonNode) {
		this.O_IR_DT = jsonNode.path("O_IR_DT").getTextValue();
		this.O_PERSG = jsonNode.path("O_PERSG").getTextValue();
		this.O_PLEDGE_YN = jsonNode.path("O_PLEDGE_YN").getTextValue();
		this.O_IR_YN = jsonNode.path("O_IR_YN").getTextValue();
		this.O_SNAME = jsonNode.path("O_SNAME").getTextValue();
		this.O_DEPT_CD = jsonNode.path("O_DEPT_CD").getTextValue();
		this.O_SNGL_RSCH_YN = jsonNode.path("O_SNGL_RSCH_YN").getTextValue();
		this.O_DO_CANCEL_YN = jsonNode.path("O_DO_CANCEL_YN").getTextValue();
		this.O_RSCH_YN = jsonNode.path("O_RSCH_YN").getTextValue();
		this.O_DEPT_NM = jsonNode.path("O_DEPT_NM").getTextValue();
		this.O_ITAB2 = new ArrayList<CGR000Response_Body_O_ITAB2>();
		JsonNode O_ITAB2Node = jsonNode.path("O_ITAB2");
		for (Iterator<JsonNode> iter = O_ITAB2Node.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB2.add(new CGR000Response_Body_O_ITAB2(node));
		}
		this.O_ITAB1 = new ArrayList<CGR000Response_Body_O_ITAB1>();
		JsonNode O_ITAB1Node = jsonNode.path("O_ITAB1");
		for (Iterator<JsonNode> iter = O_ITAB1Node.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB1.add(new CGR000Response_Body_O_ITAB1(node));
		}
		this.O_ITAB3 = new ArrayList<CGR000Response_Body_O_ITAB3>();
		JsonNode O_ITAB3Node = jsonNode.path("O_ITAB3");
		for (Iterator<JsonNode> iter = O_ITAB3Node.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			O_ITAB3.add(new CGR000Response_Body_O_ITAB3(node));
		}
	}

	public String getO_IR_DT() {
		return this.O_IR_DT;
	}

	public void setO_IR_DT(String O_IR_DT) {
		this.O_IR_DT = O_IR_DT;
	}

	public String getO_PERSG() {
		return this.O_PERSG;
	}

	public void setO_PERSG(String O_PERSG) {
		this.O_PERSG = O_PERSG;
	}

	public String getO_PLEDGE_YN() {
		return this.O_PLEDGE_YN;
	}

	public void setO_PLEDGE_YN(String O_PLEDGE_YN) {
		this.O_PLEDGE_YN = O_PLEDGE_YN;
	}

	public String getO_IR_YN() {
		return this.O_IR_YN;
	}

	public void setO_IR_YN(String O_IR_YN) {
		this.O_IR_YN = O_IR_YN;
	}

	public String getO_SNAME() {
		return this.O_SNAME;
	}

	public void setO_SNAME(String O_SNAME) {
		this.O_SNAME = O_SNAME;
	}

	public String getO_DEPT_CD() {
		return this.O_DEPT_CD;
	}

	public void setO_DEPT_CD(String O_DEPT_CD) {
		this.O_DEPT_CD = O_DEPT_CD;
	}

	public String getO_SNGL_RSCH_YN() {
		return this.O_SNGL_RSCH_YN;
	}

	public void setO_SNGL_RSCH_YN(String O_SNGL_RSCH_YN) {
		this.O_SNGL_RSCH_YN = O_SNGL_RSCH_YN;
	}

	public String getO_DO_CANCEL_YN() {
		return this.O_DO_CANCEL_YN;
	}

	public void setO_DO_CANCEL_YN(String O_DO_CANCEL_YN) {
		this.O_DO_CANCEL_YN = O_DO_CANCEL_YN;
	}

	public String getO_RSCH_YN() {
		return this.O_RSCH_YN;
	}

	public void setO_RSCH_YN(String O_RSCH_YN) {
		this.O_RSCH_YN = O_RSCH_YN;
	}

	public String getO_DEPT_NM() {
		return this.O_DEPT_NM;
	}

	public void setO_DEPT_NM(String O_DEPT_NM) {
		this.O_DEPT_NM = O_DEPT_NM;
	}

	public List<CGR000Response_Body_O_ITAB2> getO_ITAB2() {
		return this.O_ITAB2;
	}

	public void setO_ITAB2(List<CGR000Response_Body_O_ITAB2> O_ITAB2) {
		this.O_ITAB2 = O_ITAB2;
	}

	public List<CGR000Response_Body_O_ITAB1> getO_ITAB1() {
		return this.O_ITAB1;
	}

	public void setO_ITAB1(List<CGR000Response_Body_O_ITAB1> O_ITAB1) {
		this.O_ITAB1 = O_ITAB1;
	}

	public List<CGR000Response_Body_O_ITAB3> getO_ITAB3() {
		return this.O_ITAB3;
	}

	public void setO_ITAB3(List<CGR000Response_Body_O_ITAB3> O_ITAB3) {
		this.O_ITAB3 = O_ITAB3;
	}
}