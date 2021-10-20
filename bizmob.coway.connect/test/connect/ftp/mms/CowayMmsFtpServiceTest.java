package connect.ftp.mms;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcnc.common.util.IOUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-ftp-applicationContext.xml")
public class CowayMmsFtpServiceTest {

	
	@Autowired
	ApplicationContext appContext;
	
	
	CowayMmsFtpsService cowayMmsFtpsService;
	
	@Before
	public void setUp() throws Exception {
		cowayMmsFtpsService = appContext.getBean(CowayMmsFtpsService.class);
	}
	
	
	@Test
	public void cowayMmsFtpServiceDownloadTest() {
		
		try {
			String fileName = "11.jpg";
			
			//임시 파일 생성
			byte[] byteArray = cowayMmsFtpsService.downloadImageFile("/oradata/WJSMSEXCEL/img/", fileName);
			
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
	public void cowayMmsFtpServiceViewListTest() {
		
		try {
			String path = "/oradata/WJSMSEXCEL/img";
			
			List<String> list = cowayMmsFtpsService.viewImageFileList(path);
			
			for(String file : list) {
				//System.out.println(new String(file.getBytes(), "UTF-8"));		
				System.out.println(file);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
