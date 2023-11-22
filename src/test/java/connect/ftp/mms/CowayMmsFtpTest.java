package connect.ftp.mms;

import com.mcnc.common.util.DateUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CowayMmsFtpTest {

	@Test
	public void imgDownload() {
		
		try {
			
			FTPClient ftp = new FTPClient();
			//ftp.connect("10.101.5.74", 22);
			ftp.connect("10.101.5.74");
			ftp.login("wjsms", "!dnvwjsms1");
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory("/");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);  
			
			String oFile = "/oradata/WJSMSEXCEL/img/magic1.jpg";
			String tFile = "D:/test/magic999.jpg";
			
			FileOutputStream fos = new FileOutputStream(tFile);
			ftp.retrieveFile(oFile, fos);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void imgUpload() {
		
		try {
			
			FTPClient ftp = new FTPClient();
			//ftp.connect("10.101.5.74", 22);
			ftp.connect("10.101.5.74");
			ftp.login("wjsms", "!dnvwjsms1");
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory("/");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);  
			
			String oFile = "/oradata/WJSMSEXCEL/img/magic9999.jpg";
			String tFile = "D:/test/magic999.jpg";
			
			FileInputStream fis = new FileInputStream(tFile);
			ftp.storeFile(oFile, fis);
			
			ftp.logout();
			ftp.disconnect();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void dateTest(){
		
		String date1 = DateUtil.getCurrentDate();
		System.out.println(date1);
		
		SimpleDateFormat date2 = new SimpleDateFormat("yyyyMM");
		String yyyymm = date2.format(new Date());
		System.out.println(yyyymm);
		
		SimpleDateFormat date3 = new SimpleDateFormat("yyyyMMdd");
		String yyyymmdd = date3.format(new Date());
		System.out.println(yyyymmdd);
		


		
	}
	
}
