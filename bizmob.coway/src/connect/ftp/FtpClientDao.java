package connect.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.mcnc.common.util.IOUtil;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;

@Deprecated
public class FtpClientDao {
	
	private String hostName;
	private String userName;
	private String password;

	private FTPClient connectFtpServer() throws ConnectClientException {
		
		FTPClient ftpClient = new FTPClient();
		
		try {

			ftpClient.connect(hostName);
			
			int reply = ftpClient.getReplyCode();
			if(FTPReply.isPositiveCompletion(reply) == false) {
				ftpClient.disconnect();
				throw new ConnectClientException(hostName + " FTP Server refused connection.", ConnectClientExceptionCode.SOCKET_EXCEPTION);
			}
			
			ftpClient.login(userName, password);
			
			
		} catch (SocketException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.SOCKET_EXCEPTION);
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
		
		return ftpClient;
	}
	
	private boolean disconnectFtpServer(FTPClient ftpClient) throws ConnectClientException {
		try {
			if(ftpClient == null) {
				return false;
			}
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
		
		return true;
	}
	
	public byte[] downloadFile(String filePath, String fileName) throws ConnectClientException {
		
		/*
		 * 서버의 /public/테스트.txt 파일을 클라이언트의 C:\\Test\\테스트.txt 에 다운받는 예제
		 * File get_file = new File("C:\\Test\\테스트.txt");  
		 * OutputStream outputStream = new FileOutputStream(get_file);
		 * boolean result = ftpClient.retrieveFile("/public/테스트.txt", outputStream);
		 * outputStream.close();
		 */
		FTPClient ftpClient = null;
		ByteArrayOutputStream bos = null;
		
		try {
			
			ftpClient = connectFtpServer();
			
			bos = new ByteArrayOutputStream();
			ftpClient.retrieveFile(filePath + "/" + fileName, bos);
				
			return bos.toByteArray();			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bos);
			disconnectFtpServer(ftpClient);
		}

	}
	
	public boolean uploadFile(String filePath, String fileName, byte[] fileData) throws ConnectClientException {
		
		/*
		 * 클라이언트의 C:\\Test\\보내자.txt 파일을 서버의 /public/보내자.txt 에 업로드하는 예제
		 * 만일 서버에 이미 /public/보내자.txt 파일이 있다면 덮어쓰게 된다
		 * File put_file = new File("C:\\Test\\보내자.txt");
		 * inputStream = new FileInputStream(put_file);
		 * boolean result = ftpClient.storeFile("/public/보내자.txt", inputStream);
		 * inputStream.close();
		 */
		FTPClient ftpClient = null;
		ByteArrayInputStream bis = null;
		
		try {
			ftpClient = connectFtpServer();	
			ftpClient.makeDirectory(filePath);
			
			bis = new ByteArrayInputStream(fileData);
			
			ftpClient.storeFile(filePath + "/" + fileName, bis);
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bis);
			disconnectFtpServer(ftpClient);
		}

