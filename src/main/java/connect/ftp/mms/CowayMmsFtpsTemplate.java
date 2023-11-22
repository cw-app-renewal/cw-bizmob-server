package connect.ftp.mms;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.ftp.session.DefaultFtpsSessionFactory;

@Deprecated
public class CowayMmsFtpsTemplate {

	DefaultFtpsSessionFactory cowayMmsFtpsSessionFactory;
	
	public DefaultFtpsSessionFactory getCowayMmsFtpsSessionFactory() {
		return cowayMmsFtpsSessionFactory;
	}

	public void setCowayMmsFtpsSessionFactory(
			DefaultFtpsSessionFactory cowayMmsFtpsSessionFactory) {
		this.cowayMmsFtpsSessionFactory = cowayMmsFtpsSessionFactory;
	}

	public Session<FTPFile> getCowayMmsFtpsSession() {
		
		Session<FTPFile> session = cowayMmsFtpsSessionFactory.getSession();
	
		return session;
	}
	
}
