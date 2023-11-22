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

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.util.FileAttachmentService;
import connect.exception.ConnectClientException;

@Component
public class CGR115_ADT_QAImageDownloader extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR115_ADT_QAImageDownloader.class);

	private static final String ISO_8859_1_ENCODING = "iso-8859-1";
	private static final String UTF_8_ENCODING = "utf-8";
	
	@Autowired FileAttachmentService fileAttachmentService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format(">>>> CGR115_ADT_QAImageDownloader Start downloading from %s : %s", target, uid));

		int fileStartPos = Integer.parseInt(params.get("index").toString());
		
		HttpServletRequest request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");
		
		String isThumbnail = "";
		String jobDate = "";
		String jobType = "";
		String jobSeq = "";
		String imgSeq = "";
		String orderNo = "";
		
		try {
			
			isThumbnail = iso88591ToUtf8(request.getParameter("thumbnail"));
			jobDate = iso88591ToUtf8(request.getParameter("job_dt"));
			jobType = iso88591ToUtf8(request.getParameter("job_tp"));
			jobSeq = iso88591ToUtf8(request.getParameter("job_seq"));
			imgSeq = iso88591ToUtf8(request.getParameter("img_seq"));
			orderNo = iso88591ToUtf8(request.getParameter("order_no"));
			
			logger.info(">>>> params fileStartPos = " + fileStartPos + ", isThumbnail = " + isThumbnail + 
					", \n jobDate = " + jobDate + ", jobType = " + jobType + 
					", \n jobSeq = " + jobSeq + ", imgSeq = " + imgSeq +
					", \n orderNo = " + orderNo);
		} catch ( Exception e ) {
			
			logger.error("", e);
			sendError(response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다.");
			
			return;
		}
		
		String filePath = CowayFtpFilePath.getWorkFolder(jobDate, jobType, jobSeq);
		String fileName = CowayFtpFileName.getWorkQaImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		logger.info(">>>> Full File Path = [" + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName + "]");
		
		ByteArrayInputStream bais = null;
		
		try {
			
			
			
			
			byte[] 					byteArray 	= fileAttachmentService.download(filePath, fileName, true);
			
			
			
			bais = new ByteArrayInputStream(byteArray);
			
			send( response, fileName, getFileExt(fileName), bais, byteArray.length, fileStartPos );
		} catch ( ConnectClientException e ) {
			
			logger.error("ConnectClientException IO Exception !! :: ", e);
			
	    	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
		} catch ( Exception e ) {
			
			logger.error("", e);
			
        	sendError( response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "요청된 파일 조회시 오류가 발행했습니다." );
		} finally {
			
			IOUtil.closeQuietly(bais);	bais = null;
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
