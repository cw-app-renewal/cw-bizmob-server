package connect.ftp;


import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;


public class FtpClientTemplate {
	
	DefaultFtpSessionFactory ftpSessionFactory;
	
	public DefaultFtpSessionFactory getFtpSessionFactory() {
		return ftpSessionFactory;
	}
	public void setFtpSessionFactory(DefaultFtpSessionFactory ftpSessionFactory) {
		this.ftpSessionFactory = ftpSessionFactory;
	}
	
	public Session<FTPFile> getFtpSession() throws Exception {
		
		Session<FTPFile> session = ftpSessionFactory.getSession();
		
		return session;
	}
	
	
	
}
