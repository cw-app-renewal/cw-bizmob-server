package connect.ftp.coway;

import java.io.ByteArrayOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.config.SmartConfig;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Deprecated
public class CowayFtpClient {

	private static final Logger logger = LoggerFactory.getLogger(CowayFtpClient.class);

	
	public byte[] downloadFile(String filePath, String fileName) throws ConnectClientException {
		
		ByteArrayOutputStream baos = null;
		FTPClient ftp = null;
		
		try {
			ftp = new FTPClient();
			ftp.setControlEncoding(SmartConfig.getString("media.ftp.encoding.type", "euc-kr"));
			ftp.connect(SmartConfig.getString("media.ftp.host", "10.101.1.57"));
			if(ftp.isConnected() == false) {
				logger.debug("FTP 연결 실패!!");
				throw new ConnectClientException("FTP 연결 오류", ConnectClientExceptionCode.IO_EXCEPTION);
			}
			
			ftp.login(SmartConfig.getString("media.ftp.username", "ftpuser_smt"), SmartConfig.getString("media.ftp.passowrd", "tm!ak@xm#"));
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory("/");
			ftp.setFileType(FTP.BINARY_FILE_TYPE); 
			
			baos = new ByteArrayOutputStream();
			
			ftp.changeWorkingDirectory(filePath);	
			if(ftp.retrieveFile(fileName, baos) == true){
				logger.debug("FTP 파일 검색 성공 !!");
			} else {
				throw new ConnectClientException(fileName + " 파일을 찾을 수 없습니다.", ConnectClientExceptionCode.IO_EXCEPTION);
			}
			
			ftp.logout();
			
			return baos.toByteArray();

		} catch (Exception e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly( baos );
			
			try {
				if(ftp != null && ftp.isConnected()) {
					ftp.disconnect();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}
	
	
}
