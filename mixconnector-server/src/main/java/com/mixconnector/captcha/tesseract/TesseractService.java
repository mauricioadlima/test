package com.mixconnector.captcha.tesseract;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.mixconnector.captcha.CaptchaService;

@Service("tesseract")
public class TesseractService implements CaptchaService {

	@Override
	public String solve(final String image) {
		try {
			final Path path = Files.createTempFile("image-", null);
			Files.write(path, image.getBytes());
			return solve(path.toFile());
		} catch (IOException e) {
			throw new TesseractException(e);
		}
	}

	@Override
	public String solve(File image) {
		try {
			File tmp = File.createTempFile("tesseract-", null);
			final Process process = Runtime.getRuntime()
									.exec(String.format("tesseract %s %s", image.getAbsolutePath(), tmp.getAbsolutePath()));

			File fileTextImage = new File(tmp.getAbsolutePath() + ".txt");
			if (process.waitFor() == 0 && fileTextImage.exists()) {
				return new String(Files.readAllBytes(Paths.get(fileTextImage.getAbsolutePath()))).trim();
			}
		} catch (InterruptedException | IOException e) {
			throw new TesseractException(e);
		}
		throw new TesseractException();
	}

}
