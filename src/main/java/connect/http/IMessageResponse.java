package connect.http;

import connect.exception.ConnectClientException;
import org.apache.http.HttpResponse;

/**
 * 
 * Message Response Inteface
 * 
 * @author kevin
 *
 */
public interface IMessageResponse {

	/**
	 * set response
	 * @param httpResponse http response object
	 * @param startTime time before execution
	 * @param endTime time after execution
	 */
	public void setResponse( HttpResponse httpResponse, long startTime, long endTime ) throws ConnectClientException;
}