		return true;
	}
	
	public boolean appendFile(String filePath, byte[] fileData) {
		
		/*
		 * 위의 예제와 같은 기능을 하지만 이미 파일에 동일한 파일이 있으면 false를 반환하면서 실행하지 않는다
		 * File append_file = new File("C:\\Test\\더해라.txt");
		 * inputStream = new FileInputStream(append_file);
		 * boolean result = ftpClient.appendFile("/public/더해라.txt", inputStream);
		 * inputStream.close();
		 */
		return true;
	}
	
	public boolean deleteFile(String filePath, String fileName) throws ConnectClientException {
		
		/*
		 * 삭제할 파일을 선택한다    
		 * boolean result = ftpClient.deleteFile("/public/test.txt");
		 */
		
		FTPClient ftpClient = null;
		
		try {
			ftpClient = connectFtpServer();			
			
			ftpClient.deleteFile(filePath + "/" + fileName);
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			disconnectFtpServer(ftpClient);
		}

		return true;
	}	

	/*
	 * 디렉토리 생성
	 * /public 에 oops 디렉토리를 생성한다
	 * boolean result = ftpClient.makeDirectory("/public/oops");
	 * 
	 * (cf) ftpClient.sendCommand(FTPCommand.MAKE_DIRECTORY, "/public/oops");
	 */
	
	/*
	 * 디렉토리 변경
	 * 작업디렉토리를 /public 설정한 후 oops 디렉토리를 설정한다 (위의 예제와 비교해보라!)
	 * ftpClient.changeWorkingDirectory("/public");
	 * boolean result = ftpClient.makeDirectory("oops"); // /public/oops 절대경로를 적지 않아도 된다
	 */
	
	/*
	 * 파일 형태 설정
	 * ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	 * FTP.BINARY_FILE_TYPE, FTP.ASCII_FILE_TYPE, FTP.EBCDIC_FILE_TYPE, FTP.IMAGE_FILE_TYPE , FTP.LOCAL_FILE_TYPE 
	 * 이 값을 설정하지 않으면 디폴트는 ASCII 이다
	 */
	
	/*
	 * 전송 형태 설정
	 * ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
	 * FTP.BLOCK_TRANSFER_MODE, FTP.COMPRESSED_TRANSFER_MODE  
	 * 이값을 설정하지 않으면 디폴트는 FTP.STREAM_TRANSFER_MODE 이다
	 */
	
	/* 
	 * FTPFile 클래스
	 * FTPClient.listFiles() 메소드를 사용하면 현재 디렉토리의 파일 및 폴더 목록을 읽어올 수 있는데, 이 때 각 파일과 폴더는 FTPFile 객체로 표현된다. 
	 * FTPFile 클래스는 파일 및 폴더에 대한 정보를 제공하는데, 이와 관련된 메소드는 다음과 같다.
	 * 
	 * String getName()	파일의 이름을 구한다.
	 * String getSize()	파일의 크기를 구한다.
	 * boolean isDirectory()	폴더인 경우 true를 리턴한다.
	 * boolean isFile()	파일인 경우 true를 리턴한다.
	 * boolean isSymbolicLink()	심볼 링크인 경우 true를 리턴한다.
	 * String getLink()	심볼 링크인 경우 링크가 가리키는 파일의 이름을 리턴한다.
	 * Calendar getTimestamp()	생성날짜를 구한다.
	 * 
	 */
	
	/*
	FTPClient ftpClient = null;
	try {
	    ftpClient = new FTPClient();
	    ftpClient.setControlEncoding("euc-kr");  // 한글파일명 때문에 디폴트 인코딩을 euc-kr로 합니다
	    ftpClient.connect("user.chollian.net");  // 천리안 FTP에 접속합니다
	 
	   int reply = ftpClient.getReplyCode(); // 응답코드가 비정상이면 종료합니다
	   if (!FTPReply.isPositiveCompletion(reply)) {
	       ftpClient.disconnect();
	       System.out.println("FTP server refused connection.");    
	   } else {
	
	       System.out.print(ftpClient.getReplyString());  // 응답 메세지를 찍어봅시다
	 
	       ftpClient.setSoTimeout(10000);  // 현재 커넥션 timeout을 millisecond 값으로 입력합니다
	       ftpClient.login(username, password); // 로그인 유저명과 비밀번호를 입력 합니다
	       // 목록보기 구현
	       FTPFile[] ftpfiles = ftpClient.listFiles("/public");  // public 폴더의 모든 파일을 list 합니다
	       if (ftpfiles != null) {
	           for (int i = 0; i < ftpfiles.length; i++) {
	               FTPFile file = ftpfiles[i];
	               System.out.println(file.toString());  // file.getName(), file.getSize() 등등..
	           }
	       }
	       ftpClient.logout();
	   }
	} catch (Exception e) {
	   System.out.println(e);
	   e.printStackTrace(); 
	} finally {
	   if (ftpClient != null && ftpClient.isConnected()) {
	    try {
	         ftpClient.disconnect();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    }
	} 

 */
}
