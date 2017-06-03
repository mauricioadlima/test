package com.nfespy.api;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(Include.NON_NULL)
class NfeResponse {

	@JsonProperty
	private UUID lotId;

	@JsonProperty
	private List<String> keys;

	void setLotId(final UUID lotId) {
		this.lotId = lotId;
	}

	void setKeys(final List<String> keys) {
		this.keys = keys;
	}
}
