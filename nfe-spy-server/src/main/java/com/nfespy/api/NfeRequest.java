package com.nfespy.api;

import static java.util.Collections.EMPTY_LIST;
import static java.util.stream.Collectors.toList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfespy.model.Nfe;

class NfeRequest {

	private static final class NfeRequestProperty {

		@JsonProperty
		private String key;

		@JsonProperty
		private String state;
	}

	@JsonProperty
	private List<NfeRequestProperty> nfes;

	List<Nfe> toModel() {
		return nfes == null ?
				EMPTY_LIST :
				nfes.parallelStream()
					.map(p -> new Nfe(p.key, p.state))
					.collect(toList());
	}
}
