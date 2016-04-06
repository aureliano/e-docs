package com.github.aureliano.edocs.common.exception;

public class FileRepositoryException extends EDocsException {

	private static final long serialVersionUID = 1822901921671075041L;

	public FileRepositoryException() {
		super();
	}

	public FileRepositoryException(String message) {
		super(message);
	}

	public FileRepositoryException(Throwable cause) {
		super(cause);
	}

	public FileRepositoryException(Throwable cause, String message) {
		super(cause, message);
	}
}