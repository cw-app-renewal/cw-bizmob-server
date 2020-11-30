package common.util;

import java.io.IOException;

import org.junit.Test;

public class ImageUtilTest {

	@Test
	public void createThumbnailTest() {
		
		String oImg = "D:/test/testImg.png";
		String tImg = "D:/test/testImgThumbnail.png";
		
		try {
			ImageUtil.createThumb(oImg, tImg, 128, 72);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	

}
