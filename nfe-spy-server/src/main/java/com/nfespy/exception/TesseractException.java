package com.nfespy.exception;

public class TesseractException extends RuntimeException {

	public TesseractException(final String message) {
		super(message);
	}

	public TesseractException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
