package com.nfespy.site;

import org.springframework.stereotype.Component;

@Component("sp")
public class SaoPaulo extends AbstractHttpService {

	public SaoPaulo() {
		super("sp");
	}
}