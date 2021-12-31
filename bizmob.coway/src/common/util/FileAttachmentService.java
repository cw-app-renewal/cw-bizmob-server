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
		String			defaultBaseUrl		= "https://api-gw.coway.dev";
		String 			defaultUploadPath 	= "/attachments/v1";
		
		try {
			
			String 			baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
			String 			uploadPath 		= SmartConfig.getString("coway.attach.upload.path", defaultUploadPath);
			
			//테스트용
//			String baseUrl 		= defaultBaseUrl;
//			String uploadPath 		= defaultUploadPath;
			
			
			RestTemplate 	restTemplate 	= new RestTemplate();
			HttpHeaders 	headers 		= new HttpHeaders();
			
			if(isBinary) {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			    headers.setAcceptCharset(Arrays.asList(Charset.defaultCharset()));
			    
				Map<String, String> 	params 		= new HashMap<>();
				params.put("contentType", 	"image/jpeg");
				params.put("destFileName", 	fileName);
				params.put("destFilePath", 	filePath);
			    
			    HttpEntity<byte[]> 		requestEntity = new HttpEntity(fileData, headers);
			    uploadPath = uploadPath + "?destFilePath={destFilePath}&destFileName={destFileName}&contentType={contentType}";
			    
			    FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
			    restTemplate.getMessageConverters().add(formHttpMessageConverter);
			    restTemplate.getMessageConverters().remove(6); //json컨버터 제거
			    
			    logger.info("File Upload Url = " + baseUrl + uploadPath + " :: " +  params.toString());
			    
			    long start = System.currentTimeMillis();
			    restTemplate.exchange(baseUrl + uploadPath, HttpMethod.PUT, requestEntity, String.class, params);
			    long end = System.currentTimeMillis();
			    logger.info("{} File Upload Time ::  [" +  (end - start) + " ms]", fileName);
			    
			} else {
				headers.setContentType(MediaType.APPLICATION_JSON);
			    
			    String			base64EncData	= Base64.encodeBase64String(fileData);
			    
			    ObjectNode reqObjNode = JsonUtil.objectNode();
			    reqObjNode.put("contentType",	"image/jpeg");
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
		
		String			defaultBaseUrl	= "https://api-gw.coway.dev";
		String 			defaultDownPath = "/attachments/v1?destFilePath={destFilePath}&destFileName={destFileName}&isBinary={isBinary}";
		
		String 			baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
		String 			downPath 		= SmartConfig.getString("coway.attach.down.path", defaultDownPath);
		
		Map<String, String> params 			= new HashMap<>();
		params.put("destFileName", fileName);
		params.put("destFilePath", filePath);
		params.put("isBinary", "N");
		
		RestTemplate	 		restTemplate 	= new RestTemplate();
		HttpEntity<?> 			requestEntity 	= null;
		byte[] 					byteData		= new byte[] {};
		
		if(isBinary) {
			params.put("isBinary", "Y");	
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		
		    requestEntity = new HttpEntity<Object>(headers);
		    
		    long start = System.currentTimeMillis();
		    ResponseEntity<byte[]> 	resEntity 		=  restTemplate.exchange(baseUrl + downPath, HttpMethod.GET, requestEntity, byte[].class, params);
		    long end = System.currentTimeMillis();
		    logger.info("{} File Download Time ::  [" +  (end - start) + " ms]", fileName);
		    
		    byteData = resEntity.getBody();
		    
		} else {
			ResponseEntity<String> 	resEntity 		=  restTemplate.exchange(baseUrl + downPath, HttpMethod.GET, requestEntity, String.class, params);
			
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
		String			defaultBaseUrl		= "https://api-gw.coway.dev";
		String 			defaultDeletePath 	= "/attachments/v1?destFilePath={destFilePath}&destFileName={destFileName}";
		
		try {
			
			String 				baseUrl 		= SmartConfig.getString("coway.attach.base.url", defaultBaseUrl);
			String 				uploadPath 		= SmartConfig.getString("coway.attach.upload.path", defaultDeletePath);
			
			RestTemplate 		restTemplate 	= new RestTemplate();
			Map<String, String> params 			= new HashMap<>();
			params.put("destFileName", fileName);
			params.put("destFilePath", filePath);
			
			long start = System.currentTimeMillis();
			restTemplate.delete(baseUrl + uploadPath, params);
			long end = System.currentTimeMillis();
		    logger.info("{} File Deleted Time ::  [" +  (end - start) + " ms]", fileName);
			
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
		}
		
		return result;
		
	}
}
