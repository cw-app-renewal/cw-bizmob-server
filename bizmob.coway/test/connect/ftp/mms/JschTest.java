package connect.ftp.mms;

/*import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
*/
@Deprecated
public class JschTest {

	/*@Test
	public void test() {
		
		try {
			String ftpHost = "10.101.5.74";
			int ftpPort = 22;
			String ftpUserName = "wjsms";
			String ftpPassword = "!dnvwjsms1";
			String ftpRemoteDir = "/oradata/WJSMSEXCEL/img/";
			String fileToTransmit = "D://test/test.txt";
			
			//first create a jsch session
			JSch jsch = new JSch();
			Session session = null;
			Channel channel = null;
			ChannelSftp c = null;
		
			//now connect and sftp to the sftp server
			try {
				
				//create a session sending through our username and password
				session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
				session.setPassword(ftpPassword);
				
				//setup strict HostKeyChecking to no so we don't get the unknown host key exception
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.connect();
				
				//open the sftp channel
				channel = session.openChannel("sftp");
				channel.connect();
				c = (ChannelSftp) channel;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//change to the remote directory
			c.cd(ftpRemoteDir);
		
			//send the file we generated
			try {
				File f = new File(fileToTransmit);
				c.put(new FileInputStream(f), f.getName());
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
			//get the list of files in the remote server directory
			Vector files = c.ls(ftpRemoteDir);
			
			//log if we have nothing to download
			if(files.size() == 0) {
				System.out.println("No files are available for download");
			} else {
				
				for(int i=0 ; i<files.size() ; i++) {
					com.jcraft.jsch.ChannelSftp.LsEntry lsEntry = (com.jcraft.jsch.ChannelSftp.LsEntry)files.get(i);
					
					if(!lsEntry.getFilename().equals(".") && !lsEntry.getFilename().equals("..")){
						String outputFileName = "" + lsEntry.getFilename();
						
						//get the wirte and write it to our local file system
						File f = new File(outputFileName);
						c.get(lsEntry.getFilename(), new FileOutputStream(f));
					}
					
				}
			}
		
			//disconnect from the ftp server
			try {
				c.quit();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
}
