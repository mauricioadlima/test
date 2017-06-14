package com.mixconnector.html;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HtmlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlService.class);

	// Scape ssl connections
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

	public void download(String imageUrl, File output, String sessionId) throws IOException {
		LOGGER.debug("Downloading image: {}", imageUrl);
		URL url = new URL(imageUrl);
		final URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Cookie", sessionId);

		try (FileOutputStream out = new FileOutputStream(output)) {
			try (InputStream in = new BufferedInputStream(urlConnection.getInputStream())) {
				byte[] buf = new byte[1024];
				int n;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
			}
		}
	}
}
