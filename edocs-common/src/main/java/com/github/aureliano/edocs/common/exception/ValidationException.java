package com.github.aureliano.edocs.common.exception;

public class ValidationException extends EDocsException {

	private static final long serialVersionUID = 6085981301148158914L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(Throwable cause, String message) {
		super(cause, message);
	}
}