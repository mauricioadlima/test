package com.mixconnector.captcha.tesseract;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mixconnector.captcha.CaptchaService;

@RunWith(SpringRunner.class)
public class TesseractServiceTest {

	private CaptchaService tesseractService = new TesseractService();

	@Value("classpath:img.jpg")
	private Resource resource;

	@Test
	public void solveByFile() throws IOException {
		assertEquals("nm9c", tesseractService.solve(resource.getFile()));
	}

	@Test
	public void solveByByteArray() throws IOException {
		assertEquals("nm9c", tesseractService.solve(new String(Files.readAllBytes(Paths.get(resource.getURI())))));
	}
}