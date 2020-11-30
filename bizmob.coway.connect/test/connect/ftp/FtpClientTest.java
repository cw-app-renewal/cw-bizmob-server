package connect.ftp;

import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FtpClientTest {

	@Test
	public void download() {
		
		try {
			FTPClient ftp = new FTPClient();
			ftp.setControlEncoding("EUC-KR");
			ftp.connect("10.101.1.57");
			ftp.login("ftpuser_smt", "tm!ak@xm#");
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory("/");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);  
			
			String oFile = "/test/test1/test2/test3/디비파일.zip";
			String tFile = "D:/test/디비파일.zip";
			
			FileOutputStream fos = new FileOutputStream(tFile);
			ftp.retrieveFile(oFile, fos);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
