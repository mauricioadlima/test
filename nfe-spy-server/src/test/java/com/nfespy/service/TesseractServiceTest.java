package com.nfespy.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TesseractServiceTest {

	private TesseractService tesseractService = new TesseractService();

	@Value("classpath:img.jpg")
	private Resource resource;

	@Test
	public void solve() throws IOException {
		assertEquals("nm9c", tesseractService.solve(resource.getFile()));
	}
}