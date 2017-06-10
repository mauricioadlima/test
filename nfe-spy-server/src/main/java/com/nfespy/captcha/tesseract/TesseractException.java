package com.nfespy.captcha.tesseract;

public class TesseractException extends RuntimeException {

	private static final String DEFAULT_MSG = "Tesseract cannot be read image";

	public TesseractException() {
		super(DEFAULT_MSG);
	}

	public TesseractException(final Throwable cause) {
		super(DEFAULT_MSG, cause);
	}
}
