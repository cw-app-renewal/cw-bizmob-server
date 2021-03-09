package common.util;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import common.BizmobUtil;

public class RestTest {

	
	@Test
	public void testSearchHttpsConnection() {
		try {
			
			String serverUrl = "https://devapis.coway.co.kr/acupi/v1/1024735774/";
			String originKey = "c3179fb0-6763-11ea-ab12-0800200c9a66";
			
			HttpsURLConnection httpsURLConnection =  BizmobUtil.getHttpsURLConnection(serverUrl);
			httpsURLConnection.setRequestProperty("origin_key", originKey);
			httpsURLConnection.connect();
			
			if (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				String 		resBody 		= IOUtils.toString( httpsURLConnection.getInputStream() );
				
				System.out.println(resBody);
			} else {
				System.out.println("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseCode()));
				System.out.println("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseMessage()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testSearch() {
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			String serverUrl = "https://devapis.coway.co.kr/acupi/v1/1024735774/";
			
			final HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("origin_key", "08a8f639-7c68-40d9-8c24-53d2a7f250da");
			
			final HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<JsonNode> result = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, JsonNode.class);
			JsonNode responseNode = result.getBody();
			System.out.println(responseNode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testHistory() {
		
		String version = "v1";
		String cid = "1024735774";
		String from = "20200101";
		String to = "20200302";
		
		try {
			
			
			RestTemplate restTemplate = new RestTemplate();
			String serverUrl = "https://devapis.coway.co.kr";
			serverUrl += "/acupi/" + version + "/" + cid + "/history?start_date=" + from + "&end_date=" + to;
			
			final HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("origin_key", "08a8f639-7c68-40d9-8c24-53d2a7f250da");
			
			final HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			ResponseEntity<JsonNode> result = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, JsonNode.class);
			JsonNode responseNode = result.getBody();
			System.out.println(responseNode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegister() {
		
		String version = "v1";
		String reqPayload = "{\"cid\" : \"1024735774\", \"uid\" : \"0105921650\", \"app_no\" : \"\", \"status\" : \"AGR\", \"agree_cu\" : \"N\", \"agree_pi\" : \"N\", \"agree_m\" : \"N\", \"phone_yn\" : \"N\",\"sms_yn\" : \"N\",\"edm_yn\" : \"N\", \"dm_yn\" : \"N\",\"pic_name\" : \"홍길동\",\"pic_emplno\" : \"21001234\"}";
		try {
			
			
			String serverUrl = "https://devapis.coway.co.kr";
			serverUrl += "/acupi/" + version;
			String originKey = "08a8f639-7c68-40d9-8c24-53d2a7f250da";
			
			HttpsURLConnection httpsURLConnection =  BizmobUtil.getHttpsURLConnection(serverUrl);
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setRequestProperty("origin_key", originKey);
			httpsURLConnection.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(httpsURLConnection.getOutputStream());
			dos.writeBytes(reqPayload);
			dos.flush();
			dos.close();
			
			httpsURLConnection.connect();
			
			if (HttpURLConnection.HTTP_OK <= httpsURLConnection.getResponseCode() &&  httpsURLConnection.getResponseCode() <= 299) {
				
				String 				resBody 		= IOUtils.toString( httpsURLConnection.getInputStream() );
				System.out.println(resBody);
			}else{
				System.out.println("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseCode()));
				System.out.println("conn.getResponseCode() ::: " + String.valueOf(httpsURLConnection.getResponseMessage()));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void TestFileSave() {
		
		String userId = "regar";
		String packageName = "com.mcnc.coway";
		StringBuffer logParams = new StringBuffer();
		logParams.append("System.out.println(\"conn.getResponseCode() ::: \" + String.valueOf(httpsURLConnection.getResponseMessage()));\n");
		
		String 				dateFormat 			= "yyyyMMdd";
		Date 				date 				= new Date();
		SimpleDateFormat 	simpleDateFormat 	= new SimpleDateFormat(dateFormat); 
		String 				SAVE_FILE_PATH 		= "D:/";
		BufferedWriter 		bw					= null;
		try {
			
			String			toDate			= simpleDateFormat.format(date);
			String			saveFilePath	= SAVE_FILE_PATH + File.separator + toDate; 
			
			//오늘날짜로된 디렉토리가 없다면 생성
			File			fileSavePath	= new File(saveFilePath);
			if( !fileSavePath.exists() ) {
				fileSavePath.mkdir();
			}
			
			String 			fileName 		= saveFilePath + File.separator + userId + "_" + packageName + ".txt";
			File			logFile			= new File(fileName);
			if(!logFile.exists()) {
				logFile.createNewFile();
			}
			
			bw = new BufferedWriter(new FileWriter(logFile, true));
			bw.write(logParams.toString());
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}
}