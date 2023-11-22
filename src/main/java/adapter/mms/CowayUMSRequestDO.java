package adapter.mms;

public class CowayUMSRequestDO {

    // 수신번호
    private String TRAN_PHONE;

    // 회신번호 1588-5200 권장
    private String TRAN_CALLBACK;

    // 메시지 제목
    private String TITLE;

    // 메시지 본문
    private String MESSAGE;

    // 발송구분코드 : 시스템명 + 숫자 3자 ex) SAPCS002
    private String AUTOTYPE;

    // AUTOTYPE 와 연계된 발송용도설명 20자 이내
    private String AUTOTYPEDESC;

    // 시스템 운영/관리 부서코드 (ITPM 팀)
    private String DEPTCODE_OP;

    // 오너쉽 가진 현업 부서코드(비용배부 목적, 기술서비스운영지원팀)
    private String DEPTCODE;

    // 해당시스템의 수신자 식별 key (사번)
    private String LEGACYID;

    // R:실시간, B:배치
    private String SENDTYPE;

    // 첨부파일 URL
    private String ATTACH;


    public String getTRAN_PHONE() {
        return TRAN_PHONE;
    }

    public void setTRAN_PHONE(String TRAN_PHONE) {
        this.TRAN_PHONE = TRAN_PHONE;
    }

    public String getTRAN_CALLBACK() {
        return TRAN_CALLBACK;
    }

    public void setTRAN_CALLBACK(String TRAN_CALLBACK) {
        this.TRAN_CALLBACK = TRAN_CALLBACK;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getAUTOTYPE() {
        return AUTOTYPE;
    }

    public void setAUTOTYPE(String AUTOTYPE) {
        this.AUTOTYPE = AUTOTYPE;
    }

    public String getAUTOTYPEDESC() {
        return AUTOTYPEDESC;
    }

    public void setAUTOTYPEDESC(String AUTOTYPEDESC) {
        this.AUTOTYPEDESC = AUTOTYPEDESC;
    }

    public String getDEPTCODE_OP() {
        return DEPTCODE_OP;
    }

    public void setDEPTCODE_OP(String DEPTCODE_OP) {
        this.DEPTCODE_OP = DEPTCODE_OP;
    }

    public String getDEPTCODE() {
        return DEPTCODE;
    }

    public void setDEPTCODE(String DEPTCODE) {
        this.DEPTCODE = DEPTCODE;
    }

    public String getLEGACYID() {
        return LEGACYID;
    }

    public void setLEGACYID(String LEGACYID) {
        this.LEGACYID = LEGACYID;
    }

    public String getSENDTYPE() {
        return SENDTYPE;
    }

    public void setSENDTYPE(String SENDTYPE) {
        this.SENDTYPE = SENDTYPE;
    }

    public String getATTACH() {
        return ATTACH;
    }

    public void setATTACH(String ATTACH) {
        this.ATTACH = ATTACH;
    }

}
