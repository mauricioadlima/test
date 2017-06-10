package com.nfespy.captcha;

import java.io.File;

public interface CaptchaService {

	String solve(final String imageBase64);

	String solve(final File image);
}
