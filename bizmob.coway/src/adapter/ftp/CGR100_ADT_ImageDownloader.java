package adapter.ftp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import connect.ftp.FtpClientService;

@Deprecated
public class CGR100_ADT_ImageDownloader extends AbstractDownloader implements Downloader {

	private ILogger logger = LoggerService.getLogger(CGR100_ADT_ImageDownloader.class);
		
	@Autowired
	FtpClientService ftpClientService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		/*logger.info(String.format("======== CowayImgDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		
		 * img-type : 이미지 종류
		 * 		1 : 설치사진
		 * 		2 : 주소사진
		 * 		3 : 메모사진
		 * 		4 : 제품사진
		 * 		5 : 부품사진
		 * img_name : 고객주문번호 or 제품코드 or 부품코드
		 * img_seq : 이미지 순번
		 
		String imgType = new String(request.getParameter("img_type").getBytes("iso-8859-1"), "utf-8");
		String jobDate = new String(request.getParameter("job_dt").getBytes("iso-8859-1"), "utf-8");
		String jobType = new String(request.getParameter("job_tp").getBytes("iso-8859-1"), "utf-8");
		String orderNo = new String(request.getParameter("order_no").getBytes("iso-8859-1"), "utf-8");
		String jobSeq = new String(request.getParameter("job_seq").getBytes("iso-8859-1"), "utf-8");
		String imgSeq = new String(request.getParameter("img_seq").getBytes("iso-8859-1"), "utf-8");
		String goodsCode = new String(request.getParameter("goods_cd").getBytes("iso-8859-1"), "utf-8");
		String partsCode = new String(request.getParameter("parts_cd").getBytes("iso-8859-1"), "utf-8");
		
		String filePath = CowayFtpFilePath.getCowayFtpFilePath(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, goodsCode, partsCode);
		String fileName = CowayFtpFileName.getCowayFtpFileName(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, goodsCode, partsCode);
		logger.debug("download full file path = [" + filePath + File.separator + fileName + "]");
		
	    ByteArrayInputStream bais = null;
		
	    try {
		
			//ftp
			byte[] byteArray = ftpClientService.downloadFile(filePath, fileName);
		  
	        bais = new ByteArrayInputStream(byteArray);
       
	    	send( response, fileName, getFileExt(fileName), bais, byteArray.length, fileStartPos );
	    	
		} catch (Exception e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
        	
		}  finally {
			IOUtil.closeQuietly( bais );
			
        }*/
	}
	
	private String getFileExt( String fileName ){
    	String extension;
    	int dotPos = fileName.lastIndexOf(".");
    	
    	if( dotPos != -1 )
    		extension = fileName.substring(dotPos + 1);
    	else
    		extension = "jpg";

    	return extension;
    }
	
    private boolean sendError( HttpServletResponse response, int errorCode, String errorText ) {
    	
    	try {
			response.sendError( errorCode, errorText );
		} catch (IOException e) {
			e.printStackTrace();
		}

    	return true;
    }	

}
