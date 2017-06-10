package com.nfespy.domain;

public class NfeException extends RuntimeException {

	public NfeException(final String message) {
		super(message);
	}

	public NfeException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
