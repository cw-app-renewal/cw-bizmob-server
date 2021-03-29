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
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;

import connect.ftp.FtpClientService;

import adapter.common.TestAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class GoodsImgUpload extends TestAdapter {

	@Autowired
	private FtpClientService ftpClientService;
	
	@Test
	public void goodsImgUploadTest() {
		
		try {
			
			String path = "D:/test";
			
			File dirFile = new File(path);
			File[] fileList = dirFile.listFiles();
			
			for(File tmpFile : fileList) {
				
				if(tmpFile.isFile() == true) {
					
					String tmpName = tmpFile.getName();
					System.out.println("source name = " + tmpName);
					
					String goodsCode = "";
					String isThumbNail = "";
					if(tmpName.indexOf("_s") > 0) {
						goodsCode = tmpName.substring(0, tmpName.length()-6);
						isThumbNail = "Y";
					} else {
						goodsCode = tmpName.substring(0, tmpName.length()-4);
						isThumbNail = "N";
					}
					
					String filePath = CowayFtpFilePath.getGoodsFolder(goodsCode);
					String fileName = CowayFtpFileName.getGoodsImgName(goodsCode, isThumbNail);
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