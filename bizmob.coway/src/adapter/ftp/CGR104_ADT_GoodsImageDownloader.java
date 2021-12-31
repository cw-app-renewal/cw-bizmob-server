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
import connect.exception.ConnectClientException;

@Component
public class CGR104_ADT_GoodsImageDownloader extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGR104_ADT_GoodsImageDownloader.class);
		
	@Autowired FileAttachmentService fileAttachmentService;
	
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGR104_ADT_GoodsImageDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

		//goods 사진의 경우, 
		//file_name = goods_cd (제품코드)
		//file_path = 4 (제품사진구분) 
		String reqFileName = null;
		String goodsCode = null;
		String isThumbnail = null;
		try {
			reqFileName = new String(request.getParameter("file_name").getBytes("iso-8859-1"), "utf-8");
			goodsCode = new String(request.getParameter("goods_cd").getBytes("iso-8859-1"), "utf-8");
			isThumbnail = new String(request.getParameter("thumbnail").getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 정보가 잘못되었습니다." );
        	return;
		}
	    
	    ByteArrayInputStream bais = null;
		
	    try {
		
	    	String filePath = getFilePath(goodsCode, target);
	    	String fileName = CowayFtpFileName.getGoodsImgName(goodsCode, isThumbnail);
	    	

	    	
			byte[] 					byteArray 	= fileAttachmentService.download(filePath, fileName, true);
					  
	        bais = new ByteArrayInputStream(byteArray);
       
	    	send( response, fileName, getFileExt(fileName), bais, byteArray.length, fileStartPos );
	    } catch (ConnectClientException e) {
	    	logger.error("ConnectClientException IO Exception !! :: ", e);
	    	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
	    	
		} catch (Exception e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "요청된 파일 조회시 오류가 발행했습니다." );
        	
		}  finally {
			IOUtil.closeQuietly( bais );	bais = null;
			
        }
	}
	
	/**
	 * goodsImage (1,2,3,4,5....) 그 외에 따른 파일 경로 가져오는 메소드
	 * @param goodsCode : 제품 코드
	 * @param target : goodsImage (1,2,3,4,5....) 그 외에..  
	 * @return : 파일 이름을 제외한 경로
	 * @description
	 */
	private String getFilePath(String goodsCode, String target) {
		
		final String IMAGE = "Image";

		logger.info(">>>> getFilePath = " + goodsCode + ", target = " + target);
		
		String result = CowayFtpFilePath.getGoodsFolder(goodsCode);
		
		try {
			
			String goodsCodeFolderName = target.replace(IMAGE, "");
			
			result = CowayFtpFilePath.getCommonGoodsFolder(goodsCode, goodsCodeFolderName);
			
			logger.info(">>>> goodsCodeFolderName = " + goodsCodeFolderName + ", result = " + result);
		} catch ( NullPointerException e ) {
			
			logger.error(">>>> target 정보가 잘못되었습니다. | " + target, e);
			
			throw e;
		}
			
		return result;
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
