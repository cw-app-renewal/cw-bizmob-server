// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3)
// Source File Name:   EnDecode.java

package common.util;

public class EnDecode {

    public EnDecode() {
    }

    public static String[] EncodeByFileInType(String strType, String filePath, String strData) {
        String arrRet[] = new String[4];
        String strD0 = strType;
        String strD1 = EncodeBySType(strData);
        String strD2 = null;
        String strD3 = null;
        arrRet[0] = strD1;
        arrRet[1] = strD2;
        arrRet[2] = strD3;
        arrRet[3] = strD0;
        return arrRet;
    }

    public static String[] EncodeByFile(String filePath, String strData) {
        String strType = "SDS";
        return EncodeByFileInType(strType, filePath, strData);
    }

    public static String[] EncodeByCertIDInType(String strType, String certID, String strData) {
        String arrRet[] = new String[4];
        String strD0 = strType;
        String strD1 = EncodeBySType(strData);
        String strD2 = null;
        String strD3 = null;
        arrRet[0] = strD1;
        arrRet[1] = strD2;
        arrRet[2] = strD3;
        arrRet[3] = strD0;
        return arrRet;
    }

    public static String[] EncodeByCertID(String certID, String strData) {
        String strType = "SDS";
        return EncodeByCertIDInType(strType, certID, strData);
    }

    public static String DecodeByFileInType(String strType, String filePath, String strKey, String strHash, String strData) {
        return DecodeBySType(strData);
    }

    public static String DecodeByFile(String filePath, String strKey, String strHash, String strData) {
        String strType = "SDS";
        return DecodeByFileInType(strType, filePath, strKey, strHash, strData);
    }

    public static String EncodeBySType(String strData) {
        return Encrypt.com_Encode(":" + strData + ":sisenc");
    }

    public static String DecodeBySType(String strData) {
        String strRet = Encrypt.com_Decode(strData);
        int e = strRet.indexOf(":");
        int d = strRet.indexOf(":sisenc");
        if (e > -1 && d > -1)
            strRet = strRet.substring(e + 1, d);
        return strRet;
    }
}
