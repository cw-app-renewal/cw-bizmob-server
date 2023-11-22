package connect.http.coway;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import connect.http.HttpClientTemplate;
import connect.http.MessageResponse;
import connect.http.coway.data.*;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.JsonNode;

import java.io.UnsupportedEncodingException;

@Deprecated
public class CowayCommonHttpClient_Json {
	
	private HttpClientTemplate httpClientTemplate;

	public HttpClientTemplate getHttpClientTemplate() {
		return httpClientTemplate;
	}

	public void setHttpClientTemplate(HttpClientTemplate httpClientTemplate) {
		this.httpClientTemplate = httpClientTemplate;
	}
	
	
	public JsonNode deleteDeviceList(String serverUrl, DeleteDeviceListRequestDO deleteDeviceListObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/open/deleteDeviceList.json");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(deleteDeviceListObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
		
	}
	
	public JsonNode deleteUserList(String serverUrl, DeleteUserListRequestDO deleteUserListObj)  throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/open/deleteUserList.json");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(deleteUserListObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode initDeviceAuth(String serverUrl, InitDeviceAuthRequestDO initDeviceAuthObj) throws ConnectClientException {

		HttpPost httpPost = new HttpPost(serverUrl + "/open/initDeviceAuth.json");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(initDeviceAuthObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode insertDevice(String serverUrl, InsertDeviceRequestDO insertDeviceObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/open/insertDevice.json");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(insertDeviceObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode insertUser(String serverUrl, InsertUserRequestDO insertUserObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/open/insertUser.json");
		//httpPost.setHeaders( getDefaultHeaders() );
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		
		try {
			String jsonString = insertUserObj.toJsonNode().toString();
			
			StringEntity entity = new StringEntity(jsonString, "application/json", "UTF-8");		
			//StringEntity entity = new StringEntity(jsonString);
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
	}

	public JsonNode updateUser (String serverUrl, UpdateUserRequestDO updateUserObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/open/updateUser.json");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(updateUserObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
	}
	
	public JsonNode postSmartAdmin (String serverUrl, String contextUrl, JsonNode jsonObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/" + contextUrl);
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(jsonObj.toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
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
	
}
