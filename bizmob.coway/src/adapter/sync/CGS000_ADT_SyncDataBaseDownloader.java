package adapter.sync;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * http://10.131.16.124:8080/bizmob/download/SyncDatabase/1?mode=1&file_name=WD_WORK.db&file_path=CSDR
 */

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

@Deprecated
public class CGS000_ADT_SyncDataBaseDownloader  extends AbstractDownloader implements Downloader {

	private static final Logger logger = LoggerFactory.getLogger(CGS000_ADT_SyncDataBaseDownloader.class);
		
	@Override
	public void download(String target, String uid, Map<String, Object> params) throws Exception {

		logger.info(String.format("======== CGS000_ADT_SyncDataBaseDownloader Start downloading from %s : %s ", target, uid));
		
		int fileStartPos = Integer.parseInt(params.get("index").toString());
		HttpServletRequest  request = (HttpServletRequest) params.get("HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");

	    String fileName = new String(request.getParameter("file_name").getBytes("iso-8859-1"), "utf-8");
	    String filePath = new String(request.getParameter("file_path").getBytes("iso-8859-1"), "utf-8");
	    if(filePath.equals("") == true) {
	    	filePath = "sync/CSDR";
	    }
	    
	    logger.debug("sync file name :: " + fileName);
	    logger.debug("sync file path :: " + filePath);
	    
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    ByteArrayInputStream bais = null;
	    
		try {
			
			String smartHome = System.getProperty("SMART_HOME");
			String fileFullPath = smartHome + "/" + filePath + "/" + fileName;
			
			fis = new FileInputStream(fileFullPath);
			bis = new BufferedInputStream(fis);
			
	        byte[] fileData =  IOUtil.toByteArray( bis );
	        bais = new ByteArrayInputStream(fileData);
       
	    	send( response, fileName, getFileExt(fileName), bais, fileData.length, fileStartPos );
		} catch (Exception e) {
			logger.error("", e);
        	sendError( response, HttpServletResponse.SC_NOT_FOUND, "요청된 파일 정보가 없습니다." );
		}  finally {
			IOUtil.closeQuietly( bais );
        	IOUtil.closeQuietly( bis );
        	IOUtil.closeQuietly( fis );
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
