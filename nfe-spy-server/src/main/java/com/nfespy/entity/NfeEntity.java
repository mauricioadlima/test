package com.nfespy.entity;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nfespy.domain.Nfe;

@Document(collection = "nfes")
public class NfeEntity {

	public enum Status {
		WAITING, PROCESSING, PROCESSED, ERROR, FAIL
	}

	@Id
	@NotEmpty
	private String key;

	@NotNull
	private UUID lotId;

	@NotNull
	private Status status = Status.WAITING;

	@NotEmpty
	private String state;

	private Nfe nfe;

	public NfeEntity(final String key, final String state) {
		this.key = key;
		this.state = state;
	}

	public void setLotId(final UUID lotId) {
		this.lotId = lotId;
	}

	public UUID getLotId() {
		return lotId;
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

	public Nfe getNfe() {
		return nfe;
	}

	public void setNfe(final Nfe nfe) {
		this.nfe = nfe;
	}
}
