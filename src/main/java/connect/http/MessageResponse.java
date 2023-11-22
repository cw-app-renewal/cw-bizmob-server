package connect.http;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageResponse implements IMessageResponse {

	private long	startTime;
	private long	endTime;
	
	private HttpResponse	httpResponse;

	private static final Logger logger = LoggerFactory.getLogger(MessageResponse.class);
	
	public void setResponse(HttpResponse httpResponse, long startTime, long endTime) throws ConnectClientException {
		setHttpResponse( httpResponse );
		setStartTime( startTime );
		setEndTime( endTime );
	}
	
	public List< String > entityToStringLinesOfEuckrType() throws ConnectClientException {
		HttpEntity		he		= getHttpResponse().getEntity();
		try {
			return IOUtils.readLines( he.getContent(), "euc-kr" );
		} catch (IllegalStateException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
	}

	public String entityToStringUtf8Type() throws ConnectClientException {
		HttpEntity		he		= getHttpResponse().getEntity();
		try {
			return EntityUtils.toString(he, HTTP.UTF_8);
		} catch (IllegalStateException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
	}
	
	public JsonNode entityToJsonNode() throws ConnectClientException {
		HttpEntity he = getHttpResponse().getEntity();
		
		try {
			String jsonString = EntityUtils.toString(he, HTTP.UTF_8);
			return jsonParserToString(jsonString);
		} catch (IllegalStateException e){
			throw new ConnectClientException(e, ConnectClientExceptionCode.ILLEGAL_STATE_EXCEPTION);			
		} catch (IOException e) {
			throw new ConnectClientException(e, ConnectClientExceptionCode.IO_EXCEPTION);
		}
	}
	
	
	public byte[] entityToByteArray() throws ConnectClientException{
		HttpEntity		he	= getHttpResponse().getEntity();
		try {
			return EntityUtils.toByteArray(he);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public int getResultCode() {
		return getHttpResponse().getStatusLine().getStatusCode();
	}
	
	public String getContentType() {
		return getHttpResponse().getEntity().getContentType().getValue();
	}
	
	private JsonNode jsonParserToString(String msg) throws JsonParseException, IOException {
		logger.error("===========================");
		logger.error(msg);
    	ObjectMapper mapper = new ObjectMapper(); 
		JsonFactory f = new JsonFactory();
		JsonParser p = f.createJsonParser(msg); 
		JsonNode actualObj = mapper.readTree(p);
		
		return actualObj;
    }

	public String getCookie() {
		Header[]	headers	= getHttpResponse().getHeaders( "Set-Cookie" );
		if ( headers.length > 0 ) {
			return headers[ 0 ].getValue();
		}
		return null;
	}
	
	public List<String> getCookies() {
		Header[]	headers	= getHttpResponse().getHeaders( "Set-Cookie" );
		
		List<String> 	setCookies = new ArrayList<String>();
		
		if ( headers.length > 0 ) {
			for(int i=0; i<headers.length; i++ ){
				setCookies.add(headers[i].getValue());
			}
			return setCookies;
		}
		return null;
	}
	
	public String getCookie( String key ) {
		Header[]	headers	= getHttpResponse().getHeaders( "Set-Cookie" );
		if ( headers.length > 0 ) {
			return headers[ 0 ].getValue();
		}
		return null;
	}
	
	public Header[] getAllHeaders(){
		return getHttpResponse().getAllHeaders();
	}
	
	public Header[] getHeaders(String name){
		return getHttpResponse().getHeaders(name);
	}
	
//	public Header[] getDefaultHeaders() {
//		Header[]	headers	= {
//				new BasicHeader( "Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*" ),
//				new BasicHeader( "Accept-Language", "ko-KR" ),
//				new BasicHeader( "User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; IPMS/631810AC-14F04070DCD-000000030295; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Tablet PC 2.0)" ),
//				new BasicHeader( "Accept-Encoding", "gzip, deflate" ),
//				new BasicHeader( "Connection", "Keep-Alive" ),
//		};
//		return headers;
//	}
	
}