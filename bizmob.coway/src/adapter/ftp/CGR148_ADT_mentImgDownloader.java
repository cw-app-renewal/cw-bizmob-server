package adapter.ftp;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mcnc.common.util.FileUtil;
import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.ftp.DownloadDO;
import connect.exception.ConnectClientException;
import connect.ftp.FtpClientService;
@Component
public class CGR148_ADT_mentImgDownloader extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR148_ADT_mentImgDownloader.class);

	@Autowired private FtpClientService ftpClientService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {
		
		int 			fileStartPos 	= Integer.parseInt(params.get("index").toString());
		StringBuffer 	logBuffer 		= new StringBuffer();
		
		logBuffer.append(this.getClass().getName() +  String.format(">>>> Start downloading from %s : %s", target, uid));
		
		HttpServletRequest 	request 	= (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response 	= (HttpServletResponse) params.get("HttpServletResponse");
		DownloadDO 			downloadDO 	= null;
		
		try {
			downloadDO = new DownloadDO(request);
			logBuffer.append(downloadDO.toString());
		} catch ( Exception e ) {
			logger.error("", e);
			response.sendError( HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
			return;
		}
		
		String filePath = CowayFtpFilePath.getMentFolder(downloadDO);
		String fileName = CowayFtpFileName.getMentImgName(downloadDO);
		
		logBuffer.append(">>>> Full File Path = [" + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName + "]");
		
		ByteArrayInputStream bais = null;
		
		try {
			long 	startTime 	= System.currentTimeMillis();
			byte[] 	fileByte 	= ftpClientService.downloadFile(filePath, fileName);
			
			logBuffer.append(">>>> File Down Time [" +  ((System.currentTimeMillis() - startTime) / 1000.0) + "초]");
			
			bais = new ByteArrayInputStream(fileByte);
			send( response, fileName, FileUtil.getExtension(fileName), bais, fileByte.length, fileStartPos );
			
		} catch ( ConnectClientException e ) {
			logger.error("ConnectClientException IO Exception !! :: ", e);
	    	response.sendError( HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
		} catch ( Exception e ) {
			logger.error("", e);
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "요청된 파일 조회시 오류가 발행했습니다." );
		} finally {
			logger.info(logBuffer.toString());
			IOUtil.closeQuietly(bais);	bais = null;
		}
	
	}
	
	
}





