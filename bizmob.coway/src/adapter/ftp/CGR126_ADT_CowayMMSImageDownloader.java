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

import common.ftp.CowayFtpFilePath;
import connect.ftp.FtpClientService;

@Component
public class CGR126_ADT_CowayMMSImageDownloader extends AbstractDownloader implements Downloader {

	private ILogger logger = LoggerService.getLogger(CGR126_ADT_CowayMMSImageDownloader.class);
		
	@Autowired
	FtpClientService ftpClientService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGR126_ADT_CowayMMSImageDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		//install 사진의 경우, 
		//file_name = tmpOrderNo (임시고객주문번호).jpg
		//file_path = 3 (고객메모사진구분) 
		//file_seq = 1/2/3 (이미지 순번)
		String reqFileName = null;
		
		try {
			//reqFileName = new String(request.getParameter("file_name").getBytes(), "utf-8");
			reqFileName = request.getParameter("file_name");

		} catch (NullPointerException e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다." );
        	return;
		}
		String filePath = CowayFtpFilePath.getCowayMMSFolder();
		String fileName = reqFileName; // 확장자를 포함한 이미지 명칭
		
		filePath = filePath.replace("..", "");
		fileName = fileName.replace("..", "");
		
		logger.debug("download full file path = [" + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName + "]");
		
	    ByteArrayInputStream bais = null;
		
	    try {
		
			//ftp
			byte[] byteArray = ftpClientService.downloadFile(filePath, fileName);
		  
	    	// ftp 이미지 다운로드 : charset 조정
//			String ftpHost = SmartConfig.getString("media.ftp.host", "10.101.1.57");
//			String ftpUserName = SmartConfig.getString("media.ftp.username", "ftpuser_smt");
//			String ftpPassword = SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#");
//			
//			CowayMmsFtpUtils ftpDowloadUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
//			//ftpDowloadUtils.setFtpCharset("euc-kr");
//			byte[] byteArray = ftpDowloadUtils.ftpDownload(filePath, fileName);
			////
			
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
