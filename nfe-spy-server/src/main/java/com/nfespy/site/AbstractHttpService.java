package com.nfespy.site;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.GlobalProperties;
import com.nfespy.config.StateConfigPropertires;
import com.nfespy.service.CaptchaService;

abstract class AbstractHttpService implements HttpService {

	private static final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";

	private static final String WEB_SECURITY_NO = "--web-security=no";

	private static final String IGNORE_SSL_ERRORS_YES = "--ignore-ssl-errors=yes";

	private static final String CHROME_BINARY_PATH = "webdriver.chrome.driver";

	@Autowired
	StateConfigPropertires stateConfigPropertires;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	private CaptchaService captchaService;

	@PostConstruct
	private void postConstruct() {
		System.setProperty(PHANTOMJS_BINARY_PATH, globalProperties.getPhantomjsBinary());
		System.setProperty(CHROME_BINARY_PATH, globalProperties.getChromeBinary());
	}

	WebDriver getDriver() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { WEB_SECURITY_NO, IGNORE_SSL_ERRORS_YES });
		//TODO WebDriver driver = new ChromeDriver();
		WebDriver driver = new PhantomJSDriver(caps);
		driver.manage()
			  .timeouts()
			  .implicitlyWait(1, TimeUnit.SECONDS);
		return driver;
	}

	String brokenCaptcha(String urlOrImage) {
		return captchaService.solve(urlOrImage);
	}
}
