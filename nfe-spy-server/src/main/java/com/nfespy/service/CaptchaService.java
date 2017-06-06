package com.nfespy.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.nfespy.config.CaptchaSolutionsProperties;
import com.nfespy.exception.CaptchaException;

@Service
public class CaptchaService {

	private static final String REGEX = "<[^<>]*>|\\W";

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaService.class);

	@Autowired
	private CaptchaSolutionsProperties captchaSolutionsProperties;

	public String solve(final String urlOrImage) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost post = getHttpPost(urlOrImage);
			final CloseableHttpResponse httpResponse = httpClient.execute(post);

			final String ret = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.debug("Captcha found: {}", ret);
			return ret.replaceAll(REGEX, StringUtils.EMPTY);
		} catch (Exception ex) {
			throw new CaptchaException("Captcha cannot be solved", ex);
		}
	}

	private HttpPost getHttpPost(final String urlOrImage) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(captchaSolutionsProperties.getUrl());
		List<NameValuePair> nvps = new ArrayList<>();
		if (urlOrImage.startsWith("http")) {
			nvps.add(new BasicNameValuePair("p", "decode"));
			nvps.add(new BasicNameValuePair("url", urlOrImage));
		} else {
			nvps.add(new BasicNameValuePair("p", "base64"));
			nvps.add(new BasicNameValuePair("captcha", urlOrImage));
		}
		nvps.add(new BasicNameValuePair("key", captchaSolutionsProperties.getKey()));
		nvps.add(new BasicNameValuePair("secret", captchaSolutionsProperties.getSecret()));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		return post;
	}

}
