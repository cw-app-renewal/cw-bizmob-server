package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import connect.ftp.FtpClientService;

@Component
public class CGR103_ADT_MemoImageDownloader extends AbstractDownloader implements Downloader {

	private ILogger logger = LoggerService.getLogger(CGR103_ADT_MemoImageDownloader.class);
		
	@Autowired
	FtpClientService ftpClientService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGR103_ADT_MemoImageDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		//install 사진의 경우, 
		//file_name = tmpOrderNo (임시고객주문번호)
		//file_path = 3 (고객메모사진구분) 
		//file_seq = 1/2/3 (이미지 순번)
		String reqFileName = null;
		String tmpOrderNo = null;
		String imgSeq = null;
		
		try {
			reqFileName = new String(request.getParameter("file_name").getBytes("iso-8859-1"), "utf-8");
		    tmpOrderNo = new String(request.getParameter("tmp_order_no").getBytes("iso-8859-1"), "utf-8");
		    imgSeq = new String(request.getParameter("img_seq").getBytes("iso-8859-1"), "utf-8");
		} catch (NullPointerException e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다." );
        	return;
		}
		String filePath = CowayFtpFilePath.getMemoFolder(tmpOrderNo);
		String fileName = CowayFtpFileName.getMemoImgName(tmpOrderNo, imgSeq);
		logger.debug("download full file path = [" + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName + "]");
		
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
			IOUtil.closeQuietly( bais );	bais = null;
			
        }
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
