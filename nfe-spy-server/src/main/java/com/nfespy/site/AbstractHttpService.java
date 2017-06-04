package com.nfespy.site;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.GlobalConfig;
import com.nfespy.config.StateConfigPropertires;

abstract class AbstractHttpService implements HttpService {

	private static final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";

	private static final String WEB_SECURITY_NO = "--web-security=no";

	private static final String IGNORE_SSL_ERRORS_YES = "--ignore-ssl-errors=yes";

	@Autowired
	StateConfigPropertires stateConfigPropertires;

	WebDriver driver;

	@Autowired
	private GlobalConfig globalConfig;

	@PostConstruct
	private void postConstruct() {
		System.setProperty(PHANTOMJS_BINARY_PATH, globalConfig.getPhantomjsBinary());
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { WEB_SECURITY_NO, IGNORE_SSL_ERRORS_YES });
		driver = new PhantomJSDriver(caps);
		driver.manage()
			  .timeouts()
			  .implicitlyWait(1, TimeUnit.SECONDS);
	}

	String brokenCaptcha(String byteImage) {
		// TODO invoke captcha API
		String captcha = "";

		final byte[] decode = Base64.getDecoder()
									.decode(byteImage);

		try (FileOutputStream ios = new FileOutputStream(new File("/home/CIT/mauriciol/Desktop/image.png"))) {
			ios.write(decode);
			ios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return captcha;
	}
}
