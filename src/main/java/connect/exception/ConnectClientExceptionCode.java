package connect.exception;

public enum ConnectClientExceptionCode {

	IO_EXCEPTION( "", "" ),
	PROTOCOL_EXCEPTION( "", "" ), 
	UNSUPPORTED_ENCODING_EXCEPTION( "", "" ), 
	CLIENT_PROTOCOL_EXCEPTION( "", "" ), 
	SOCKET_TIMEOUT_EXCEPTION( "", "" ), 
	HTTP_HOST_CONNECT_EXCEPTION( "", "" ), 
	PARSE_EXCEPTION( "", "" ), 
	INVALID_CONTENT_TYPE_EXCEPTION( "", "" ), 
	HTTP_STATE_ERROR( "", "" ), 
	INSTANTIATION_EXCEPTION( "", "" ), 
	ILLEGAL_ACCESS_EXCEPTION( "", "" ), 
	ILLEGAL_STATE_EXCEPTION( "", "" ), 
	JSON_PROCESSING_EXCEPTION( "", "" ),
	SOCKET_EXCEPTION( "", "");
	
	
	private String code;
	private String message;
	
	private ConnectClientExceptionCode( String code, String message ) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
