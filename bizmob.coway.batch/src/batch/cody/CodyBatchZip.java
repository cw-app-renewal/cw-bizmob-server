package batch.cody;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.mcnc.common.util.CompressUtil;
import com.mcnc.common.util.IOUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;

public class CodyBatchZip {

	private ILogger logger = LoggerService.getLogger(CodyBatchZip.class);
	
	private static final String COM_DATABASE_CODY_PATH = "/SYNC/CODY/CODY/WC_COM";
	private static final String COM_DATABASE_ING_PATH = "/SYNC/CODY/ING/WC_COM";
	//private static final String BOM_DATABASE_PATH = "/SYNC/CODY/CODY/WC_BOM";
	//private static final String BOM_DATABASE_ING_PATH = "/SYNC/CODY/ING/WC_BOM";
	
	//private static final String CODY_DATABASE_PATH = "/SYNC/CODY/CODY/";
	
	CodyBatch codyBatch;
	
	public CodyBatch getCodyBatch() {
		return codyBatch;
	}
	public void setCodyBatch(CodyBatch codyBatch) {
		this.codyBatch = codyBatch;
	}

	public boolean codeBatchDatabase() {
		try {
			//sap -> ing db
			codyBatch.createDatabase();
			
			// com ing db -> origin
			String smartHome = System.getProperty("SMART_HOME");
			File comIngDb = new File(smartHome + COM_DATABASE_ING_PATH);
			File comOriginDb = new File(smartHome + COM_DATABASE_CODY_PATH);
			
			copyFileChannel(comIngDb, comOriginDb);
			
			//bom ing db -> origin
			//File bomIngDb = new File(smartHome + BOM_DATABASE_ING_PATH);
			//File bomOriginDb = new File(smartHome + BOM_DATABASE_PATH);
			
			//copyFileChannel(bomIngDb, bomOriginDb);
			
			//compress folder
			//File zipFile = new File(smartHome+ CODY_DATABASE_PATH);
			
			//CompressUtil.zip(zipFile);
			
			//compress com db file
			CompressUtil.zip(comOriginDb);
			
			//compress bom db file
			//CompressUtil.zip(bomOriginDb);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return true;
	}
	
	private void copyFileStream(File origin, File target) {
		
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			
			in = new FileInputStream(origin);
			out = new FileOutputStream(target);
			
			int bytesRead = 0;
			byte[] buffer= new byte[1024];
			
			while((bytesRead = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			IOUtil.closeQuietly(in);
			IOUtil.closeQuietly(out);
		}
	}
	
	private void copyFileBuffer(File origin, File target) {
		
		FileInputStream in = null;
		FileOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			
			in = new FileInputStream(origin);
			bis = new BufferedInputStream(in);		
			out = new FileOutputStream(target);
			bos = new BufferedOutputStream(out);
			
			int bytesRead = 0;
			byte[] buffer= new byte[1024];
			
			while((bytesRead = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			IOUtil.closeQuietly(bis);
			IOUtil.closeQuietly(bos);			
			IOUtil.closeQuietly(in);
			IOUtil.closeQuietly(out);
		}
	}

	private void copyFileChannel(File origin, File target) {
		
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		
		try {
			
			in = new FileInputStream(origin);
			out = new FileOutputStream(target);

			fcin = in.getChannel();
			fcout = out.getChannel();
			
			long size = fcin.size();
			
			fcin.transferTo(0, size, fcout);
			
			logger.debug("db file channel copy !! fcin=[" + fcin + "], fcout=[" + fcout + "]" );
			
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			//try { if(fcin != null) fcin.close(); } catch (IOException e) { }
			//try { if(fcout != null) fcout.close(); } catch (IOException e) { }
			IOUtil.closeQuietly(fcin);
			IOUtil.closeQuietly(fcout);
			IOUtil.closeQuietly(in);
			IOUtil.closeQuietly(out);
		}
	}

}
