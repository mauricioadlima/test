package com.nfespy.html.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nfespy.captcha.CaptchaService;

@Component("sp")
public class SaoPaulo extends AbstractHtmlParse {

	@Autowired
	public SaoPaulo(@Qualifier("tesseract") CaptchaService captchaService) {
		super("sp", captchaService);
	}
}