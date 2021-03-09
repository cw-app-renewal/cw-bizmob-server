package common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {

	/**
	 * 썸네일 생성
	 * @param orgName 원본 이미지 파일 경로
	 * @param destName 썸에릴로 저장될 이미지 파일 경로
	 * @param width 줄일 가로 길이
	 * @param height 줄일 세로 길이
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(String orgName, String destName, int width, int height) throws IOException {
		File orgFile = new File(orgName);
		File destFile = new File(destName);
		
		return createThumb(orgFile, destFile, width, height);
	}
	/**
	 * 썸네일 생성
	 * @param orgFile 원본 이미지 파일 객체
	 * @param destFile 썸네일로 저장될 이미지 파일 객체
	 * @param width 줄일 가로 길이
	 * @param height 줄일 세로 길이
	 * @return 썸네일 파일이름
	 * @throws IOException
	 */
	public static String createThumb(File orgFile, File destFile, int width, int height) throws IOException {
		Image srcImg = null;
		String suffix = orgFile.getName().substring(orgFile.getName().lastIndexOf('.') + 1).toLowerCase();

		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(orgFile);
		} else {
			srcImg = new ImageIcon(orgFile.toString()).getImage();
		}
		
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);
		int destWidth = -1, destHeight = -1;

		if (width < 0) {
			destWidth = srcWidth;
		} else if (width > 0) {
			destWidth = width;
		}

		if (height < 0) {
			destHeight = srcHeight;
		} else if (height > 0) {
			destHeight = height;
		}
		
		Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[destWidth * destHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
		ImageIO.write(destImg, "jpg", destFile);

		return destFile.getName();
	}

	
}
