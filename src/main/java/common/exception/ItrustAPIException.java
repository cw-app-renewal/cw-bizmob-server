package common.exception;

import org.springframework.web.client.RestClientException;

public class ItrustAPIException extends RestClientException{

	private static final long serialVersionUID = 1L;

	public ItrustAPIException(String msg, Throwable ex) {
		super(msg, ex);	
	}

	public ItrustAPIException(String msg) {
		super(msg);
	}
	
	public ItrustAPIException(Throwable ex) {
		super("ITRUST API ERROR", ex);	
	}
}
