package com.nfespy.api.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfespy.entity.StateEntity;

class StateRequestResponse {

	@JsonProperty
	private String state;

	@JsonProperty
	private String url;

	public StateRequestResponse() {
	}

	StateRequestResponse(final StateEntity stateEntity) {
		this.state = stateEntity.getId();
		this.url = stateEntity.getUrl();
	}

	StateEntity toModel() {
		return new StateEntity(state, url);
	}

}
