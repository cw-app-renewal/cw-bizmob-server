package common.util;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.config.SmartConfig;

import connect.ftp.FtpClientService;

public class FileAttachmentService {

	private static final Logger logger = LoggerFactory.getLogger(FileAttachmentService.class);
	
	@Autowired FtpClientService ftpClientService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean upload(String filePath, String fileName, byte[] fileData, boolean isBinary) throws AdapterException {
		boolean 		result 				= false;
		String			defaultBaseUrl		= "https://storage-proxy.coway.dev";
		String 			defaultUploadPath 	= "/v1/binary-file";
		
		try {

			if (filePath != null && filePath.length() > 1 &&filePath.endsWith("/")) {
				filePath = filePath.substring(0, filePath.length() - 1);
			}
			
			String 			baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
			String 			uploadPath 		= ""; 
			
			RestTemplate 	restTemplate 	= new RestTemplate();
			HttpHeaders 	headers 		= new HttpHeaders();
			
			if(isBinary) {
				
				uploadPath = SmartConfig.getString("coway.attach.upload.binary.path", defaultUploadPath);
				
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			    headers.setAcceptCharset(Arrays.asList(Charset.defaultCharset()));
			    
				/*Map<String, String> 	params 			= new HashMap<>();
				params.put("contentType", 	"image/jpeg");
				params.put("destFileName", 	fileName);
				params.put("destFilePath", 	filePath);
				params.put("privacy", "N");*/

			    HttpEntity<byte[]> 		requestEntity 	= new HttpEntity(fileData, headers);
			    uploadPath = uploadPath +
						"?destFilePath=" + filePath +
						"&destFileName=" + fileName +
						//"&contentType={contentType}" +
						"&privacy=N";
			    
			    FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
			    restTemplate.getMessageConverters().add(formHttpMessageConverter);
			    restTemplate.getMessageConverters().remove(6); //json컨버터 제거
			    
			    logger.info("File Upload Url = " + baseUrl + uploadPath + " :: ");
			    
			    long start = System.currentTimeMillis();
			    restTemplate.exchange(baseUrl + uploadPath, HttpMethod.PUT, requestEntity, String.class);
			    long end = System.currentTimeMillis();
			    logger.info("{} File Upload Time ::  [" +  (end - start) + " ms]", fileName);
			    
			} else {
				headers.setContentType(MediaType.APPLICATION_JSON);
				uploadPath = SmartConfig.getString("coway.attach.upload.base64.path", defaultUploadPath);
			    
			    String			base64EncData	= Base64.encodeBase64String(fileData);
			    
			    ObjectNode reqObjNode = JsonUtil.objectNode();
			    //reqObjNode.put("contentType",	"image/jpeg");
			    reqObjNode.put("destFileName", 	fileName);
			    reqObjNode.put("destFilePath", 	filePath);
			    reqObjNode.put("base64EncData", base64EncData);
				
				HttpEntity<ObjectNode> requestEntity = new HttpEntity(reqObjNode, headers);
				
				long start = System.currentTimeMillis();
				restTemplate.exchange(baseUrl + uploadPath, HttpMethod.PUT, requestEntity, Void.class);
				long end = System.currentTimeMillis();
			    logger.info("{} File Upload Time ::  [" +  (end - start) + " ms]", fileName);
			}
			
			result = true;
		} catch (Exception e) {
			throw new AdapterException("UPLOADE", "파일 업로드에 실패하였습니다.", e);
		}
		
		return result;
	}
	
