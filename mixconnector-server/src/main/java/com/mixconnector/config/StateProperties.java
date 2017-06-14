package com.mixconnector.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class StateProperties {

	private Map<String, Map<Object, Object>> fields = new HashMap<>();

	private Map<String, Map<Object, Object>> forms = new HashMap<>();

	public Map<String, Map<Object, Object>> getFields() {
		return fields;
	}

	public void setFields(final Map<String, Map<Object, Object>> fields) {
		this.fields = fields;
	}

	public Map<String, Map<Object, Object>> getForms() {
		return forms;
	}

	public void setForms(final Map<String, Map<Object, Object>> forms) {
		this.forms = forms;
	}
}
