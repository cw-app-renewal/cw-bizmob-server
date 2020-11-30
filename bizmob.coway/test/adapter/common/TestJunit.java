package adapter.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

public class TestJunit {

	@Test
	public void test() {
		
		
		
		
		//dLizSq1hSJDh1eJ4ZgVKkjbolLZR8oog21nO\/RKWjReTMj42XVhI2uaNgJi1vP6X;
		String value = "i9UVojt9DDLNw8zHuI0CWQ0JfGYcjtvOBTtIWSyxdyY=";
		
		try {
			String enc = EncryptionUtil.getEncryptAES256(value);
			System.out.println(enc);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String appKey = "COWMANP0";
		
		
	}
	
	@Test
	public void test1() {
		
		String nowTime = "20200804000000000";
		String reqTime = "20200803000000000";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date nowDate = dateFormat.parse(nowTime);
			Date reqDate = dateFormat.parse(reqTime);
			
			System.out.println( nowDate.after(reqDate) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