	public byte[] download(String filePath, String fileName, boolean isBinary) throws Exception {
		
		String			defaultBaseUrl	= "https://storage-proxy.coway.dev";
		String 			defaultDownPath = "/v1/binary-file";
		
		String 			baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
		String 			downPath 		= "";
		
		/*Map<String, String> params 			= new HashMap<>();
		params.put("destFileName", fileName);
		params.put("destFilePath", filePath);
		params.put("privacy", "N");*/
		
		RestTemplate	 		restTemplate 	= new RestTemplate();
		HttpEntity<?> 			requestEntity 	= null;
		byte[] 					byteData		= new byte[] {};

		if (filePath != null && filePath.length() > 1 &&filePath.endsWith("/")) {
			filePath = filePath.substring(0, filePath.length() - 1);
		}

		if(isBinary) {

			downPath = SmartConfig.getString("coway.attach.download.binary.path", defaultDownPath);
			downPath = downPath +
					"?destFilePath=" + filePath +
					"&destFileName=" + fileName +
					"&privacy=N";

			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		
		    requestEntity = new HttpEntity<Object>(headers);
		    
		    long start = System.currentTimeMillis();
		    ResponseEntity<byte[]> 	resEntity 		=  restTemplate.exchange(baseUrl + downPath, HttpMethod.GET, requestEntity, byte[].class);
		    long end = System.currentTimeMillis();
		    logger.info("{} File Download Time ::  [" +  (end - start) + " ms]", fileName);
		    
		    byteData = resEntity.getBody();
		    
		} else {
			downPath = SmartConfig.getString("coway.attach.download.base64.path", defaultDownPath);
			downPath = downPath +
					"?destFilePath=" + filePath +
					"&destFileName=" + fileName +
					"&privacy=N";

			ResponseEntity<String> 	resEntity 		=  restTemplate.exchange(baseUrl + downPath, HttpMethod.GET, requestEntity, String.class);
			
			String 					resBody 		= resEntity.getBody();
			JsonNode 				node 			= JsonUtil.toObject(resBody, JsonNode.class);
			String 					base64EncData 	= (String) JsonUtil.getValue(node, "base64EncodedImage");
			
			byteData = Base64.decodeBase64(base64EncData);
		}
		
		if(byteData.length == 0) {
			String _DEV_ROOT_FOLDER = Boolean.parseBoolean(SmartConfig.getString("media.ftp.mode.dev", "false"))? "/dev":"";
			byteData = ftpClientService.downloadFile(_DEV_ROOT_FOLDER + filePath, fileName);
		}
		
		return byteData;
	}
	
	public boolean delete(String filePath, String fileName) {
		
		boolean 		result 				= false;
		String			defaultBaseUrl		= "https://storage-proxy.coway.dev";
		String 			defaultDeletePath 	= "/v1/file";
		
		try {

			if (filePath != null && filePath.length() > 1 &&filePath.endsWith("/")) {
				filePath = filePath.substring(0, filePath.length() - 1);
			}

			String 				baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
			String 				uploadPath 		= SmartConfig.getString("coway.attach.delete.path", defaultDeletePath);
			
			RestTemplate 		restTemplate 	= new RestTemplate();
			/*Map<String, String> params 			= new HashMap<>();
			params.put("destFileName", fileName);
			params.put("destFilePath", filePath);
			params.put("permanently", "Y");*/

			uploadPath 	= uploadPath +
					"?destFilePath=" + filePath +
					"&destFileName=" + fileName;

			long start = System.currentTimeMillis();
			restTemplate.delete(baseUrl + uploadPath);
			long end = System.currentTimeMillis();
		    logger.info("{} File Deleted Time ::  [" +  (end - start) + " ms]", fileName);
			
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
		}
		
		return result;
		
	}

	public String getDownloadUrl(String filePath, String fileName) {
		String			defaultBaseUrl	= "https://storage-proxy.coway.dev";
		String 			defaultDownPath = "/v1/binary-file";

		String 			baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
		String 			downPath 		= "";

		if (filePath != null && filePath.length() > 1 &&filePath.endsWith("/")) {
			filePath = filePath.substring(0, filePath.length() - 1);
		}

		downPath = SmartConfig.getString("coway.attach.download.binary.path", defaultDownPath);
		downPath = baseUrl + downPath +
				"?destFilePath=" + filePath +
				"&destFileName=" + fileName +
				"&privacy=N";

		return downPath;
	}
}
