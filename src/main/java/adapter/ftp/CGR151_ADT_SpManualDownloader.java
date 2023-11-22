package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import common.util.FileAttachmentService;
@Component
public class CGR151_ADT_SpManualDownloader  extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR151_ADT_SpManualDownloader.class);

	private static final String ISO_8859_1_ENCODING = "iso-8859-1";
	private static final String UTF_8_ENCODING = "utf-8";
	
	@Autowired FileAttachmentService fileAttachmentService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format(">>>> CGR151_ADT_SpManualDownloader Start downloading from %s : %s", target, uid));

		int fileStartPos = Integer.parseInt(params.get("index").toString());
		
		HttpServletRequest request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");
		
//		String fileFullPath = "";
		String fileName = "";
		String filePath = "";
		
		try {
			
			//fileFullPath = iso88591ToUtf8(request.getParameter("file_path"));
			//fileName = getFileName(fileFullPath);
			//filePath = getFilePath(fileFullPath, fileName);
			
			fileName = request.getParameter("file_name");
			filePath = iso88591ToUtf8(request.getParameter("file_path"));
			
			filePath = filePath.replace("..", "");
			fileName = fileName.replace("..", "");
			
			logger.info(">>>> before File Path = " + filePath);
			
			filePath = filePath.substring(0, filePath.length() -1);
			
			logger.debug(">>>>>>>>>>>>>>>>>>>>> " + fileName);
			
			logger.info(">>>> params = fileName :: " + ", filePath = " + filePath);
		} catch ( Exception e ) {
			
			logger.error("", e);
			sendError(response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다.");
			
			return;
		}
		
		ByteArrayInputStream bais = null;
		
	    try {

	    	
			byte[] 					byteArray 	= fileAttachmentService.download(filePath, fileName, true);
			
			logger.debug(">>>> byteArray size = [" + byteArray.length + "]");
			
			if ( byteArray.length == 0 ) {
				
				logger.debug(">>>> ftp retriveFile size 0 !!");
				
				sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
				
				return;
			}
			
			bais = new ByteArrayInputStream(byteArray);
			
	    	send( response, fileName, getFileExt(fileName), bais, byteArray.length, fileStartPos );
		} catch (Exception e) {
			logger.error("Exception :: ", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다.(3)" );
        	
		}  finally {
			IOUtil.closeQuietly( bais );	bais = null;
        }
	}

	private String iso88591ToUtf8(String changeString) throws UnsupportedEncodingException {
		
		String result = "";
		
		try {

			if ( (changeString != null) && (changeString.length() > 0) ) {
				
				result = new String(changeString.getBytes(ISO_8859_1_ENCODING), UTF_8_ENCODING);
			}
		} catch (UnsupportedEncodingException e) {

			logger.error(">>>> 지원하지 않는 인코딩 타입입니다.", e);
			
			throw e;
		}
		
		return result;
	}
	
	private boolean sendError( HttpServletResponse response, int errorCode, String errorText ) {
    	
    	try {
    		
			response.sendError( errorCode, errorText );
		} catch ( IOException e ) {
			
			e.printStackTrace();
		}

    	return true;
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
}
