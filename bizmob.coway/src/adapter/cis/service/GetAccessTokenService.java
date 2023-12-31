package adapter.cis.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mcnc.smart.common.config.SmartConfig;

import adapter.cis.vo.AccessTokenVO;

@Service
public class GetAccessTokenService {
	
	private static final Logger logger = LoggerFactory.getLogger(GetAuthTokenService.class);
	
	private RestTemplate getAccessTokenRestTemplate;
	
	private String accessToken;
	
	@PostConstruct
	public void makeRestTemplate() {
	    getAccessTokenRestTemplate = new RestTemplate();
	    
		try {
			refreshAccessToken();
		} catch(Exception e) {
			logger.error("GetAccessTokenService makeRestTemplate and refreshAccessToken error {}", e.getMessage());
		}
	}
	
	
	
	
	public void refreshAccessToken(){
		
		String authKey = SmartConfig.getString("coway.oauth.basic");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization","Basic " + authKey);
		
		MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<String, String>();
		
		parameterMap.add("grant_type", "client_credentials");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameterMap, headers);
		
		String url = SmartConfig.getString("coway.oauth.main") + SmartConfig.getString("coway.oauth.token");
		AccessTokenVO accessTokenvo = getAccessTokenRestTemplate.postForObject(url, entity, AccessTokenVO.class);
		
		logger.debug("getAccessToken - {}", accessTokenvo.getAccess_token());
	
		this.accessToken = accessTokenvo.getAccess_token();
	}
	
	public String getAccessToken(){
		return this.accessToken;
	}
}

