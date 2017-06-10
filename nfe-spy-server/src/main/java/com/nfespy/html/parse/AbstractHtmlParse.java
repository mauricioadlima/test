package com.nfespy.html.parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.GlobalProperties;
import com.nfespy.config.StateProperties;
import com.nfespy.config.StateTypeValue;
import com.nfespy.domain.Nfe;
import com.nfespy.driver.WebDriver;
import com.nfespy.domain.NfeException;
import com.nfespy.repository.StateRepository;
import com.nfespy.captcha.CaptchaService;
import com.nfespy.html.HtmlService;

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

	private String estado;

	AbstractHtmlParse(final String estado, CaptchaService captchaService) {
		this.captchaService = captchaService;
		this.estado = estado;
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
		final Map<String, StateTypeValue> form = stateProperties.getForms()
																.get(estado);
		final Map<String, StateTypeValue> fields = stateProperties.getFields()
																  .get(estado);
		final RemoteWebDriver driver = getDriver();
		try {
			final String url = stateRepository.findOne(estado)
											  .getUrl();
			if (StringUtils.isBlank(url)) {
				throw new NfeException(String.format("State url cannot be null. State: %s", estado));
			}
			driver.navigate()
				  .to(url);

			final WebElement image = driver.findElement(choiceFindBy(form.get(IMAGE)));
			final String src = image.getAttribute("src");

			final String captchaValue = solve(src, getSessionId(driver));

			final WebElement captcha = driver.findElement(choiceFindBy(form.get(CAPTCHA)));
			captcha.sendKeys(captchaValue);

			final WebElement key = driver.findElement(choiceFindBy(form.get(KEY)));
			key.sendKeys(keyValue);

			final WebElement btnSubmit = driver.findElement(choiceFindBy(form.get(SEARCH)));
			btnSubmit.click();

			return parse(fields, driver);
		} finally {
			driver.close();
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

	private Nfe parse(Map<String, StateTypeValue> fields, RemoteWebDriver webDriver) {
		Nfe nfe = new Nfe();
		fields.forEach((k, v) -> {
			try {
				final WebElement element = webDriver.findElement(choiceFindBy(v));
				BeanUtils.setProperty(nfe, k, element.getText());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchElementException e) {
				LOGGER.error("Error on bind field: {}, type: {}", k, v.getType(), e);
			}
		});
		return nfe;
	}

	private By choiceFindBy(StateTypeValue stateTypeValue) {
		switch (stateTypeValue.getType()) {
			case "id":
				return By.id(stateTypeValue.getValue());
			case "name":
				return By.name(stateTypeValue.getValue());
			case "xpath":
				return By.xpath(stateTypeValue.getValue());
			default:
				throw new IllegalArgumentException("Search type not found. Should be (id, name, xpath)");
		}
	}

}
