package com.nfespy.html.parse;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.nfespy.domain.Nfe;

public interface HtmlParse {

	Nfe getNfe(final String keyValue);

	String getSessionId(RemoteWebDriver remoteWebDriver);

}
