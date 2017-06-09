package com.nfespy.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nfes")
public class NfeEntity {

	public enum Status {
		ESPERANDO, PROCESSANDO, PROCESSADA, ERRO, FALHA
	}

	@Id
	private String chave;

	private UUID lotId;

	private Status status = Status.ESPERANDO;

	private String estado;

	public NfeEntity(final String chave, final String estado) {
		this.chave = chave;
		this.estado = estado;
	}

	public void setLotId(final UUID lotId) {
		this.lotId = lotId;
	}

	public String getChave() {
		return chave;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public String getEstado() {
		return estado;
	}
}
