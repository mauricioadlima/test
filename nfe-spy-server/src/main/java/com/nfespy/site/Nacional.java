package com.nfespy.site;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nfespy.model.CanonicNfe;

@Component("nacional")
public class Nacional extends AbstractHttpService {

	private static final String IMAGE_ID = "ctl00_ContentPlaceHolder1_imgCaptcha";

	private static final Logger LOGGER = LoggerFactory.getLogger(Nacional.class);

	private static final String KEY_ID = "ctl00_ContentPlaceHolder1_txtChaveAcessoCompleta";

	private static final String BTN_SUBMIT = "ctl00$ContentPlaceHolder1$btnConsultar";

	private static final String CAPTCHA_ID = "ctl00_ContentPlaceHolder1_txtCaptcha";

	@Override
	public CanonicNfe getNfe(final String key) {
		driver.navigate()
			  .to(stateConfigPropertires.getNacional());
		final WebElement image = driver.findElement(By.id(IMAGE_ID));
		final String src = image.getAttribute("src");
		final String captcha = brokenCaptcha(src.substring(22));

		final WebElement keyInput = driver.findElement(By.id(KEY_ID));
		keyInput.sendKeys(key);

		final WebElement captchaInput = driver.findElement(By.id(CAPTCHA_ID));
		captchaInput.sendKeys(captcha);

		final WebElement btnSubmit = driver.findElement(By.name(BTN_SUBMIT));
		btnSubmit.click();

		// TODO
		LOGGER.info(driver.getPageSource());
		return null;
	}
}
