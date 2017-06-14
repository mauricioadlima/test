package com.mixconnector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class GlobalProperties {

	private String phantomjsBinary;

	private String chromeBinary;

	public String getPhantomjsBinary() {
		return phantomjsBinary;
	}

	public void setPhantomjsBinary(final String phantomjsBinary) {
		this.phantomjsBinary = phantomjsBinary;
	}

	public String getChromeBinary() {
		return chromeBinary;
	}

	public void setChromeBinary(final String chromeBinary) {
		this.chromeBinary = chromeBinary;
	}
}
