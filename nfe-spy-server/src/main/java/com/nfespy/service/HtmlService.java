package com.nfespy.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class HtmlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlService.class);

	static {
		TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(final X509Certificate[] x509Certificates, final String s) throws CertificateException {

			}

			@Override
			public void checkServerTrusted(final X509Certificate[] x509Certificates, final String s) throws CertificateException {

			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

		} };

		HostnameVerifier trustAllHostnames = (hostname, session) -> true;

		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCertificates, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
		} catch (GeneralSecurityException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	String downloadImageBase64(String imageUrl) throws IOException {
		LOGGER.debug("Downloading image: {}", imageUrl);
		URL url = new URL(imageUrl);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			try (InputStream in = new BufferedInputStream(url.openStream())) {
				byte[] buf = new byte[1024];
				int n;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
			}
			String bytes64 = addPrefix(imageUrl) + Base64.getEncoder()
														 .encodeToString(out.toByteArray());
			LOGGER.debug(bytes64);
			return bytes64;
		}
	}

	private String addPrefix(String imageUrl) {
		if (imageUrl.toLowerCase()
					.endsWith("png")) {
			return "data:image/png;base64,";
		}
		return "data:image/jpg;base64,";
	}

	void download(String imageUrl, File output) throws IOException {
		LOGGER.debug("Downloading image: {}", imageUrl);
		URL url = new URL(imageUrl);
		try (FileOutputStream out = new FileOutputStream(output)) {
			try (InputStream in = new BufferedInputStream(url.openStream())) {
				byte[] buf = new byte[1024];
				int n;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
			}
		}
	}

}
