package com.nfespy.captcha.tesseract;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.nfespy.captcha.CaptchaService;

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
		} catch (InterruptedException | IOException e) {
			throw new TesseractException(e);
		}
		throw new TesseractException();
	}

}
