package adapter.mms;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Text2Image {

	private static final Logger logger = LoggerFactory.getLogger(Text2Image.class);

	String bkImageName = "bkReceipt_blank.jpg";
	BufferedImage bkOrgImage = null;
	String smartHome = System.getProperty("SMART_HOME");
	boolean testMode = true;
	
	public boolean isTestMode() {
		return testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	// background image load
	public Text2Image(){		
		try {
			//String fileFullPath = smartHome + "/" + filePath + "/" + fileName;
			bkOrgImage = ImageIO.read(new File(smartHome+"/local_resources/"+bkImageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] text2Image(boolean testMode, String targetImageName, JsonNode I_ITAB) throws IOException{
		this.setTestMode(testMode);
		return text2Image(targetImageName, I_ITAB);
	}
	
	public byte[] text2Image(String targetImageName, JsonNode I_ITAB) throws IOException{
		// init
		String targetImageFullPath = smartHome + "/upload_temp/" + targetImageName;
		
		logger.debug("CGW902(text2Image) :: targetImageName = " + targetImageName);
		
		// image copy
//		ColorModel cm = bkOrgImage.getColorModel();
//		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//		WritableRaster raster = bkOrgImage.copyData(null);
//		
//		BufferedImage im = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		BufferedImage im = bkOrgImage;
		
		Graphics2D g2d = im.createGraphics();
		this.drawString(g2d, I_ITAB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im,  "jpg", baos);
		
		// save image file
		logger.debug("CGW902(text2Image) :: isTestMode = " + this.isTestMode());
		if( this.isTestMode() == true) {
			FileOutputStream fos = new FileOutputStream(targetImageFullPath);
			fos.write(baos.toByteArray());
			fos.close();			
		}
		
		return baos.toByteArray();
		
	}
	
	private void drawString(Graphics2D g2d, JsonNode I_ITAB){
		Font t = new Font("굴림체", Font.BOLD, 34);
		
		//int[] positionArray = {LeftX, LeftY, RightX, bold style, font size};
		int[][] textPosArray = {
								{27, 75, 750, new Color(54, 57, 62).getRGB(), Font.BOLD, 34},	//SECTOR "01" : 타이틀
								{50, 238, 750, Color.BLACK.getRGB(), Font.PLAIN, 28},	//SECTOR "02" : 발행정보
								{50, 488, 750, Color.BLACK.getRGB(), Font.PLAIN, 28},	//SECTOR "03" : 입금구분
								{50, 922, 750, Color.BLACK.getRGB(), Font.PLAIN, 28},	//SECTOR "04" : 결제내역
								{40, 1240, 750, Color.RED.getRGB(), Font.PLAIN, 28}	//SECTOR "05" : 하단문구
							};
		int line = 0;
		int gap = 27;
		int line_gap = 37;
		
		Iterator<JsonNode> iterator = I_ITAB.iterator();
		while( iterator.hasNext() ){
			JsonNode array = iterator.next();
			String sector = array.findPath("SECTOR").getTextValue();
			int nSectorNum = Integer.parseInt(sector);

			String sectorSeq = array.findPath("SECTOR_SEQ").getTextValue();
			int nSectorSeq = 0;
			if( !sectorSeq.equalsIgnoreCase("") ){
				nSectorSeq = Integer.parseInt(sectorSeq);
			}

			String text1 = array.findPath("TEXT1").getTextValue();
			String text2 = array.findPath("TEXT2").getTextValue();

			// font 설정
			int[] postion = textPosArray[nSectorNum-1];
			g2d.setColor(new Color(postion[3]));
			g2d.setFont( t.deriveFont(postion[4], postion[5]) );

			// 글씨 크기 조회
			FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
			int nText2Width = fm.stringWidth(text2);
			
			// 글씨 그리기
			if( nSectorSeq == 0){
				g2d.drawString(text1, postion[0], postion[1]+gap);
			}else{
				g2d.drawString(text1, postion[0], postion[1]+gap+line_gap*(nSectorSeq-1));
				g2d.drawString(text2, postion[2]-nText2Width, postion[1]+gap+line_gap*(nSectorSeq-1));
			}
		}
		
/*
//		SECTOR	SECTOR_SEQ	TEXT
//		타이틀
//		01	0	코웨이(주) 모바일 영수증
		line = 0;
		g2d.drawString(text, sector01_posX, sector01_posY+gap);

//		발행정보	max	5
//		02	1	영수증번호                            100000000010					
//		02	2	고객명                                      안희옥					
//		02	3	주문번호                        20ABK0903545외 4건					
//		02	4	수납자                오창지국 이순진 ( 20222929 )					
//		02	5	발행시간                      2013. 11. 25 15 : 46
		line = 0;
		g2d.setColor(Color.BLACK);
		g2d.setFont( new Font("gulim", Font.PLAIN, 28) );
		g2d.drawString(text, sector02_posX, sector02_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector02_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector02_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector02_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector02_posY+gap+line_gap*line++);

//		입금구분	max 10
//		03	1	멤버쉽월회비                                83,100					
//		03	2	멤버쉽연체이자                               1,100					
//		03	3	렌탈료                                      20,750
		line = 0;
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		text = "1234567890123456789012345678901234567890";//50
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector03_posY+gap+line_gap*line++);

//		결제내역	max	8		
//		04	1	카드구분                                  롯데카드					
//		04	2	매입사                                  롯데아멕스					
//		04	3	카드번호                       5200-4505-4603-****
		line = 0;
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);
		g2d.drawString(text, sector02_posX, sector04_posY+gap+line_gap*line++);

//		alert	max	1
//		05	0	* 2013년 05월 25일 총 104,950원 수납되었습니다.					
		g2d.setColor(Color.RED);
		g2d.setFont( new Font("gulim", Font.PLAIN, 28) );
		g2d.drawString("* "+text, sector05_posX, sector05_posY);
*/
	}
	
}
