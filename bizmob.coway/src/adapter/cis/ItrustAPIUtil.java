package adapter.cis;

import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import common.exception.ItrustAPIException;

public class ItrustAPIUtil {

	private static final Logger logger = LoggerFactory.getLogger(ItrustAPIUtil.class);
	
	/**
	 * GET Send OpenAPI
	 * @param url API URL 정보
	 * @return ResponseEntity<ObjectNode>
	 */
	public static ResponseEntity<ObjectNode> getSendMessage(String url, HttpHeaders headers) {
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
			
//			logger.debug("### URL : " + url);
			
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ObjectNode.class);
		} catch(RestClientException e){
			throw new ItrustAPIException(e);
		} catch (Exception e) {
			throw new ItrustAPIException(e);
		}
	}
	
	/**
	 * POST Send OpenAPI
	 * @param url API URL 정보
	 * @param reqData Request정보
	 * @return ResponseEntity<ObjectNode>
	 */
	public static ResponseEntity<ObjectNode> postSendMessage(String url, HttpHeaders headers, ObjectNode reqData) {
		
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ObjectNode> requestEntity = new HttpEntity<ObjectNode>(reqData, headers);
			
			logger.debug("### requestEntity : " + requestEntity);
			
			return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ObjectNode.class);
		} catch(RestClientException e){
			throw new ItrustAPIException(e);
		} catch (Exception e) {
			throw new ItrustAPIException(e);
		}
	}
}
