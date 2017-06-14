package com.mixconnector.html.parse;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.mixconnector.domain.Nfe;

public interface HtmlParse {

	Nfe getNfe(final String keyValue);

	String getSessionId(RemoteWebDriver remoteWebDriver);

}
