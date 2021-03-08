package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

import common.exception.ItrustAPIException;

public class ItrustAPIUtil {

	private static final Logger logger = LoggerFactory.getLogger(ItrustAPIUtil.class);
	
	private static RestTemplate	restTemplate;
	
	/**
	 * GET Send OpenAPI
	 * @param url API URL 정보
	 * @return ResponseEntity<ObjectNode>
	 */
	public static ResponseEntity<ObjectNode> getSendMessage(String url, HttpHeaders headers) {
		try{
			
			HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
			
			logger.debug("### URL : " + url);
			
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ObjectNode.class);
		} catch(RestClientException e){
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
			
			HttpEntity<ObjectNode> requestEntity = new HttpEntity<ObjectNode>(reqData, headers);
			
			logger.debug("### requestEntity : " + requestEntity);
			
			return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ObjectNode.class);
		} catch(RestClientException e){
			throw new ItrustAPIException(e);
		}
	}
}
