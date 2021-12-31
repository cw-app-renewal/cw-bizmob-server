package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.util.FileAttachmentService;

@Component
public class CGR105_ADT_PartsImageDownloader extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR105_ADT_PartsImageDownloader.class);
		
	@Autowired FileAttachmentService fileAttachmentService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGR105_ADT_PartsImageDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		//goods 사진의 경우, 
		//file_name = partsCode (부품코드)
		//file_path = 5 (부품사진구분) 
		String reqFileName = null;
		String partsCode = null;
		String isThumbnail = null;
		
		try {
			reqFileName = new String(request.getParameter("file_name").getBytes("iso-8859-1"), "utf-8");
			partsCode = new String(request.getParameter("parts_cd").getBytes("iso-8859-1"), "utf-8");
		    isThumbnail = new String(request.getParameter("thumbnail").getBytes("iso-8859-1"), "utf-8");
		} catch (NullPointerException e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다." );
        	return;
		}

		String filePath = CowayFtpFilePath.getPartsFolder(partsCode);
		String fileName = CowayFtpFileName.getPartsImgName(partsCode, isThumbnail);
		
		
	    ByteArrayInputStream bais = null;
		
	    try {
		
	    	
			byte[] 					byteArray 	= fileAttachmentService.download(filePath, fileName, true);
		  
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
