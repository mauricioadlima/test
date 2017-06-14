package com.mixconnector.html.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mixconnector.captcha.CaptchaService;

@Component("nacional")
public class Nacional extends AbstractHtmlParse {

	@Autowired
	public Nacional(@Qualifier("tesseract") CaptchaService captchaService) {
		super("nacional", captchaService);
	}
}
