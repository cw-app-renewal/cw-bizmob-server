package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

import common.util.FileAttachmentService;

@Component
public class CGR106_ADT_MediaDownloader extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR106_ADT_MediaDownloader.class);
		
	@Autowired FileAttachmentService fileAttachmentService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGR106_ADT_MediaDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		//goods 사진의 경우, 
		//file_name = partsCode (부품코드)
		//file_path = 5 (부품사진구분) 
		String fileName = null;
		String filePath = null;
		
		try {
			
			if(Boolean.parseBoolean(SmartConfig.getString("media.ftp.local.mode.flag", "true")) == true) {
				fileName = new String(request.getParameter("file_name").getBytes("iso-8859-1"), "utf-8");			//iso-8859-1
				filePath = new String(request.getParameter("file_path").getBytes("iso-8859-1"), "utf-8");
			} else {
				fileName = request.getParameter("file_name");
				filePath = request.getParameter("file_path");
			}
			
			filePath = filePath.replace("..", "");
			fileName = fileName.replace("..", "");
			
		} catch (NullPointerException e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다." );
        	return;
		}
		logger.debug("download fileName = [" + fileName + "]");
		logger.debug("download filePath = [" + filePath + "]");
		
		
	    FTPClient ftp = null;
	    FileOutputStream fos = null;
	    ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		
	    try {
			
			
			byte[] 					byteArray 	= fileAttachmentService.download(filePath, fileName, true);
			
			if(byteArray.length == 0) {
				logger.debug("ftp retriveFile size 0 !!");
				sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
				return;
			}
			
			if(Boolean.parseBoolean(SmartConfig.getString("media.ftp.temp.save.flag", "true")) == true) {
				File file = new File(SmartConfig.getString("media.ftp.temp.dir", "D:/test/") + fileName);
				fos = new FileOutputStream(file);
				fos.write(byteArray);
				logger.debug("media ftp temp file saved!!");
			}
			
			bais = new ByteArrayInputStream(byteArray);
			
	    	
	    	send( response, fileName, getFileExt(fileName), bais, byteArray.length, fileStartPos );
	    	
		} catch (Exception e) {
			logger.error("Exception :: ", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다.(3)" );
        	
		}  finally {
			IOUtil.closeQuietly( bais );	bais = null;
			IOUtil.closeQuietly( baos );	baos = null;
			IOUtil.closeQuietly( fos );		fos = null;
			try {
				if(ftp != null && ftp.isConnected()) {
					ftp.disconnect();
					ftp = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
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
