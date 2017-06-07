package com.nfespy.site;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.GlobalProperties;
import com.nfespy.config.StateConfigProperties;
import com.nfespy.config.StateConfigProperties.State;
import com.nfespy.config.StateConfigProperties.State.StateProperty;
import com.nfespy.service.CaptchaService;

abstract class AbstractHttpService implements HttpService {

	private static final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";

	private static final String WEB_SECURITY_NO = "--web-security=no";

	private static final String IGNORE_SSL_ERRORS_YES = "--ignore-ssl-errors=yes";

	private static final String CHROME_BINARY_PATH = "webdriver.chrome.driver";

	@Autowired
	StateConfigProperties stateConfigPropertires;

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
		WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
		//WebDriver driver = new PhantomJSDriver(caps);
		driver.manage()
			  .timeouts()
			  .implicitlyWait(1, TimeUnit.SECONDS);
		return driver;
	}

	String loadNfe(final String keyValue, State state) {
		WebDriver driver = getDriver();
		try {
			driver.navigate()
				  .to(state.getUrl());

			final WebElement image = driver.findElement(choiceFindBy(state.getImage()));
			final String src = image.getAttribute("src");
			final String captchaValue = captchaService.solve(src);

			final WebElement captcha = driver.findElement(choiceFindBy(state.getCaptcha()));
			captcha.sendKeys(captchaValue);

			final WebElement key = driver.findElement(choiceFindBy(state.getKey()));
			key.sendKeys(keyValue);

			final WebElement btnSubmit = driver.findElement(choiceFindBy(state.getButton()));
			btnSubmit.click();

			return driver.getPageSource();
		} finally {
			driver.close();
		}

	}

	private By choiceFindBy(StateProperty stateProperty) {
		switch (stateProperty.getType()) {
			case "id":
				return By.id(stateProperty.getValue());
			case "name":
				return By.name(stateProperty.getValue());
			case "xpath":
				return By.xpath(stateProperty.getValue());
			default:
				throw new IllegalArgumentException("Type to 'Find By' not found");
		}
	}

}
