package com.mixconnector.captcha.solutions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixconnector.captcha.CaptchaService;
import com.mixconnector.html.HtmlService;

@Service
public class CaptchaSolutionsService implements CaptchaService {

	private static final String REGEX = "<[^<>]*>|\\W";

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaService.class);

	@Autowired
	private CaptchaSolutionsProperties captchaSolutionsProperties;

	@Autowired
	private HtmlService htmlService;

	@Override
	public String solve(final String imageBase64) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost post = getHttpPost(imageBase64);
			final CloseableHttpResponse httpResponse = httpClient.execute(post);

			final String ret = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.debug("Captcha found: {}", ret);
			return ret.replaceAll(REGEX, StringUtils.EMPTY);
		} catch (Exception ex) {
			throw new CaptchaSolutionsException(ex);
		}
	}

	@Override
	public String solve(final File image) {
		try {
			return solve(addPrefix(image.getName()) + new String(Files.readAllBytes(image.toPath())));
		} catch (IOException ex) {
			throw new CaptchaSolutionsException(ex);
		}
	}

	private String addPrefix(String imageUrl) {
		if (imageUrl.toLowerCase()
					.endsWith("png")) {
			return "data:image/png;base64,";
		}
		return "data:image/jpg;base64,";
	}

	private HttpPost getHttpPost(final String imageBase64) throws IOException {
		HttpPost post = new HttpPost(captchaSolutionsProperties.getUrl());
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("captcha", imageBase64));
		nvps.add(new BasicNameValuePair("p", "base64"));
		nvps.add(new BasicNameValuePair("key", captchaSolutionsProperties.getKey()));
		nvps.add(new BasicNameValuePair("secret", captchaSolutionsProperties.getSecret()));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		return post;
	}
}
