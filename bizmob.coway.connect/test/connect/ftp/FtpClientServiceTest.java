package connect.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcnc.common.util.IOUtil;

import test.common.TestCommon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-ftp-applicationContext.xml")
public class FtpClientServiceTest extends TestCommon {

	@Autowired
	ApplicationContext appContext;
	
	FtpClientService ftpClientService;
	

	@Before
	public void setUp() throws Exception {
		ftpClientService = appContext.getBean(FtpClientService.class);
	}
	
	@Test
	public void downloadTest() {
		
		try {
			String fileName = "디비파일.zip";
			//String fileName = "ABCD.PPTX";
			
			//임시 파일 생성
			byte[] byteArray = ftpClientService.downloadFile("/test/test1/test2/test3", fileName);
			
			//FileOutputStream fos = new FileOutputStream(new File("D:/test/" + fileName));
			FileOutputStream fos = new FileOutputStream("D:/test/" + fileName);
			
			//fos.write(byteArray);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			bos.write(byteArray);
			
			IOUtil.closeQuietly(bos);
			IOUtil.closeQuietly(fos);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void uploadTest() {
		
		try {
			
			//임시파일 지정
			String filePath = "D:";
			String fileName = "test_0.png";
			
			FileInputStream fis = new FileInputStream(filePath + "/" + fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			byte[] byteArray = IOUtils.toByteArray(bis);
			
			ftpClientService.uploadFile("test\\test1\\test2\\test3", "test_0.png", byteArray);
			
			IOUtil.closeQuietly(bis);
			IOUtil.closeQuietly(fis);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@Test
	public void deleteTest() {
		
		try {
			
			ftpClientService.deleteFile("test", "test.java");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewFileListTest() {
		
		try {
			
			String path = "/test/test1/test2/test3";
			
			List<String> list = ftpClientService.viewFileList(path);
			
			for(String file : list) {
				//System.out.println(new String(file.getBytes(), "UTF-8"));		
				System.out.println(file);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
