package connect.http.coway;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import connect.http.HttpClientTemplate;
import connect.http.MessageResponse;
import connect.http.coway.data.DeleteDeviceListRequestDO;
import connect.http.coway.data.DeleteUserListRequestDO;
import connect.http.coway.data.InitDeviceAuthRequestDO;
import connect.http.coway.data.InsertDeviceRequestDO;
import connect.http.coway.data.InsertUserRequestDO;
import connect.http.coway.data.MdmUserResignRequestDO;
import connect.http.coway.data.MdmUserSyncRequestDO;
import connect.http.coway.data.UpdateUserRequestDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class CowayCommonHttpClient {

	private static final Logger logger = LoggerFactory.getLogger(CowayCommonHttpClient.class);
	
	private HttpClientTemplate httpClientTemplate;

	public HttpClientTemplate getHttpClientTemplate() {
		return httpClientTemplate;
	}

	public void setHttpClientTemplate(HttpClientTemplate httpClientTemplate) {
		this.httpClientTemplate = httpClientTemplate;
	}
	
	
	public JsonNode deleteDeviceList(String serverUrl, DeleteDeviceListRequestDO deleteDeviceListObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : deleteDeviceListObj.getDeleteDeviceListNameValueLimitList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/deleteDeviceList.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	public JsonNode deleteUserList(String serverUrl, DeleteUserListRequestDO deleteUserListObj)  throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : deleteUserListObj.getDeleteUserListNameValueLimitList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/deleteUserList.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	public JsonNode initDeviceAuth(String serverUrl, InitDeviceAuthRequestDO initDeviceAuthObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : initDeviceAuthObj.getInitDeviceAuthNameValueLimitList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/initDeviceAuth.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	public JsonNode insertDevice(String serverUrl, InsertDeviceRequestDO insertDeviceObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : insertDeviceObj.getInsertDeviceNameValueList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/insertDevice.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	public JsonNode insertUser(String serverUrl, InsertUserRequestDO insertUserObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : insertUserObj.getInsertUserNameValueList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/insertUser.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;
	}

	public JsonNode updateUser (String serverUrl, UpdateUserRequestDO updateUserObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		for (NameValuePair pair : updateUserObj.getUpdateUserNameValueList()) {
			map.add(pair.getName(), pair.getValue());
		}

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(map, getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/open/updateUser.json", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	public JsonNode postSmartAdmin (String serverUrl, String contextUrl, ArrayList<NameValuePair> qparams) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/" + contextUrl);
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(qparams, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
	}
	
	/**
	 * 
	 */
	private Header[] getDefaultHeaders() {
		Header[]	headers	= {
				new BasicHeader( "Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*" ),
				new BasicHeader( "Accept-Language", "ko-KR" ),
				new BasicHeader( "User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; IPMS/631810AC-14F04070DCD-000000030295; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0)" ),
				new BasicHeader( "Accept-Encoding", "gzip, deflate" ),
				new BasicHeader( "Connection", "Keep-Alive" ),
		};
		return headers;
	}
	
	/**
	 * MDM 서버 인터페이스 
	 */
	public JsonNode mdmUserSyncCheck(String serverUrl, MdmUserSyncRequestDO mdmUserSyncObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<String> requestParam = new HttpEntity<>(mdmUserSyncObj.toJsonNode().toString(), getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/SSM/userSyncCheck.do", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}

	public JsonNode mdmUserResignCheck(String serverUrl, MdmUserResignRequestDO mdmUserResignObj) throws ConnectClientException {

		RestTemplate getTokenTemplate = new RestTemplate();
		ResponseErrorHandler getTokenErrorHandler = new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.warn("response status: '{}', headers: '{}', body: '{}'",
						response.getStatusCode(), response.getHeaders(), response.getBody());
			}
		};

		getTokenTemplate.setErrorHandler(getTokenErrorHandler);
		// Add the Jackson message converter
		getTokenTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders getTokenHeaders = new HttpHeaders();
		getTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		//HttpEntity<ArrayList<NameValuePair>> requestParam = new HttpEntity<>(insertUserObj.getInsertUserNameValueList(), getTokenHeaders);
		HttpEntity<String> requestParam = new HttpEntity<>(mdmUserResignObj.toJsonNode().toString(), getTokenHeaders);

		ResponseEntity<String> getTokenResponse = getTokenTemplate.postForEntity(serverUrl + "/SSM/userResignCheck.do", requestParam , String.class);
		logger.error(getTokenResponse.toString());

		JsonNode actualObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory f = new JsonFactory();
			JsonParser p = f.createJsonParser(getTokenResponse.getBody());
			actualObj = mapper.readTree(p);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return actualObj;

	}
	
	/**
	 * Push 서버 인터페이스
	 */
	public void pushNotification( String url, ObjectNode message ) throws ConnectClientException {
		try {
			HttpPost httpPost = new HttpPost( url );
			
			httpPost.setEntity( new StringEntity( message.toString(), "utf-8" ) );
			
			getHttpClientTemplate().sendAndReceive( MessageResponse.class, httpPost );
			
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}
	}
	
	/**
	 * 퇴사자 배치 RFC call 
	 */
	public JsonNode userCloseCheck(String serverUrl, String jsonString) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/bizmob/COMMON.json");
		StringEntity entity = null;
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		try {
			params.add(new BasicNameValuePair("message", jsonString));
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}

}
