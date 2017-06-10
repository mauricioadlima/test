package com.nfespy.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nfespy.exception.TesseractException;

@Service
public class TesseractService implements CaptchaService {

	@Autowired
	private HtmlService htmlService;

	public String solve(File image) {
		try {
			File tmp = File.createTempFile("tesseract-", null);
			Runtime.getRuntime()
				   .exec(String.format("tesseract %s %s", image.getAbsolutePath(), tmp.getAbsolutePath()));
			int count = 0;
			File fileTextImage = new File(tmp.getAbsolutePath() + ".txt");
			do {
				if (fileTextImage.exists()) {
					return new String(Files.readAllBytes(Paths.get(fileTextImage.getAbsolutePath()))).trim();
				}
				Thread.sleep(1000);
				count++;
			} while (count < 3);
		} catch (InterruptedException | IOException ex) {
			throw new TesseractException("Tesseract cannot be read image", ex);
		}
		throw new TesseractException("Tesseract cannot be read image");
	}

	@Override
	public String solve(final String urlOrImage) {
		try {
			final File captcha = File.createTempFile("captcha", null);
			htmlService.download(urlOrImage, captcha);
			return solve(captcha);
		} catch (IOException e) {
			throw new TesseractException("Tesseract cannot be read image", e);
		}

	}
}
