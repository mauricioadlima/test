package com.nfespy.driver;

import org.openqa.selenium.remote.RemoteWebDriver;

@FunctionalInterface
public interface WebDriver {

	RemoteWebDriver getDriver();
}
