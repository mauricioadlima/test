package com.nfespy.domain;

import java.util.UUID;

public class NfeStatusReport {

	private String status;

	private UUID lotId;

	private int total;

	public String getStatus() {
		return status;
	}

	public UUID getLotId() {
		return lotId;
	}

	public int getTotal() {
		return total;
	}
}
