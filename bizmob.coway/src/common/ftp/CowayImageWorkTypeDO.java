package common.ftp;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class CowayImageWorkTypeDO {

	//작업관련 이미지 정보
	private String COMMAND;			//C:create, D:Delete
	private String JOB_DT;			//작업예정일자
	private String JOB_TP;			//작업구분
	private String ORDER_NO;		//고객주문번호
	private String JOB_SEQ;			//작업일련번호
	private String GUBUN1;			//C:customer, W:work
	private String GUBUN2;			//NAN:난공사, QA_IMG:품직(사진), QA_MOV:품질(동영상)
	private String FILENAME;		//파일명
	
	public String getCOMMAND() {
		return COMMAND;
	}
	public void setCOMMAND(String COMMAND) {
		this.COMMAND = COMMAND;
	}
	public String getJOB_DT() {
		return JOB_DT;
	}
	public void setJOB_DT(String JOB_DT) {
		this.JOB_DT = JOB_DT;
	}
	public String getJOB_TP() {
		return JOB_TP;
	}
	public void setJOB_TP(String JOB_TP) {
		this.JOB_TP = JOB_TP;
	}
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String ORDER_NO) {
		this.ORDER_NO = ORDER_NO;
	}
	public String getJOB_SEQ() {
		return JOB_SEQ;
	}
	public void setJOB_SEQ(String JOB_SEQ) {
		this.JOB_SEQ = JOB_SEQ;
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
		return "CowayImageWorkTypeDO [COMMAND=" + COMMAND + ", JOB_DT="
				+ JOB_DT + ", JOB_TP=" + JOB_TP + ", ORDER_NO=" + ORDER_NO
				+ ", JOB_SEQ=" + JOB_SEQ + ", GUBUN1=" + GUBUN1 + ", GUBUN2="
				+ GUBUN2 + ", FILENAME=" + FILENAME + "]";
	}
	
	
}
