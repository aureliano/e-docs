package com.github.aureliano.edocs.common.exception;

public class EDocsException extends RuntimeException {

	private static final long serialVersionUID = 2045431366748119694L;

	public EDocsException() {
		super();
	}

	public EDocsException(String message) {
		super(message);
	}

	public EDocsException(Throwable cause) {
		super(cause);
	}

	public EDocsException(Throwable cause, String message) {
		super(message, cause);
	}
}