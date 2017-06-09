package com.nfespy.site;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.GlobalProperties;
import com.nfespy.config.EstadoTipoValor;
import com.nfespy.config.EstadoProperties;
import com.nfespy.domain.Nfe;
import com.nfespy.repository.EstadoRepository;
import com.nfespy.service.CaptchaService;

abstract class AbstractHttpService implements HttpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpService.class);

	private static final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";

	private static final String WEB_SECURITY_NO = "--web-security=no";

	private static final String IGNORE_SSL_ERRORS_YES = "--ignore-ssl-errors=yes";

	private static final String CHROME_BINARY_PATH = "webdriver.chrome.driver";

	@Autowired
	private EstadoProperties estadoProperties;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private EstadoRepository estadoRepository;

	private String state;

	public AbstractHttpService(final String state) {
		this.state = state;
	}

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

	@Override
	public Nfe getNfe(final String keyValue) {
		final Map<String, EstadoTipoValor> form = estadoProperties.getForms()
																  .get(state);
		final Map<String, EstadoTipoValor> fields = estadoProperties.getCampos()
																	.get(state);
		WebDriver driver = getDriver();
		try {
			driver.navigate()
				  .to(estadoRepository.findOne(state)
									  .getUrl());

			final WebElement image = driver.findElement(choiceFindBy(form.get("imagem")));
			final String src = image.getAttribute("src");
			final String captchaValue = captchaService.solve(src);

			final WebElement captcha = driver.findElement(choiceFindBy(form.get("captcha")));
			captcha.sendKeys(captchaValue);

			final WebElement key = driver.findElement(choiceFindBy(form.get("chave")));
			key.sendKeys(keyValue);

			final WebElement btnSubmit = driver.findElement(choiceFindBy(form.get("consultar")));
			btnSubmit.click();

			return parse(fields, driver);
		} finally {
			driver.close();
		}

	}

	Nfe parse(Map<String, EstadoTipoValor> fields, WebDriver webDriver) {
		Nfe nfe = new Nfe();
		fields.forEach((k, v) -> {
			final WebElement element = webDriver.findElement(choiceFindBy(v));
			try {
				BeanUtils.setProperty(nfe, k, element.getText());
			} catch (IllegalAccessException | InvocationTargetException e) {
				LOGGER.error("Erro ao popular o campo: {}, tipo: {}", k, v.getTipo(), e);
			}
		});
		return nfe;
	}

	private By choiceFindBy(EstadoTipoValor estadoTipoValor) {
		switch (estadoTipoValor.getTipo()) {
			case "id":
				return By.id(estadoTipoValor.getValor());
			case "name":
				return By.name(estadoTipoValor.getValor());
			case "xpath":
				return By.xpath(estadoTipoValor.getValor());
			default:
				throw new IllegalArgumentException("Tipo para busca não encontrado. Deve ser (id, name, xpath)");
		}
	}

}
