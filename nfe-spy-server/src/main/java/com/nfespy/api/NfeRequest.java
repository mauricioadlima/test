package com.nfespy.api;

import static java.util.Collections.EMPTY_LIST;
import static java.util.stream.Collectors.toList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfespy.model.Nfe;

class NfeRequest {

	@JsonProperty
	private List<String> keys;

	List<Nfe> toModel() {
		return keys == null ?
				EMPTY_LIST :
				keys.parallelStream()
					.map(Nfe::new)
					.collect(toList());
	}
}
