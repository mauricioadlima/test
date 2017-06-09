package com.nfespy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "states")
public class EstadoEntity {

	@Id
	private String id;

	private String url;

	public EstadoEntity(final String id, final String url) {
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