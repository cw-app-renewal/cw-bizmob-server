package connect.exception;

public class ConnectClientException extends Exception {

	private ConnectClientExceptionCode	exceptionCode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1526952313493943286L;

	public ConnectClientException( String message, ConnectClientExceptionCode exceptionCode ) {
		super( message );
		this.exceptionCode	= exceptionCode;
	}

	public ConnectClientException( String message, Exception e, ConnectClientExceptionCode exceptionCode ) {
		super( message, e );
		this.exceptionCode	= exceptionCode;
	}
	
	public ConnectClientException( Exception e, ConnectClientExceptionCode exceptionCode ) {
		super( e );
		this.exceptionCode	= exceptionCode;
	}
	
	public ConnectClientExceptionCode getExceptionCode() {
		return exceptionCode;
	}

}
