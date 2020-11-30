package connect.http.coway;

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
import org.codehaus.jackson.JsonNode;
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

public class CowayCommonHttpClient {

	
	private HttpClientTemplate httpClientTemplate;

	public HttpClientTemplate getHttpClientTemplate() {
		return httpClientTemplate;
	}

	public void setHttpClientTemplate(HttpClientTemplate httpClientTemplate) {
		this.httpClientTemplate = httpClientTemplate;
	}
	
	
	public JsonNode deleteDeviceList(String serverUrl, DeleteDeviceListRequestDO deleteDeviceListObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/deleteDeviceList.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(deleteDeviceListObj.getDeleteDeviceListNameValueLimitList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
	
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();		
	}
	
	public JsonNode deleteUserList(String serverUrl, DeleteUserListRequestDO deleteUserListObj)  throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/deleteUserList.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(deleteUserListObj.getDeleteUserListNameValueLimitList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode initDeviceAuth(String serverUrl, InitDeviceAuthRequestDO initDeviceAuthObj) throws ConnectClientException {

		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/initDeviceAuth.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(initDeviceAuthObj.getInitDeviceAuthNameValueLimitList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode insertDevice(String serverUrl, InsertDeviceRequestDO insertDeviceObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/insertDevice.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(insertDeviceObj.getInsertDeviceNameValueList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();	
	}
	
	public JsonNode insertUser(String serverUrl, InsertUserRequestDO insertUserObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/insertUser.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(insertUserObj.getInsertUserNameValueList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
	}

	public JsonNode updateUser (String serverUrl, UpdateUserRequestDO updateUserObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/smart-admin/open/updateUser.json");
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(updateUserObj.getUpdateUserNameValueList(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
	    httpPost.setEntity(entity);
	    httpPost.setHeaders( getDefaultHeaders() );
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
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
		
		HttpPost httpPost = new HttpPost(serverUrl + "/SSM/userSyncCheck.do");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(mdmUserSyncObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
//		UrlEncodedFormEntity entity = null;
//		try {
//			entity = new UrlEncodedFormEntity(mdmUserSyncObj.getMdmUserSyncNameValueList(), HTTP.UTF_8);
//		} catch (UnsupportedEncodingException e) {
//			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
//		}	
//	    httpPost.setEntity(entity);
//	    httpPost.setHeaders( getDefaultHeaders() );	
	    
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
	}

	public JsonNode mdmUserResignCheck(String serverUrl, MdmUserResignRequestDO mdmUserResignObj) throws ConnectClientException {
		
		HttpPost httpPost = new HttpPost(serverUrl + "/SSM/userResignCheck.do");
		httpPost.setHeader("Content-Type", "application/json");
		
		try {
			StringEntity entity = new StringEntity(mdmUserResignObj.toJsonNode().toString(), "UTF-8");
			httpPost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.UNSUPPORTED_ENCODING_EXCEPTION);
		}	
		
		MessageResponse messageResponse = getHttpClientTemplate().sendAndReceive(MessageResponse.class, httpPost);
		
		return messageResponse.entityToJsonNode();
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
