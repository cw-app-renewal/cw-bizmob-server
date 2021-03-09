package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

import connect.ftp.FtpClientService;

@Component
public class CGR116_ADT_BbsSmtImageDownloader  extends AbstractDownloader implements Downloader {

	private ILogger logger = LoggerService.getLogger(CGR116_ADT_BbsSmtImageDownloader.class);

	@Autowired
	private FtpClientService ftpClientService;
	
	private static final String ISO_8859_1_ENCODING = "iso-8859-1";
	private static final String UTF_8_ENCODING = "utf-8";
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format(">>>> CGR116_ADT_BbsSmtImageDownloader Start downloading from %s : %s", target, uid));

		int fileStartPos = Integer.parseInt(params.get("index").toString());
		
		HttpServletRequest request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");
		
		String fileFullPath = "";
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
			
			logger.info(">>>> params = " + fileFullPath + ", " + fileName + ", filePath = " + filePath);
		} catch ( Exception e ) {
			
			logger.error("", e);
			sendError(response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다.");
			
			return;
		}
		
		FTPClient ftp = null;
	    FileOutputStream fos = null;
	    ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		
	    try {

	    	ftp = new FTPClient();
	    	
	    	logger.debug("current1 ftp encoding = " + ftp.getControlEncoding());
	    	
	    	ftp.setControlEncoding(SmartConfig.getString("media.ftp.encoding.type", "euc-kr"));
	    	
	    	logger.debug("current2 ftp encoding = " + ftp.getControlEncoding());
	    	
	    	ftp.connect(SmartConfig.getString("media.ftp.host", "10.101.1.57"));
	    	ftp.login(SmartConfig.getString("media.ftp.username", "ftpuser_smt"), SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#"));
	    	ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory("/");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);  
			
			baos = new ByteArrayOutputStream();
			
			ftp.changeWorkingDirectory(filePath);			
		
			if( ftp.retrieveFile(fileName, baos) == true ){
				
				logger.debug(">>>> ftp retrive file download success !!");		
			} else {
				
				logger.debug(">>>> ftp retrive file download fail !!");
			}

			byte[] byteArray = baos.toByteArray();
			
			logger.debug(">>>> byteArray size = [" + byteArray.length + "]");
			
			if ( byteArray.length == 0 ) {
				
				logger.debug(">>>> ftp retriveFile size 0 !!");
				
				sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
				
				return;
			}
			
			bais = new ByteArrayInputStream(byteArray);
			
			ftp.logout();
	    	
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
				e.printStackTrace();
			}
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
