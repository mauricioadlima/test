package com.nfespy.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("estados")
@Component
public class EstadoProperties {

	private Map<String, Map<String, EstadoTipoValor>> campos = new HashMap<>();

	private Map<String, Map<String, EstadoTipoValor>> forms = new HashMap<>();

	public Map<String, Map<String, EstadoTipoValor>> getCampos() {
		return campos;
	}

	public void setCampos(final Map<String, Map<String, EstadoTipoValor>> campos) {
		this.campos = campos;
	}

	public Map<String, Map<String, EstadoTipoValor>> getForms() {
		return forms;
	}

	public void setForms(final Map<String, Map<String, EstadoTipoValor>> forms) {
		this.forms = forms;
	}
}
