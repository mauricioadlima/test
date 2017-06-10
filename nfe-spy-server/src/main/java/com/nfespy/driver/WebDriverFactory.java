package com.nfespy.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nfespy.config.GlobalProperties;

@Configuration
public class WebDriverFactory {

	private static final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";

	private static final String WEB_SECURITY_NO = "--web-security=no";

	private static final String IGNORE_SSL_ERRORS_YES = "--ignore-ssl-errors=yes";

	private static final String CHROME_BINARY_PATH = "webdriver.chrome.driver";

	@Bean
	@Profile("production")
	public WebDriver productionDriver(GlobalProperties globalProperties) {
		System.setProperty(PHANTOMJS_BINARY_PATH, globalProperties.getPhantomjsBinary());
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { WEB_SECURITY_NO, IGNORE_SSL_ERRORS_YES });
		return () -> new PhantomJSDriver(caps);
	}

	@Bean
	@Profile("test")
	public WebDriver testDriver(GlobalProperties globalProperties) {
		System.setProperty(CHROME_BINARY_PATH, globalProperties.getChromeBinary());
		return ChromeDriver::new;
	}

}
