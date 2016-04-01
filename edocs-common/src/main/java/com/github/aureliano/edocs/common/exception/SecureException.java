package com.github.aureliano.edocs.common.exception;

public class SecureException extends EDocsException {

	private static final long serialVersionUID = -3347580659765622457L;

	public SecureException() {
		super();
	}

	public SecureException(String message) {
		super(message);
	}

	public SecureException(Throwable cause) {
		super(cause);
	}

	public SecureException(Throwable cause, String message) {
		super(cause, message);
	}
}