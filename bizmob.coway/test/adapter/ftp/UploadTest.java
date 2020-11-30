package adapter.ftp;

import java.io.File;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class UploadTest {

	@Test
	public void testUpload() {
		
		HttpClient httpClient = new DefaultHttpClient();
		String uid = String.valueOf(new Random().nextLong());
		
		try {
			
			HttpPost httpPost = new HttpPost("http://localhost:8080/bizmob/upload/");
			
			StringBody name = new StringBody(uid);
			StringBody fileName = new StringBody("123456.jpg");
			FileBody bin = new FileBody(new File("D:/123456.jpg"));
			
			MultipartEntity reqEntity = new MultipartEntity();
			
			reqEntity.addPart("name", name);
			reqEntity.addPart("fileNmae", fileName);
			reqEntity.addPart(uid, bin);
			
			httpPost.setEntity(reqEntity);
			
			HttpResponse response = httpClient.execute(httpPost);
			
			System.out.println(response.getStatusLine());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
