package connect.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.remote.session.Session;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.config.SmartConfig;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import connect.ftp.mms.CowayMmsFtpUtils;

public class FtpClientService extends FtpClientTemplate {
	
	private static final String _FOLDER_SEPARATOR = "/";
	
	private static final Logger logger = LoggerFactory.getLogger(FtpClientService.class);
	
	public byte[] downloadFile(String filePath, String fileName) throws ConnectClientException {
		
		ByteArrayOutputStream bos = null;
		Session<FTPFile> ftpSession = null;
		String path = "";
		String name = "";
		CowayMmsFtpUtils ftpUtils = null;
		
		try {
			
			path = filePath.replace("..", "");
			name = fileName.replace("..", "");
			
//			logger.debug("== downloadFile : getFtpSession() start ====");
//			ftpSession = getFtpSession();
//			logger.debug("== downloadFile : getFtpSession() end ====");
//			
//			String fullPath = path + _FOLDER_SEPARATOR + name;
//					
//			//이미지 유무 확인
//			bos = new ByteArrayOutputStream();
//			logger.debug("== downloadFile : new ByteArrayOutputStream() ====");
//				
//			ftpSession.read(fullPath, bos);
//			
//			logger.debug("== downloadFile : ftpSession.read(fullPath, bos) ====");
//			
//			return bos.toByteArray();

			///// 2014-07-21 kyryu 다운로드 모듈 기능 변경(pull 방식 -> 직접 연동) 
			String ftpHost = SmartConfig.getString("media.ftp.host", "10.101.1.57");
			String ftpUserName = SmartConfig.getString("media.ftp.username", "ftpuser_smt");
			String ftpPassword = SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#");
			
			//임시 파일 생성
			ftpUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
			
			return ftpUtils.ftpDownload(filePath, fileName);
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} catch (IllegalStateException e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (Exception e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bos);	bos = null;
			
			if(ftpSession != null)	{
				ftpSession.close();
				logger.info(">>>> downloadFile isFtpSession = " + ftpSession.isOpen());
			}
			
			ftpSession = null;
			ftpUtils = null;
		}
	}

	
	public boolean uploadFile(String filePath, String fileName, byte[] fileData) throws ConnectClientException {
		
		ByteArrayInputStream bis = null;
		Session<FTPFile> ftpSession = null;
		String path = "";
		String name = "";
		CowayMmsFtpUtils ftpUtils = null;
		
		try {
			path = filePath.replace("..", "");
			name = fileName.replace("..", "");
			
//			logger.debug("== uploadFile : getFtpSession() start ====");
//			ftpSession = getFtpSession();
//			logger.debug("== uploadFile : getFtpSession() end ====");
//			
//			bis = new ByteArrayInputStream(fileData);
//			logger.debug("== uploadFile : new ByteArrayInputStream() ====");
//			
//			StringTokenizer pathToken = new StringTokenizer(path, _FOLDER_SEPARATOR);
//			
//			String tPath = "";
//			while(pathToken.hasMoreTokens() == true) {
//				tPath += pathToken.nextToken() + _FOLDER_SEPARATOR;
//				ftpSession.mkdir(tPath);
//			}
//			
//			ftpSession.mkdir(path);
//			
//			logger.debug("== uploadFile : ftpSession.mkdir(path) ====");
//			
//			ftpSession.write(bis, path + _FOLDER_SEPARATOR + name);
//			
//			logger.debug("== uploadFile : ftpSession.write ====");

			///// 2014-07-21 kyryu upload 모듈 기능 변경(pull 방식 -> 직접 연동) 
			String ftpHost = SmartConfig.getString("media.ftp.host", "10.101.1.57");
			String ftpUserName = SmartConfig.getString("media.ftp.username", "ftpuser_smt");
			String ftpPassword = SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#");
			
			ftpUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
			boolean result = ftpUtils.ftpUpload(path, name, fileData);
			
			return result;
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} catch (IllegalStateException e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (Exception e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bis);	bis = null;
			
			if(ftpSession != null)	{
				ftpSession.close();
				logger.info(">>>> uploadFile isFtpSession = " + ftpSession.isOpen());
			}
			
			ftpSession = null;
			ftpUtils = null;
		}

	}
	
	public boolean deleteFile(String filePath, String fileName) throws ConnectClientException {
		
		Session<FTPFile> ftpSession = null;
		String path = "";
		String name = "";
		CowayMmsFtpUtils ftpUtils = null;
		
		try {
			path = filePath.replace("..", "");
			name = fileName.replace("..", "");
			
//			logger.debug("== deleteFile : getFtpSession() start ====");
//			ftpSession = getFtpSession();
//			logger.debug("== deleteFile : getFtpSession() end ====");
//
//			return ftpSession.remove(path + _FOLDER_SEPARATOR + name);

			///// 2014-07-21 kyryu upload 모듈 기능 변경(pull 방식 -> 직접 연동) 
			String ftpHost = SmartConfig.getString("media.ftp.host", "10.101.1.57");
			String ftpUserName = SmartConfig.getString("media.ftp.username", "ftpuser_smt");
			String ftpPassword = SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#");
			
			ftpUtils = new CowayMmsFtpUtils(ftpHost, ftpUserName, ftpPassword);
			return ftpUtils.ftpDeleteFile(path, name);
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} catch (IllegalStateException e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (Exception e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			if(ftpSession != null)	{
				ftpSession.close();
				logger.info(">>>> deleteFile isFtpSession = " + ftpSession.isOpen());
			}
			
			ftpSession = null;
			ftpUtils = null;
		}
	}	
	
	
	public List<String> viewFileList(String filePath) throws ConnectClientException {
		
		Session<FTPFile> ftpSession = null;
		
		try {
			
			ftpSession = getFtpSession();
			
			FTPFile[] ftpFileArray = ftpSession.list(filePath);
			
			List<String> listArray = new ArrayList<String>();
			
			for(int i=0 ; i<ftpFileArray.length ; i++) {
				String name = ftpFileArray[i].getName();
				listArray.add(name);
			}
			
			return listArray;
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} catch (IllegalStateException e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (Exception e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			if(ftpSession != null)	{
				ftpSession.close();
				logger.info(">>>> viewFileList isFtpSession = " + ftpSession.isOpen());
			}			
			
			ftpSession = null;
		}
		
	}
	
}
