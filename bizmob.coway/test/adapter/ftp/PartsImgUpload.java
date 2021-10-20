package adapter.ftp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcnc.common.util.IOUtil;

import adapter.common.TestAdapter;
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import connect.ftp.FtpClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class PartsImgUpload extends TestAdapter {

	@Autowired
	private FtpClientService ftpClientService;

	@Test
	public void partsImgUploadTest() {
		
		try {
			
			String path = "D:/test";
			
			File dirFile = new File(path);
			File[] fileList = dirFile.listFiles();
			
			for(File tmpFile : fileList) {
				
				if(tmpFile.isFile() == true) {
					
					String tmpName = tmpFile.getName();
					System.out.println("source name = " + tmpName);
					
					String partCode = "";
					String isThumbNail = "";
					if(tmpName.indexOf("_s") > 0) {
						partCode = tmpName.substring(0, tmpName.length()-6);
						isThumbNail = "Y";
					} else {
						partCode = tmpName.substring(0, tmpName.length()-4);
						isThumbNail = "N";
					}
										
					String filePath = CowayFtpFilePath.getPartsFolder(partCode);
					String fileName = CowayFtpFileName.getPartsImgName(partCode, isThumbNail);
					System.out.println("target name = " + filePath + File.separator + fileName);
					
					FileInputStream fis = new FileInputStream(tmpFile);
					BufferedInputStream bis = new BufferedInputStream(fis);
					byte[] fileData = IOUtil.toByteArray(bis);
					
					ftpClientService.uploadFile(filePath, fileName, fileData);
					
					IOUtil.closeQuietly(bis);
					IOUtil.closeQuietly(fis);
					
				}
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
