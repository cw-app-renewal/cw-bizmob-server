package adapter.mms;

import com.mcnc.smart.common.config.SmartConfig;

public class CowayUMSInfo {

    static String getUrl() {
        return SmartConfig.getString("coway.ums.url", "https://ums.coway.com/n5/rest/coway/send/mms");
    }

    static String getAuthId() {
        return SmartConfig.getString("coway.ums.auth.id", "");
    }

    static String getAuthKey() {
        return SmartConfig.getString("coway.ums.auth.key", "");
    }

    static String getAutotype() {
        return SmartConfig.getString("coway.ums.autotype", "");
    }

    static String getAutotypeDesc(String flag, String deptCode) {

        String rsrvdId;

        if (flag.equalsIgnoreCase("doctor")) {
            rsrvdId = SmartConfig.getString("coway.mms.upload.doctor.id", "RFC02엄마2")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if(flag.equalsIgnoreCase("cody")) {
            rsrvdId = SmartConfig.getString("coway.mms.upload.cody.id", "RFC02엄마")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if ( flag.equalsIgnoreCase("sun") ) {
            rsrvdId = SmartConfig.getString("coway.mms.upload.sun.id", "RFC02아들")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if ( flag.equalsIgnoreCase("home") ) {
            rsrvdId = SmartConfig.getString("coway.mms.upload.home.id", "RFC02HOME-MMS")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if ( flag.equalsIgnoreCase("codyreceipt") ) {
            // cody receipt image mms
            rsrvdId = SmartConfig.getString("coway.mms.upload.codyreceipt.id", "Web02mob-MMS")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if ( flag.equalsIgnoreCase("homereceipt") ) {
            // homecare receipt image mms
            rsrvdId = SmartConfig.getString("coway.mms.upload.codyreceipt.id", "RFC02스마트1")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else if ( flag.equalsIgnoreCase("CODYMMS") ) {
            // codysns image mms
            rsrvdId = SmartConfig.getString("coway.mms.upload.codysns.id", "RFC02전단")
                    + " " + SmartConfig.getString("coway.ums.profile", "");

        } else {
            rsrvdId = deptCode;

        }

        return rsrvdId;
    }

    // 시스템 운영/관리 부서코드 (ITPM 팀)
    static String getDeptCodeOp() {
        return "20036372";
    }

    // 오너쉽 가진 현업 부서코드(비용배부 목적, 기술서비스운영지원팀)
    static String getDeptCode() {
        return "26000133";
    }
}
