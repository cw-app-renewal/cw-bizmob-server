package connect.ftp.mms;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class CowayMmsFtpUtilsTest {

	
	
	@Test
	public void ftpUploladTest() {
		
		try {
			
			String host = "10.101.5.74";
			String user = "wjsms";
			String password = "!dnvwjsms1";
			
			String path = "D:/test/";
			String file = "magic.jpg";
			
			FileInputStream fis = new FileInputStream(path + "/" + file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			byte[] byteArray = IOUtils.toByteArray(bis);

			String remotePath = "/oradata/WJSMSEXCEL/img/";
			String remoteFile = "magicTest.jpg";
			
			CowayMmsFtpUtils ftpUtils = new CowayMmsFtpUtils(host, user, password);
			ftpUtils.ftpUpload(remotePath, remoteFile, byteArray);
			
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	
}
