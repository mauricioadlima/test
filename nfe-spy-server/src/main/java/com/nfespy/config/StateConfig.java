package com.nfespy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("state")
@Component
public class StateConfig {

	private String nacional;

	private String sp;

	public String getNacional() {
		return nacional;
	}

	public void setNacional(final String nacional) {
		this.nacional = nacional;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(final String sp) {
		this.sp = sp;
	}
}
