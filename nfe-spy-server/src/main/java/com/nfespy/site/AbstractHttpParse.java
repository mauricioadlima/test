package com.nfespy.site;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.nfespy.config.GlobalProperties;
import com.nfespy.config.StateProperties;
import com.nfespy.config.StateTypeValue;
import com.nfespy.domain.Nfe;
import com.nfespy.exception.NfeException;
import com.nfespy.repository.StateRepository;
import com.nfespy.service.CaptchaService;

abstract class AbstractHttpParse implements HttpParse {

	private static final String IMAGE = "imagem";

	private static final String CAPTCHA = "captcha";

	private static final String KEY = "chave";

	private static final String SEARCH = "consultar";

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpParse.class);

	@Autowired
	private StateProperties stateProperties;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	@Qualifier("tesseractService")
	private CaptchaService captchaService;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private com.nfespy.driver.WebDriver webDriverFactory;

	private String estado;

	AbstractHttpParse(final String estado) {
		this.estado = estado;
	}

	WebDriver getDriver() {
		WebDriver driver = webDriverFactory.getDriver();
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
		WebDriver driver = getDriver();
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
			final String captchaValue = captchaService.solve(src);

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

	Nfe parse(Map<String, StateTypeValue> fields, WebDriver webDriver) {
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
