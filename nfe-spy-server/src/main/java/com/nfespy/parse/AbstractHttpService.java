package com.nfespy.parse;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.nfespy.config.StateConfig;

public abstract class AbstractHttpService implements HttpService {

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

	@Autowired
	protected StateConfig stateConfig;
}
