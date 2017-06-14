package com.mixconnector.captcha.solutions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mixconnector.captcha.CaptchaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore("API is out")
public class CaptchaSolutionsServiceTest {

	private CaptchaService captchaService = new CaptchaSolutionsService();

	@Value("classpath:img.jpg")
	private Resource resource;

	@Test
	public void solve() throws IOException {
		assertEquals("nm9c", captchaService.solve(resource.getFile()));
	}
}