package com.nfespy.api.nfe;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
class NfeResponse {

	@JsonProperty
	private UUID lotId;

	@JsonProperty
	private List<String> chaves;

	void setLotId(final UUID lotId) {
		this.lotId = lotId;
	}

	void setChaves(final List<String> chaves) {
		this.chaves = chaves;
	}
}
