package com.nfespy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class GlobalConfig {

	private String phantomjsBinary;

	public String getPhantomjsBinary() {
		return phantomjsBinary;
	}

	public void setPhantomjsBinary(final String phantomjsBinary) {
		this.phantomjsBinary = phantomjsBinary;
	}
}
