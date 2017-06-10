package com.nfespy.captcha.solutions;

public class CaptchaSolutionsException extends RuntimeException {

	private static final String DEFAULT_MSG = "Captcha Solutions cannot be solve an image";

	public CaptchaSolutionsException(final Throwable cause) {
		super(DEFAULT_MSG, cause);
	}

}
