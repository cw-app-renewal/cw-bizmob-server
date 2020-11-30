package connect.http;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import connect.exception.ConnectClientException;
import connect.exception.ConnectClientExceptionCode;

public class HttpClientTemplate {

	public HttpClientTemplate() {
	}

	private DefaultHttpClient createDefaultHttpClient() {
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setTimeout( params, 20 );
		return new DefaultHttpClient( params );
	}
	
	private < T extends IMessageResponse > T fireAndReceive( Class< T > t, HttpUriRequest httpUriRequest ) throws ConnectClientException {
		try {
			long	t1	= System.currentTimeMillis();
			DefaultHttpClient		httpClient		= createDefaultHttpClient();
			HttpResponse	httpResponse	= httpClient.execute( httpUriRequest );
			long	t2	= System.currentTimeMillis();
			T	result	= t.newInstance();
			result.setResponse( httpResponse, t1, t2 );
			return result;
		} catch ( ClientProtocolException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.CLIENT_PROTOCOL_EXCEPTION );
		} catch ( SocketTimeoutException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.SOCKET_TIMEOUT_EXCEPTION );
		} catch ( HttpHostConnectException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.HTTP_HOST_CONNECT_EXCEPTION );
		} catch ( IOException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.IO_EXCEPTION );
		} catch (InstantiationException e) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.INSTANTIATION_EXCEPTION );
		} catch (IllegalAccessException e) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.ILLEGAL_ACCESS_EXCEPTION );
		}
	}

	public < T extends IMessageResponse > T sendAndReceive( Class< T > t, HttpUriRequest httpUriRequest, HttpContext localContext ) throws ConnectClientException {
		try {
			long	t1	= System.currentTimeMillis();
			HttpClient		httpClient		= createDefaultHttpClient();
			HttpResponse	httpResponse	= httpClient.execute( httpUriRequest, localContext );
			long	t2	= System.currentTimeMillis();
			T	result	= t.newInstance();
			result.setResponse( httpResponse, t1, t2 );
			return result;
		} catch ( ClientProtocolException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.CLIENT_PROTOCOL_EXCEPTION );
		} catch ( SocketTimeoutException e ) {
			throw new ConnectClientException( e,	ConnectClientExceptionCode.SOCKET_TIMEOUT_EXCEPTION );
		} catch ( HttpHostConnectException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.HTTP_HOST_CONNECT_EXCEPTION );
		} catch ( IOException e ) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.IO_EXCEPTION );
		} catch (InstantiationException e) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.INSTANTIATION_EXCEPTION );
		} catch (IllegalAccessException e) {
			throw new ConnectClientException( e, ConnectClientExceptionCode.ILLEGAL_ACCESS_EXCEPTION );
		}
	}
	
	public < T extends IMessageResponse > T sendAndReceive( Class< T > t, HttpUriRequest httpUriRequest ) throws ConnectClientException {
		return fireAndReceive( t, httpUriRequest );
	}
}
