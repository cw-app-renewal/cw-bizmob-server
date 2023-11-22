package connect.ftp.mms;

import com.mcnc.common.util.IOUtil;
import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.session.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Deprecated
public class CowayMmsFtpsService extends CowayMmsFtpsTemplate {

	private static final String _FOLDER_SEPARATOR = "/";
	
	public byte[] downloadImageFile(String filePath, String fileName) throws ConnectClientException {
		
		ByteArrayOutputStream bos = null;
		try {
			
			Session<FTPFile> ftpSession = getCowayMmsFtpsSession();
			
			String fullPath = filePath + _FOLDER_SEPARATOR + fileName;
					
			//이미지 유무 확인
			bos = new ByteArrayOutputStream();
				
			ftpSession.read(fullPath, bos);
			
			return bos.toByteArray();
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bos);
		}
		
	}

	
	public boolean uploadImageFile(String filePath, String fileName, byte[] fileData) throws ConnectClientException {
		
		ByteArrayInputStream bis = null;
		
		try {
			Session<FTPFile> ftpSession = getCowayMmsFtpsSession();
			
			bis = new ByteArrayInputStream(fileData);
			
			StringTokenizer pathToken = new StringTokenizer(filePath, _FOLDER_SEPARATOR);
			
			String path = "";
			while(pathToken.hasMoreTokens() == true) {
				path += pathToken.nextToken() + _FOLDER_SEPARATOR;
				ftpSession.mkdir(path);
			}
			
			ftpSession.mkdir(filePath);
			
			ftpSession.write(bis, filePath + _FOLDER_SEPARATOR + fileName);
			
			return true;
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			IOUtil.closeQuietly(bis);
		}

	}
	
	public boolean deleteImageFile(String filePath, String fileName) throws ConnectClientException {
		
		
		try {
			Session<FTPFile> ftpSession = getCowayMmsFtpsSession();	

			return ftpSession.remove(filePath + _FOLDER_SEPARATOR + fileName);
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		} finally {
			
		}
	}	
	
	
	public List<String> viewImageFileList(String filePath) throws ConnectClientException {
		
		try {
			
			Session<FTPFile> ftpSession = getCowayMmsFtpsSession();
			
			FTPFile[] ftpFileArray = ftpSession.list(filePath);
			
			List<String> listArray = new ArrayList<String>();
			
			for(int i=0 ; i<ftpFileArray.length ; i++) {
				String name = ftpFileArray[i].getName();
				listArray.add(name);
			}
			
			return listArray;
			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
		
	}
	
}
