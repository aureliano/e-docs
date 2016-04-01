package com.github.aureliano.edocs.common.exception;

public class ServiceException extends EDocsException {

	private static final long serialVersionUID = 7670452342718330256L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(Throwable cause, String message) {
		super(cause, message);
	}
}