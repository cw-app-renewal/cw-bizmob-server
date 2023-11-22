package connect.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestHttpClient {

	
	public void ConnectMethod() throws ClientProtocolException, IOException, RuntimeException {
		

		HttpClient httpClient = new DefaultHttpClient();
		
		//parpare a request object
		HttpGet httpGet = new HttpGet("http://naver.com/");
		
		//execute the request
		HttpResponse response = httpClient.execute(httpGet);
		
		//examine the response status
		System.out.println(response.getStatusLine());
		
		//get hold of the response entity
		HttpEntity entity = response.getEntity();
		
		//if the response does not enclose an entity, there is no need to worry about connection release
		if(entity != null) {
			InputStream instream = entity.getContent();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				
				//do something useful with the response
				System.out.println(reader.readLine());
			} catch (IOException e) {
				//in case of an IOException the connection will be released back to the connection manager automatically
				throw e;
			} catch (RuntimeException e) {
				//in case of an unexpected exception you may want ti abort the HTTP request in order to shut down the underlying
				//connection and release it back to the connection manager
				httpGet.abort();
				throw e;
			} finally {
				//close the input stream will trigger connection release
				instream.close();
			}
			
			//when HttpClient instance is no longer needed, shut down the connection manager to ensure
			//immediate deallocation of all system resources
			httpClient.getConnectionManager().shutdown();
			
		}
		
	}
	
	
	@Test
	public void testConnect() {
		
		try { 
			this.ConnectMethod();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
