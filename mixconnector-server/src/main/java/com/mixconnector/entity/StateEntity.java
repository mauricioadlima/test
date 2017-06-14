package com.mixconnector.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estados")
public class StateEntity {

	@Id
	private String id;

	private String url;

	public StateEntity(final String id, final String url) {
		this.id = id;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

}