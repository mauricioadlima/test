package com.nfespy.site;

import org.springframework.stereotype.Component;

@Component("nacional")
public class Nacional extends AbstractHttpService {

	public Nacional() {
		super("nacional");
	}
}
