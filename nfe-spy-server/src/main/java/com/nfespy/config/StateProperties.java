package com.nfespy.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class StateProperties {

	private Map<String, Map<String, StateTypeValue>> fields = new HashMap<>();

	private Map<String, Map<String, StateTypeValue>> forms = new HashMap<>();

	public Map<String, Map<String, StateTypeValue>> getFields() {
		return fields;
	}

	public void setFields(final Map<String, Map<String, StateTypeValue>> fields) {
		this.fields = fields;
	}

	public Map<String, Map<String, StateTypeValue>> getForms() {
		return forms;
	}

	public void setForms(final Map<String, Map<String, StateTypeValue>> forms) {
		this.forms = forms;
	}
}
