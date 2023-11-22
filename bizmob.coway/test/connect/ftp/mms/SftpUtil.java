package connect.ftp.mms;

/*import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;*/

@Deprecated
public class SftpUtil {

	/*private String sftpHost;
	private int sftpPort;
	private String sftpUser;
	private String sftpPassword;
	private String sftpDir;

	public SftpUtil(String sftpHost, int sftpPort, String sftpUser, String sftpPassword, String sftpDir) {
		super();
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.sftpUser = sftpUser;
		this.sftpPassword = sftpPassword;
		this.sftpDir = sftpDir;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

	public String getSftpUser() {
		return sftpUser;
	}

	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpDir() {
		return sftpDir;
	}

	public void setSftpDir(String sftpDir) {
		this.sftpDir = sftpDir;
	}

	public boolean moveFileToDir(String localFilePath) {
		return moveFileToDir(localFilePath, null, null, true);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath) {
		return moveFileToDir(localFilePath, remoteDirPath, null, true);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath, String remoteFileName) {
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName, true);
	}

	public boolean copyFileToDir(String localFilePath) {
		return moveFileToDir(localFilePath, null, null, false);
	}

	public boolean copyFileToDir(String localFilePath, String remoteDirPath) {
		return moveFileToDir(localFilePath, remoteDirPath, null, false);
	}

	public boolean copyFileToDir(String localFilePath, String remoteDirPath, String remoteFileName) {
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName, false);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath, String remoteFileName, boolean isDelete) {
		boolean returnResult = false;
		boolean deleteSuccess = false;
		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			
			session = jsch.getSession(this.sftpUser,this.sftpHost,this.sftpPort);
			session.setPassword(this.sftpPassword);
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			
			channelSftp = (ChannelSftp)channel;
			if(null != remoteDirPath)
				channelSftp.cd(remoteDirPath);
			else
				channelSftp.cd(this.sftpDir);

			File f = new File(localFilePath);
			String fileName = f.getName();
			if(null != remoteFileName && remoteFileName.length() > 0)
				fileName = remoteFileName;

			channelSftp.put(new FileInputStream(f), fileName);
			
			//Disconnecting the channel
			channel.disconnect();
			
			//Disconnecting the session
			session.disconnect();
			if(isDelete){
				deleteSuccess = f.delete();
			}else{
				deleteSuccess = true;
			}
			returnResult = deleteSuccess;
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return returnResult;
	}*/
}
