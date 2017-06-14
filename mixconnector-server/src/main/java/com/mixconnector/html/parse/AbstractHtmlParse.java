package com.mixconnector.html.parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mixconnector.captcha.CaptchaException;
import com.mixconnector.captcha.CaptchaService;
import com.mixconnector.config.GlobalProperties;
import com.mixconnector.config.StateProperties;
import com.mixconnector.domain.Nfe;
import com.mixconnector.domain.NfeException;
import com.mixconnector.driver.WebDriver;
import com.mixconnector.html.HtmlService;
import com.mixconnector.repository.StateRepository;

abstract class AbstractHtmlParse implements HtmlParse {

	private static final String IMAGE = "imagem";

	private static final String CAPTCHA = "captcha";

	private static final String KEY = "chave";

	private static final String SEARCH = "consultar";

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHtmlParse.class);

	@Autowired
	private StateProperties stateProperties;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private WebDriver webDriverFactory;

	@Autowired
	private HtmlService htmlService;

	private CaptchaService captchaService;

	private String state;

	AbstractHtmlParse(final String state, CaptchaService captchaService) {
		this.captchaService = captchaService;
		this.state = state;
	}

	private RemoteWebDriver getDriver() {
		RemoteWebDriver driver = webDriverFactory.getDriver();
		driver.manage()
			  .timeouts()
			  .implicitlyWait(1, TimeUnit.SECONDS);
		return driver;
	}

	@Override
	public Nfe getNfe(final String keyValue) {
		final Map form = stateProperties.getForms()
										.get(state);
		final Map fields = stateProperties.getFields()
										  .get(state);
		final RemoteWebDriver driver = getDriver();
		try {
			final String url = stateRepository.findOne(state)
											  .getUrl();
			if (StringUtils.isBlank(url)) {
				throw new NfeException(String.format("State url cannot be null. State: %s", state));
			}
			driver.navigate()
				  .to(url);

			final WebElement key = driver.findElement(choiceFindBy(form.get(KEY)));
			key.sendKeys(keyValue);

			int attempts = 0;
			do {
				final WebElement image = driver.findElement(choiceFindBy(form.get(IMAGE)));
				final String src = image.getAttribute("src");
				final String captchaValue = solve(src, getSessionId(driver));

				final WebElement captcha = driver.findElement(choiceFindBy(form.get(CAPTCHA)));
				captcha.clear();
				captcha.sendKeys(captchaValue);

				final WebElement btnSubmit = driver.findElement(choiceFindBy(form.get(SEARCH)));
				btnSubmit.click();
			} while (!isCaptchaOk(driver) && shouldRetry(attempts++));

			Nfe nfe = new Nfe();
			bind(fields, driver, nfe);
			return nfe;
		} finally {
			driver.close();
		}
	}

	// TODO externalize attempts
	private boolean shouldRetry(int attempts) {
		if (attempts > 3) {
			throw new CaptchaException("The maximum number of attempts to solve the captcha has been reached.");
		}
		return true;
	}

	// TODO generic to override class children
	private boolean isCaptchaOk(RemoteWebDriver driver) {
		try {
			driver.findElement(By.id("ContentMain_cvCaptch"));
			return false;
		} catch (NoSuchElementException e) {
			LOGGER.debug("Captcha solved");
			return true;
		}
	}

	@Override
	public String getSessionId(final RemoteWebDriver driver) {
		String sessionKey = "ASP.NET_SessionId";
		final Cookie cookie = driver.manage()
									.getCookieNamed(sessionKey);
		return "ASP.NET_SessionId=" + cookie.getValue();
	}

	private String solve(String url, String sessionId) {
		try {
			File out = File.createTempFile("image", null);
			htmlService.download(url, out, sessionId);
			return captchaService.solve(out);
		} catch (IOException e) {
			throw new NfeException("Error on create tmp file", e);
		}
	}

	private void bind(final Map<Object, Map> fields, RemoteWebDriver driver, Object objectToBind) {
		fields.forEach((k, v) -> {
			try {
				if (v.containsKey("type")) {
					final WebElement element = driver.findElement(choiceFindBy(v));
					PropertyUtils.setProperty(objectToBind, k.toString(), getValue(element));
				} else {
					final Object property = PropertyUtils.getProperty(objectToBind, k.toString());
					bind(v, driver, property);
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOGGER.error("Error on access property: {}", k);
			}
		});
	}

	private String getValue(WebElement element) {
		return element.getAttribute("innerHTML")
					  .replaceAll("&nbsp;", " ")
					  .replaceAll(String.valueOf((char) 160), " ")
					  .trim().replaceAll("\\s{2,}", " ")
					  .replaceAll("\n", "");
	}

	private By choiceFindBy(final Object map) {
		return choiceFindBy((Map) map);
	}

	private By choiceFindBy(Map map) {
		final String type = map.get("type")
							   .toString();
		final String value = map.get("value")
								.toString();
		switch (type) {
			case "id":
				return By.id(value);
			case "name":
				return By.name(value);
			case "xpath":
				return By.xpath(value);
			default:
				throw new IllegalArgumentException("Search type not found. Use (id, name, xpath) instead of " + type);
		}
	}

}
