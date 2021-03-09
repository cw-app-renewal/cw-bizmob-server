package adapter.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtil {
	
	private final static String KEY = "COWAYENCRYPTKEY!";

    public static String getEncryptAES256(String value) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        String result_value = "";

        if(value.equals("")){
            return "";
        }

        result_value = aes256encrypt(KEY, value);
//        result_value = aes256encrypt(new Setting().set(), value);
        String retrunValue = "";
        if (!result_value.equals("")) {
            retrunValue = result_value;
        } else {
            retrunValue = "";
        }
        return retrunValue;
    }

    public static String aes256encrypt(String key, String str) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] keyDate = key.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyDate, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv));

        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    public static String getDecryptAES256(String value) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String result_value = "";
        String retrunValue = "";

        if(value.equals("")){
            return "";
        }

        result_value = aes256decrypt(KEY, value);
//        result_value = aes256decrypt(new Setting().set(), value);
        if (!result_value.equals("")) {
            retrunValue = result_value;
        } else {
            retrunValue = "";
        }
        return retrunValue;
    }

    public static String aes256decrypt(String key, String enStr) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] keyDate = key.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyDate, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv));

        byte[] byteStr = Base64.decodeBase64(enStr.getBytes());
        String str = new String(c.doFinal(byteStr), "UTF-8");

        return str;
    }

}