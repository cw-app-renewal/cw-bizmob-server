package common.ftp;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class CowayImageCustomerTypeDO {
	
	//고객관련 이미지 정보
	private String COMMAND;			//C:create, D:Delete
	private String ORDER_NO;		//고객주문번호
	private String GUBUN1;			//C:customer, W:work
	private String GUBUN2;			//INST:설치, ADDR(주소)
	private String FILENAME;		//파일명
	
	public String getCOMMAND() {
		return COMMAND;
	}
	public void setCOMMAND(String COMMAND) {
		this.COMMAND = COMMAND;
	}
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String ORDER_NO) {
		this.ORDER_NO = ORDER_NO;
	}
	public String getGUBUN1() {
		return GUBUN1;
	}
	public void setGUBUN1(String GUBUN1) {
		this.GUBUN1 = GUBUN1;
	}
	public String getGUBUN2() {
		return GUBUN2;
	}
	public void setGUBUN2(String GUBUN2) {
		this.GUBUN2 = GUBUN2;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String FILENAME) {
		this.FILENAME = FILENAME;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	@Override
	public String toString() {
		return "CowayImageCustomerTypeDO [COMMAND=" + COMMAND + ", ORDER_NO="
				+ ORDER_NO + ", GUBUN1=" + GUBUN1 + ", GUBUN2=" + GUBUN2
				+ ", FILENAME=" + FILENAME + "]";
	}
	
	
}
