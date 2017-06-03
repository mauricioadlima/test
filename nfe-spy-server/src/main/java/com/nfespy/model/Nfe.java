package com.nfespy.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nfes")
public class Nfe {

	public enum Status {
		WAITING, PROCESSING, PROCESSED, ERROR
	}

	@Id
	private String key;

	private UUID lotId;

	private Status status = Status.WAITING;

	private String state;

	public Nfe(final String key) {
		this.key = key;
	}

	public void setLotId(final UUID lotId) {
		this.lotId = lotId;
	}

	public String getKey() {
		return key;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}
}
