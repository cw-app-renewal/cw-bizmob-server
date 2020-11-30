package connect.ftp.mms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;

import connect.exception.ConnectClientException;
import connect.ftp.FtpClientService;

public class CowayMmsFtpUtils {

	private FTPClient client;
	private String ftpHost;
	private String ftpUserName;
	private String ftpPassword;
	private String ftpCharset = null;
	
	private ILogger logger = LoggerService.getLogger(CowayMmsFtpUtils.class);
	
	public CowayMmsFtpUtils() {}
	
	public CowayMmsFtpUtils(String host, String user, String password) {
		this.ftpHost = host;
		this.ftpUserName = user;
		this.ftpPassword = password;
		
	}
	
	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpCharset() {
		return ftpCharset;
	}

	public void setFtpCharset(String ftpCharset) {
		this.ftpCharset = ftpCharset;
	}

	public FTPClient connect() throws IOException {
				
		FTPClient ftp = new FTPClient();
		
		ftp.setDataTimeout(10000);
		ftp.setConnectTimeout(10000);
		
		if( getFtpCharset() != null) 
			ftp.setControlEncoding(getFtpCharset());
		
		ftp.connect(this.ftpHost);
		ftp.login(this.ftpUserName, this.ftpPassword);
		ftp.enterLocalPassiveMode();
		ftp.changeWorkingDirectory("/");
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		
		return ftp;		
	}
	
	public void disconnect(FTPClient ftp) throws IOException {
		
		if(ftp == null) {
			return;
		}
		
		if(ftp.isConnected() == true) {
			ftp.logout();
			ftp.disconnect();
		}
		
	}
	
	public boolean ftpUpload(String filePath, String fileName, byte[] fileData) throws Exception {
		
		InputStream bais = null;
		
		try {
			
			if(client != null) {		
				disconnect(this.client);		
			} 
			
			logger.debug("== ftpUpload : connect() ====");
			client = connect();
			
			//make directory
			StringTokenizer pathToken = new StringTokenizer(filePath, "/");
			String path = "";
			while(pathToken.hasMoreTokens() == true) {
				path += pathToken.nextToken() + "/";
				client.makeDirectory(path);
			}
			
			logger.debug("== ftpUpload : new ByteArrayInputStream() ====");
			bais = new ByteArrayInputStream(fileData);
			
			logger.debug("== ftpUpload : client.storeFile() start====");
			boolean result = client.storeFile(filePath + "/" + fileName, bais);
			logger.debug("== ftpUpload : client.storeFile() end, result =[" +result+"]====");
			
			return result;
		} catch (Exception e) {
			throw e;
		
		} finally {
			IOUtil.closeQuietly(bais);	bais = null;
			disconnect(client);			client = null;
		}
		
	}
	
	public byte[] ftpDownload(String filePath, String fileName) throws Exception {
		
		ByteArrayOutputStream bos = null;
		
		try {
			
			if(client != null) {		
				disconnect(this.client);		
			} 
			
			logger.debug("== ftpDownload : connect() ====");
			client = connect();
						
			logger.debug("== ftpDownload : new ByteArrayOutputStream() ====");
			//이미지 유무 확인
			bos = new ByteArrayOutputStream();

			logger.debug("== ftpDownload : client.retrieveFile() start ====");
			boolean result = client.retrieveFile(filePath + "/" + fileName, bos);
			logger.debug("== ftpDownload : client.retrieveFile() end, result =[" +result+"] ====");
			
			return bos.toByteArray();
			
		} catch (Exception e) {
			throw e;
		
		} finally {
			IOUtil.closeQuietly(bos);	bos = null;
			disconnect(client);			client = null;
		}

		
	}

	public boolean ftpDeleteFile(String filePath, String fileName) throws Exception {
		
		try {
			
			if(client != null) {		
				disconnect(this.client);		
			} 
			
			logger.debug("== ftpDeleteFile : connect() ====");
			client = connect();
			
			logger.debug("== ftpDeleteFile : client.deleteFile() start====");
			boolean result = client.deleteFile(filePath + "/" + fileName);
			logger.debug("== ftpDeleteFile : client.deleteFile() end, result =[" +result+"] ====");
			
			return result;
			
		} catch (Exception e) {
			throw e;
		
		} finally {
			disconnect(client);			client = null;
		}
		
	}
}




