package com.mixconnector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("async")
public class AsyncConfigProperties {

	private int maxPoolSize;

	private int corePoolSize;

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setMaxPoolSize(final int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setCorePoolSize(final int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
}
