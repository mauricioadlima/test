package com.nfespy.api.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfespy.entity.EstadoEntity;

class StateRequestResponse {

	@JsonProperty
	private String state;

	@JsonProperty
	private String url;

	StateRequestResponse(final EstadoEntity estadoEntity) {
		this.state = estadoEntity.getId();
		this.url = estadoEntity.getUrl();
	}

	EstadoEntity toModel() {
		return new EstadoEntity(state, url);
	}

}
